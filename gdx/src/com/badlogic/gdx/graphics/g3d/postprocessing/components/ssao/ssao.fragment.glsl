#version 130

#ifdef GL_ES 
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision mediump float;
#else
#define MED
#define LOWP
#define HIGH
#endif


const int MAX_KERNEL_SIZE = 128;

// This constant removes artifacts caused by neighbour fragments with minimal depth difference.
#define CAP_MIN_DISTANCE 0.0001

// This constant avoids the influence of fragments, which are too far away.
#define CAP_MAX_DISTANCE 0.005

// u_normalTexture must contain normal (r,g,b), world space
uniform sampler2D u_normalTexture;
uniform sampler2D u_noiseTexture;
// Non linear depth, packed on (r,g,b,a)
uniform sampler2D u_depthTexture;

uniform mat4 u_projectionMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_inverseProjMatrix;
uniform mat4 u_normalViewMatrix;
uniform int u_kernelSize;
uniform vec3 u_kernelOffsets[MAX_KERNEL_SIZE];
uniform float u_radius;
uniform float u_power;

uniform float u_zNear;
uniform float u_zFar;

noperspective in vec3 v_viewRay; // required
noperspective in vec2 v_uv;

// Unpacks the high precision float stored over 4 channels
HIGH float unpack (vec4 colour) {
	const vec4 bitShifts = vec4(1.0 / (256.0 * 256.0 * 256.0),
								1.0 / (256.0 * 256.0),
								1.0 / 256.0,
								1);
	return dot(colour , bitShifts);
}

vec4 toNDC(vec4 a){
	return a * 2.0 - 1.0;
}

vec3 toNDC(vec3 a){
	return a * 2.0 - 1.0;
}

vec2 toNDC(vec2 a){
	return a * 2.0 - 1.0;
}

float toNDC(float a){
	return a * 2.0 - 1.0;
}

float linearizeDepth(float depth, mat4 projMatrix) {
	return projMatrix[3][2] / (depth - projMatrix[2][2]);
}

float linearizeDepth2(float depth) {
	depth = toNDC(depth);
	
	float f = u_zFar;
	float n = u_zNear;	
    return ((2.0 * f * n) / (f + n - depth * (f - n))) / (f - n);
}

// Returns non-linear depth
float getDepth(vec2 texCoord){
	return unpack(texture(u_depthTexture, texCoord).rgba);
}

// Returns world-space normal
vec3 getNormal(vec2 texCoord){	
	return toNDC(texture(u_normalTexture, texCoord).rgb);
}

// Return view space position
vec3 getViewPos(vec2 texCoord)
{
	// Calculate out of the fragment in screen space the view space position.
	vec2 texCoordtoNDCed = toNDC(texCoord);
	
	// Assume we have a normal depth range between 0.0 and 1.0, non-linear.
	float z = linearizeDepth2(getDepth(texCoord));
	
	vec4 posProj = vec4(texCoordtoNDCed.xy, z, 1.0);
	vec4 posView = u_inverseProjMatrix * posProj;
	
	posView /= posView.w;
	
	return posView.xyz;
}

float ssao(mat3 kernelMatrix, vec3 origin){
	// Go through the kernel samples and create occlusion factor.	
	float occlusion = 0.0;
	
	for (int i = 0; i < u_kernelSize; i++)
	{
		// Reorient sample vector in view space and calculate sample point.
		vec3 sample = kernelMatrix * u_kernelOffsets[i];
		sample = origin + u_radius * sample;
		
		// Project and get texture coordinates
		vec4 offset = vec4(sample, 1.0);
		offset = u_projectionMatrix * offset;		
		offset.xyz /= offset.w;
		offset.xy = offset.xy * 0.5 + 0.5;		
				
		// Get sample out of depth texture
		//float sampleDepth = linearizeDepth2(getDepth(offset.xy)); 
		//occlusion += (-sampleDepth >= sample.z ? 1.0 : 0.0);  
		
		
		float sampleDepth = linearizeDepth2(getDepth(offset.xy));		
		float delta = offset.z - sampleDepth;
		
		//if(delta > 0 && delta < u_radius)
		//	occlusion += 1,0;
								
	    //float rangeCheck = abs(offset.z - sampleDepth) < u_radius ? 1.0 : 0.0;
	    //occlusion += (sampleDepth <= offset.z ? 1.0 : 0.0) * rangeCheck;
		
		float rangeCheck = smoothstep(0.0, 1.0, u_radius / abs(-origin.z - sampleDepth));
		occlusion += rangeCheck * step(sampleDepth, offset.z);	
	}
	
	// No occlusion gets white, full occlusion gets black.
	occlusion = 1.0 - occlusion / float(u_kernelSize);
	return pow(occlusion, u_power);	
}


/*----------------------------------------------------------------------------*/
void main() {
	
	// Normalize the depth and normal value for bias due to storing in texture
	// Get the depth and normals
	float depth = getDepth(v_uv);
	vec3 normal = getNormal(v_uv);
	float linDepth = linearizeDepth2(getDepth(v_uv));
				
	// Receive normals in world space, transform to view space
	normal = mat3(u_viewMatrix) * normal;
				
	// Get noise texture coords:
	vec2 noiseTexCoords = vec2(textureSize(u_depthTexture, 0)) / vec2(textureSize(u_noiseTexture, 0));
	noiseTexCoords *= v_uv;
	
	// Construct kernel basis matrix:	
	vec3 rvec = toNDC(texture(u_noiseTexture, noiseTexCoords).xyz);
	vec3 tangent = normalize(rvec - normal * dot(rvec, normal));
	vec3 bitangent = cross(normal, tangent);
	mat3 kernelBasis = mat3(tangent, bitangent, normal);
		
	// Debug visualization
	//gl_FragColor = vec4(vec3(depth), 1);
	//gl_FragColor = vec4(vec3(linDepth), 1);
	//gl_FragColor = vec4(normal * 0.5 + 0.5, 1.0);
	//gl_FragColor = vec4(getViewPos(v_uv), 1);
	//gl_FragColor = texture2D(u_noiseTexture, v_uv).rgba;
	
	gl_FragColor = vec4(vec3(ssao(kernelBasis, getViewPos(v_uv))), 1.0);
}
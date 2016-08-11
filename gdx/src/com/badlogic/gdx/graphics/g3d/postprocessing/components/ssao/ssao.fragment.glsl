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

// u_normalTexture must contain normal (r,g,b) and depth (a)
uniform sampler2D u_normalTexture;
uniform sampler2D u_noiseTexture;
uniform sampler2D u_depthTexture;

uniform mat4 u_projectionMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_inverseMatrix;
uniform int u_kernelSize;
uniform vec3 u_kernelOffsets[MAX_KERNEL_SIZE];
uniform float u_radius;
uniform float u_power;

uniform float u_zNear;
uniform float u_zFar;

noperspective in vec3 v_viewRay; // required
noperspective in vec2 v_uv;

HIGH vec4 pack(HIGH float depth) {
	HIGH vec4 bitSh = vec4(256 * 256 * 256, 256 * 256, 256, 1.0);
	HIGH vec4 bitMsk = vec4(0, 1.0 / 256.0, 1.0 / 256.0, 1.0 / 256.0);
	HIGH vec4 comp = fract(depth * bitSh);
	comp -= comp.xxyz * bitMsk;
	return comp;
}

HIGH vec4 altPack(HIGH float depth) {
	const HIGH vec4 bias = vec4(1.0 / 255.0, 1.0 / 255.0, 1.0 / 255.0, 0.0);
	HIGH vec4 color = vec4(depth, fract(depth * 255.0), fract(depth * 65025.0), fract(depth * 160581375.0));
	return color - (color.yzww * bias);
}

HIGH float unpackAlt (vec4 colour) {
	const vec4 bitShifts = vec4(1.0 / (256.0 * 256.0 * 256.0),
								1.0 / (256.0 * 256.0),
								1.0 / 256.0,
								1);
	return dot(colour , bitShifts);
}

HIGH float unpack (vec4 colour) {
    const HIGH vec4 bitShifts = vec4(1.0, 1.0 / 255.0, 1.0 / 65025.0, 1.0 / 160581375.0);
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

float getDepth(vec2 texCoord){
	return unpackAlt(texture(u_depthTexture, texCoord).rgba);
}

vec3 getNormal(vec2 texCoord){
	return toNDC(texture(u_normalTexture, texCoord).rgb);
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

float linearizeDepth3(float depth){
	return gl_ProjectionMatrix[3].z/(toNDC(depth) - gl_ProjectionMatrix[2].z);
}
float ssao(mat3 kernelBasis, vec3 originPos) {
	
	float occlusion = 0.0;
	for (int i = 0; i < u_kernelSize; ++i) {
		//	get sample position:
		vec3 samplePos = kernelBasis * u_kernelOffsets[i];
		samplePos = samplePos * u_radius + originPos;
		
		//	project sample position:
		vec4 offset = u_projectionMatrix * vec4(samplePos, 1.0);
		offset.xy /= offset.w; // only need xy
		offset.xy = offset.xy * 0.5 + 0.5; // scale/bias to texcoords
		
		//	get sample depth:
		float sampleDepth = linearizeDepth2(getDepth(offset.xy));
		
	   float rangeCheck= abs(originPos.z - sampleDepth) < u_radius ? 1.0 : 0.0;
	   occlusion += (sampleDepth <= samplePos.z ? 1.0 : 0.0) * rangeCheck;
		
		//float rangeCheck = smoothstep(0.0, 1.0, u_radius / abs(originPos.z - sampleDepth));
		//occlusion += rangeCheck * step(sampleDepth, samplePos.z);
	}
	
	occlusion = 1.0 - (occlusion / float(u_kernelSize));
	return pow(occlusion, u_power);
}


float ssao2(mat3 kernelBasis, vec3 origin, float radius) {
	float occlusion = 0.0;
	for (int i = 0; i < u_kernelSize; ++i) {
		vec4 sample =  vec4(origin +(kernelBasis * u_kernelOffsets[i] * u_radius), 0.0);
		vec4 sampleProject = u_projectionMatrix * sample;
		sampleProject.xyz /= sampleProject.w;
		float depthRead = getDepth(sampleProject.xy*0.5+0.5);
		float diff = sampleProject.z*0.5+0.5 - depthRead;
		occlusion += depthRead <= sampleProject.z*0.5+0.5 ? 1.0 : 0.0;
						
		
		//float rangeCheck = smoothstep(0.0, 1.0, radius / abs(origin.z - sampleDepth));
		//occlusion += rangeCheck * step(sampleDepth, samplePos.z);
		
   		//float rangeCheck= abs(origin.z - sampleDepth) < radius ? 1.0 : 0.0;
   		//occlusion += (sampleDepth <= samplePos.z ? 1.0 : 0.0) * rangeCheck;
		
	}
	
	return 1.0 - (occlusion / float(u_kernelSize));
	//return occlusion = occlusion / float(u_kernelSize);
	//return pow(occlusion, u_power);
}

float ssao3(mat3 kernelMatrix, vec4 posView){
	// Go through the kernel samples and create occlusion factor.	
	float occlusion = 0.0;
	
	for (int i = 0; i < u_kernelSize; i++)
	{
		// Reorient sample vector in view space ...
		vec3 sampleVectorView = kernelMatrix * u_kernelOffsets[i];
		
		// ... and calculate sample point.
		vec4 samplePointView = posView + u_radius * vec4(sampleVectorView, 0.0);
		
		// Project point and calculate NDC.		
		vec4 samplePointNDC = u_projectionMatrix * samplePointView;		
		samplePointNDC /= samplePointNDC.w;
		
		// Create texture coordinate out of it.		
		vec2 samplePointTexCoord = samplePointNDC.xy * 0.5 + 0.5;   
		
		// Get sample out of depth texture

		float zSceneNDC = getDepth(samplePointTexCoord);		
		float delta = samplePointNDC.z - zSceneNDC;
		
		// If scene fragment is before (smaller in z) sample point, increase occlusion.
		if (delta > CAP_MIN_DISTANCE && delta < CAP_MAX_DISTANCE)
		{
			occlusion += 1.0;
		}
	}
	
	// No occlusion gets white, full occlusion gets black.
	occlusion = 1.0 - occlusion / (float(u_kernelSize) - 1.0);
	return pow(occlusion, u_power);
}


float ssao4(mat3 kernelMatrix, vec4 posView){
	// Go through the kernel samples and create occlusion factor.	
	float occlusion = 0.0;
	
	for (int i = 0; i < u_kernelSize; i++)
	{
		// Reorient sample vector in view space ...
		vec3 sampleVectorView = kernelMatrix * u_kernelOffsets[i];
		
		// ... and calculate sample point.
		vec4 samplePointView = posView + u_radius * vec4(sampleVectorView, 0.0);
		
		// Project point and calculate NDC.		
		vec4 samplePointNDC = u_projectionMatrix * samplePointView;		
		samplePointNDC.xyz /= samplePointNDC.w;
		
		// Create texture coordinate out of it.		
		vec2 samplePointTexCoord = samplePointNDC.xy * 0.5 + 0.5;   
		
		// Get sample out of depth texture

		float zSceneNDC = linearizeDepth2(getDepth(samplePointTexCoord));		
		float delta = samplePointNDC.z - zSceneNDC;
		
		float rangeCheck = smoothstep(0.0, 1.0, u_radius / abs(posView.z - zSceneNDC));
		occlusion += rangeCheck * step(zSceneNDC, samplePointNDC.z);	
	}
	
	// No occlusion gets white, full occlusion gets black.
	occlusion = 1.0 - occlusion / float(u_kernelSize);
	return pow(occlusion, u_power);
}

vec4 getViewPos(vec2 texCoord)
{
	// Calculate out of the fragment in screen space the view space position.
	vec2 texCoordtoNDCed = toNDC(texCoord);
	
	// Assume we have a normal depth range between 0.0 and 1.0, non-linear.
	float z = linearizeDepth2(getDepth(texCoord));
	
	vec4 posProj = vec4(texCoordtoNDCed.xy, z, 1.0);
	vec4 posView = u_inverseMatrix * posProj;
	
	posView /= posView.w;
	
	return posView;
}


/*----------------------------------------------------------------------------*/
void main() {
	
	// Normalize the depth and normal value for bias due to storing in texture
	// Get the depth and normals
	float depth = getDepth(v_uv);
	vec3 normal = getNormal(v_uv);
		
	vec4 iNorm = u_viewMatrix * vec4(normal, 1.0);
	normal = normalize(iNorm.xyz / iNorm.w);	
				
	// Get noise texture coords:
	vec2 noiseTexCoords = vec2(textureSize(u_depthTexture, 0)) / vec2(textureSize(u_noiseTexture, 0));
	noiseTexCoords *= v_uv;
	
	// Construct kernel basis matrix:	
	vec3 rvec = toNDC(texture(u_noiseTexture, noiseTexCoords).xyz);
	vec3 tangent = normalize(rvec - normal * dot(rvec, normal));
	vec3 bitangent = cross(normal, tangent);
	mat3 kernelBasis = mat3(normalize(tangent), normalize(normal), normalize(bitangent));
		
	// Debug visualization
	//gl_FragColor = vec4(depth, depth, depth, 1);
	//depth = linearizeDepth2(depth) / u_zFar;
	//gl_FragColor = vec4(depth, depth, depth, 1);
	//gl_FragColor = texture(u_depthTexture, v_uv).rgba;
	gl_FragColor = vec4(normal, 1.0);
	//gl_FragColor = vec4(v_uv, 0, 1);
	//gl_FragColor = vec4(v_viewRay, 1);	 // Correct view_ray
	//gl_FragColor = vec4(position, 1);
	//gl_FragColor = vec4(origin, 1);
	//gl_FragColor = getViewPos(v_uv);
	//gl_FragColor = texture2D(u_noiseTexture, v_uv).rgba;
	
	//gl_FragColor = vec4(ssao4(kernelBasis, getViewPos(v_uv)));
	//vec3 viewRay = v_viewRay;
	//viewRay.z = linearizeDepth2(depth);
	//gl_FragColor = vec4(ssao(kernelBasis, v_viewRay));
	//gl_FragColor = vec4(ssao(kernelBasis, getViewPos(v_uv).xyz));
}
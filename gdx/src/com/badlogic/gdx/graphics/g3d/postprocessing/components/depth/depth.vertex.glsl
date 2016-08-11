attribute vec3 a_position;
uniform mat4 u_projViewWorldTrans;

#if defined(diffuseTextureFlag) && defined(blendedFlag)
#define blendedTextureFlag
attribute vec2 a_texCoord0;
varying vec2 v_texCoords0;
#endif

#ifdef PackedDepthFlag
varying float v_depth;
#endif //PackedDepthFlag

void main() {
	#ifdef blendedTextureFlag
		v_texCoords0 = a_texCoord0;
	#endif // blendedTextureFlag
	
	gl_Position = u_projViewWorldTrans * vec4(a_position, 1.0);
}

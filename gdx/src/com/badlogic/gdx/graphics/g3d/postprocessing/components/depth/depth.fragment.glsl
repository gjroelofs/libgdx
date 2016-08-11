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

#if defined(diffuseTextureFlag) && defined(blendedFlag)
#define blendedTextureFlag
varying MED vec2 v_texCoords0;
uniform sampler2D u_diffuseTexture;
uniform float u_alphaTest;
#endif

#ifdef PackedDepthFlag
varying HIGH float v_depth;
#endif //PackedDepthFlag

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

void main() {
	#ifdef blendedTextureFlag
		if (texture2D(u_diffuseTexture, v_texCoords0).a < u_alphaTest)
			discard;
	#endif // blendedTextureFlag
	
	#ifdef PackedDepthFlag
		//gl_FragColor = pack(v_depth);
		gl_FragColor = pack(gl_FragCoord.z);
		//gl_FragColor = vec4(gl_FragCoord.z, gl_FragCoord.z, gl_FragCoord.z, 1);
	#endif //PackedDepthFlag
}

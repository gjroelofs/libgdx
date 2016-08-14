#ifdef normalFlag
varying vec3 v_normal;
#endif //normalFlag

void main() {
	#if defined(normalFlag) 
		vec3 normal = v_normal;
	#else
		vec3 normal = vec3(0.0);
	#endif // normalFlag

	gl_FragColor = vec4(normal * 0.5 + 0.5, 1);
}

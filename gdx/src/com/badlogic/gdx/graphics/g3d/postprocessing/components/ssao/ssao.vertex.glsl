#version 130

uniform float u_tanHalfFov;
uniform float u_aspectRatio;

uniform float u_screenWidth;
uniform float u_screenHeight;

uniform mat4 u_projectionMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_inverseMatrix;

attribute vec2 a_position;

noperspective out vec2 v_uv;
noperspective out vec3 v_viewRay;

void main() {
	v_uv.xy = a_position.xy * 0.5 + 0.5;
	v_viewRay = vec3(
		a_position.x * u_aspectRatio * u_tanHalfFov,
		a_position.y * u_tanHalfFov, 
		1.0
	);
	
	gl_Position = vec4(a_position.xy, 0.0, 1.0);
}

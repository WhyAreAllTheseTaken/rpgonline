uniform sampler2D texel;

uniform float exposure;
uniform float gamma;
uniform float pre_map_curve;
uniform float post_map_multiply;

void main() {
	vec2 loc = gl_TexCoord[0].st;
  	vec4 hdrColor = texture2D(texel, loc);
  	
  	vec3 hdrColor2 = pow(hdrColor.rgb, vec3(pre_map_curve));
  	
  	vec3 mapped = vec3(1.0) - exp(-hdrColor2 * exposure);
  	
  	mapped = pow(mapped, vec3(1.0 / gamma));

	mapped = mapped * post_map_multiply;
  	
    gl_FragColor = vec4(mapped, 1.0);
}
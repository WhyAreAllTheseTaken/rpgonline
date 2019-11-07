uniform sampler2D texel;

uniform float exposure;
uniform float gamma;

void main() {
	vec2 loc = gl_TexCoord[0].st;
  	vec4 hdrColor = texture2D(texel, loc);
  	
  	vec3 hdrColor2 = pow(hdrColor.rgb, vec3(1.85));
  	
  	vec3 mapped = vec3(1.0) - exp(-hdrColor2 * exposure);
  	
  	//vec3 mapped = hdrColor / (hdrColor + vec3(0.25));
  	
  	mapped = pow(mapped, vec3(1.1)) * 1.75;
  	
  	mapped = pow(mapped, vec3(1.0 / gamma));
  	
    gl_FragColor = vec4(mapped, 1.0);
}
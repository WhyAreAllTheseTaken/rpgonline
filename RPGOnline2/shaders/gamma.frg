
uniform sampler2D texel;
uniform float gamma;

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 c = texture2D(texel, loc);
  	
  	c = vec4(pow(c.rgb, vec3(1.0/gamma)), 1);
  	
  	gl_FragColor = c;
}

uniform sampler2D texel;
void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 p = texture2D(texel, loc);
  	
  	vec4 result = vec4(p.rgb * 1.5, 1);
  	
  	gl_FragColor = result;
}

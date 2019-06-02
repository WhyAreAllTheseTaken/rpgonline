uniform sampler2D texel;
void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 p = texture2D(texel, loc);
  	
  	float v = (p.r + p.g + p.b) / 3;
  	
  	p.a = v;
  	
  	gl_FragColor = p;
}

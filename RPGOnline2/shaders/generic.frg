uniform sampler2D texel;

void main()
{
	vec2 loc = gl_TexCoord[0].st;
  	gl_FragColor = texture2D(texel, loc);
}  
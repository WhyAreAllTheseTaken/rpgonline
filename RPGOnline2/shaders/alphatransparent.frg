uniform sampler2D texel;

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 p = texture2D(texel, loc);
  	
  	gl_FragColor = vec4(p.rgb, 0.25);
}

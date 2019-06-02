uniform sampler2D texel;

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 p = texture2D(texel, loc);
  	
  	float n = sin(loc.x * loc.y * 3000000) + cos(loc.x / loc.y * 3000000) + sin(loc.x * 2 / loc.y * 1500000)
  	+ sin(loc.x + loc.y * 2000000) + cos((loc.x / 2) / loc.y * 5000000) * fract(loc.x * 10 * loc.y);
  	
  	p.r *= 1.0 + n / 150.0;
  	p.g *= 1.0 + n / 150.0;
  	p.b *= 1.0 + n / 150.0;
  	
  	gl_FragColor = vec4(p.rgb, 1);
}

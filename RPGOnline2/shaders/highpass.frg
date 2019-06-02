uniform sampler2D texel;

float high(float v, float c) {
	if(v / 3 < 0.9) {
		return 0;
	} else {
		return c;
	}
}

float higha(float v) {
	if(v / 3 > 0.9) {
		return 1;
	}
	return 0;
}

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 p = texture2D(texel, loc);
  	
  	p.r = high(p.r + p.g + p.b, p.r);
  	p.g = high(p.g + p.g + p.b, p.g);
  	p.b = high(p.b + p.g + p.b, p.b);
  	p.a = higha(p.r + p.g + p.b);
  	
  	gl_FragColor = p;
}

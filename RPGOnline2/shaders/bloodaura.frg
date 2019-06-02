uniform sampler2D texel;

uniform float v;

float hypot(float x, float y) {
	return sqrt(x * x + y * y);
}

float hypot(vec2 v) {
	return sqrt(v.x * v.x + v.y * v.y);
}

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 p = texture2D(texel, loc);
  	
  	if (v > 9) {
  		p.r /= v / 9;
  	}
  	if (v > 7) {
  		p.g /= v - 6;
  		p.b /= v - 6;
  	}
  	
  	float h = hypot(loc.x - 0.5, loc.y - 0.25);
  	h *= v / 3;
  	
  	if (h < 1) {
  		h = 1;
  	}
  	
  	gl_FragColor = vec4(p.rgb / h, 1);
}

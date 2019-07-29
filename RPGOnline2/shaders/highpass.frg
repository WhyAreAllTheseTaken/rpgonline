uniform sampler2D texel;

uniform float limit;

float pass(float v, float limit) {
	if (v < limit) {
		return 0;
	} else {
		return v;
	}
}

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 p = texture2D(texel, loc);
  	
  	gl_FragColor = vec4(pass(p.r, limit), pass(p.g, limit), pass(p.b, limit), 1);
}

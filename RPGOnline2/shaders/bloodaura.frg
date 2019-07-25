//Fragment Shader
uniform sampler2D texel;

//Aura factor.
uniform float v;

// Square distance
float hypot(float x, float y) {
	return x * x + y * y;
}

// Square distance
float hypot(vec2 v) {
	return v.x * v.x + v.y * v.y;
}


// A shader than applies a red aura to the surrounding area.
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
  	
  	const vec2 constantList = vec2(1.0, 0.0);
  	gl_FragColor = p.rgba * vec4(h, 1, 1, 1) * constantList.xxxy + constantList.yyyx;
}

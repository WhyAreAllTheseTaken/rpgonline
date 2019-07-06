uniform sampler2D texel;

uniform vec4 bc;
uniform vec4 lc;
uniform vec4 mc;
uniform vec4 hc;
uniform vec4 wc;
uniform float lp;
uniform float mp;
uniform float hp;

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 c = texture2D(texel, loc);
  	
  	float b = (c.r + c.g + c.b) / 3;
  	
  	//gl_FragColor = vec4(b, b, b, 1);
  	
  	if (b < lp) {
  		gl_FragColor = mix(bc, lc, b / lp) * c;
  	} else if (b < mp) {
  		gl_FragColor = mix(lc, mc, ((b - lp) / (mp - lp))) * c;
  	} else if (b < hp) {
  		gl_FragColor = mix(mc, hc, ((b - mp) / (hp - mp))) * c;
  	} else {
  		gl_FragColor = mix(hc, wc, ((b - hp) / (1 - hp))) * c;
  	}
}



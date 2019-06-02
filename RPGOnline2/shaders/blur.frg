uniform sampler2D texel;

uniform float ss;

float hypot(float x, float y) {
	return sqrt(x * x + y * y);
}

void main() {  
	vec2 loc = gl_TexCoord[0].st;
	
  	vec4 p = texture2D(texel, loc);
  	
  	float div_sum = 0;
  	
  	for (float x = loc.x - 20; x <= loc.x + 20; x++) { 
    	for (float y = loc.y - 20; y <= loc.y + 20; y++) { 
    		vec2 loc2 = loc;
    		loc2.x = loc2.x + x * ss;
    		loc2.y = loc2.y + y * ss;
    		
    		if (!(x == 0 && y == 0)) {
    			p = p + texture2D(texel, loc2) / hypot(x, y);
    			div_sum = div_sum + 1.0 / hypot(x, y);
    		} else {
    			p = p + texture2D(texel, loc2);
    			div_sum = div_sum + 1.0;
    		}
  		}
  	}
  	
  	p = p / div_sum;
  	
  	vec4 result = vec4(p.rgb, 1);
  	
  	gl_FragColor = result;
}
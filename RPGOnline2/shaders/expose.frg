uniform sampler2D texel;
void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 p = texture2D(texel, loc);
  	
  	vec2 loc1 = loc;
  	loc1.x += 3.0 / 1366.0;
  	vec4 p1 = texture2D(texel, loc1);
  	
  	vec2 loc2 = loc;
  	loc2.x -= 3.0 / 1366.0;
  	vec4 p2 = texture2D(texel, loc2);
  	
  	vec2 loc3 = loc;
  	loc3.y -= 3.0 / 1366.0;
  	vec4 p3 = texture2D(texel, loc3);
  	
  	vec2 loc4 = loc;
  	loc4.y += 3.0 / 1366.0;
  	vec4 p4 = texture2D(texel, loc4);
  	
  	vec4 bloom = p1 + p2 + p3 + p4;
  	bloom /= 50;
  	
  	vec4 result = vec4(p.rgb * 1.01, 1) + bloom;
  	
  	gl_FragColor = result;
}

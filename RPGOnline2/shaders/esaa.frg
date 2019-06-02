uniform sampler2D texel;
uniform float ss;
void main ()
{
	vec2 loc0 = gl_TexCoord[0].st;
  	vec4 p0 = texture2D(texel, loc0);
  	
  	vec2 loc1 = gl_TexCoord[0].st;
  	loc1.x = loc1.x + ss;
  	vec4 p1 = texture2D(texel, loc1);
  	
  	vec2 loc2 = gl_TexCoord[0].st;
  	loc2.x = loc2.x - ss;
  	vec4 p2 = texture2D(texel, loc2);
  	
  	vec2 loc3 = gl_TexCoord[0].st;
  	loc3.y = loc3.y - ss;
  	vec4 p3 = texture2D(texel, loc3);
  	
  	vec2 loc4 = gl_TexCoord[0].st;
  	loc4.y = loc4.y + ss;
  	vec4 p4 = texture2D(texel, loc4);
  	
  	vec2 loc5 = gl_TexCoord[0].st;
  	loc5.x = loc5.x + ss / 1.05;
  	loc5.y = loc5.y - ss / 1.05;
  	vec4 p5 = texture2D(texel, loc5);
  	
  	vec2 loc6 = gl_TexCoord[0].st;
  	loc6.x = loc6.x - ss / 1.05;
  	loc6.y = loc6.y + ss / 1.05;
  	vec4 p6 = texture2D(texel, loc6);
  	
  	vec2 loc7 = gl_TexCoord[0].st;
  	loc7.x = loc7.x - ss / 1.05;
  	loc7.y = loc7.y - ss / 1.05;
  	vec4 p7 = texture2D(texel, loc7);
  	
  	vec2 loc8 = gl_TexCoord[0].st;
  	loc8.x = loc8.x + ss / 1.05;
  	loc8.y = loc8.y - ss / 1.05;
  	vec4 p8 = texture2D(texel, loc8);
  	
  	vec4 result = vec4(((p0 + p1 / 2 + p2 / 2 + p3 / 2 + p4 / 2 + p5 / 3 + p6 / 3 + p7 / 3 + p8 / 3) / 4).rgb, 1);
  	
  	gl_FragColor = result;
}

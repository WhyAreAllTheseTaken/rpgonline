uniform sampler2D texel;
uniform float u_time;
uniform float stren;
void main ()
{
	vec2 loc = gl_TexCoord[0].st;
	float wave = ((cos(750 * loc.y + u_time) / 2000) + (cos(2000 * loc.y + u_time * 2) / 6000) + (cos(100 * loc.y + u_time / 2) / 3000)) * stren;
  	loc.x = loc.x + wave;
  	float wave2 = ((cos(750 * loc.x + u_time) / 2000) + (cos(2000 * loc.x + u_time * 2) / 6000) + (cos(100 * loc.x + u_time / 2) / 3000)) * stren;
  	loc.y = loc.y + wave2 / 2;
  	vec4 result = vec4(texture2D(texel, loc).rgb, 1);
  	gl_FragColor = result;
}

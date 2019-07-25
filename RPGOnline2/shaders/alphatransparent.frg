//Fragment Shader
uniform sampler2D texel;

//A shader for making a texture transparent.
void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 p = texture2D(texel, loc);
  	
  	const vec2 constantList = vec2(1.0, 0.0);
  	gl_FragColor = p.rgba * constantList.xxxy + vec4(0, 0, 0, 0.25);
}


void contrastAdjust( inout vec4 color, in float c) {
    float t = 0.5 - c * 0.5; 
    color.rgb = color.rgb * c + t;
}

uniform sampler2D texel;
uniform float contrast;

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 c = texture2D(texel, loc);
  	contrastAdjust(c, contrast);
  	
  	gl_FragColor = c;
}

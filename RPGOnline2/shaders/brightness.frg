/** 
 * Brightness Contrast Saturation Hue
 * Demo: https://www.shadertoy.com/view/MdjBRy
 * starea @ ShaderToy
 * 
 * Forked and remixed from: 
 * [1] https://shadertoy.com/view/llGSzK
 * [2] https://shadertoy.com/view/MsjXRt
 *
 * Created 7/26/2017
 * Updated 8/11/2017
 **/

/*
mat4 brightnessMatrix( float b ) {
    return mat4( 
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        b, b, b, 1 );
}
*/
void brightnessAdjust( inout vec4 color, in float b) {
    color.rgb += b;
}

uniform sampler2D texel;
uniform float brightness;

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 c = texture2D(texel, loc);
  	
  	brightnessAdjust(c, brightness);
  	
  	if (c.r < 0) {
  		c.r = 0;
  	}
  	if (c.g < 0) {
  		c.g = 0;
  	}
  	if (c.b < 0) {
  		c.b = 0;
  	}
  	
  	gl_FragColor = c;
}

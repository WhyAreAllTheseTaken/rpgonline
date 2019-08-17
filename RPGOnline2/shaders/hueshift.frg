// remixed from mAlk's https://www.shadertoy.com/view/MsjXRt
vec4 shiftHue(in vec3 col, in float Shift)
{
    vec3 P = vec3(0.55735) * dot(vec3(0.55735), col);
    vec3 U = col - P;
    vec3 V = cross(vec3(0.55735), U);    
    col = U * cos(Shift * 6.2832) + V * sin(Shift * 6.2832) + P;
    return vec4(col, 1.0);
}

uniform sampler2D texel;
uniform float hueShift;

void main ()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 c = texture2D(texel, loc);
  	
  	c = shiftHue(c, hueShift);
  	
  	gl_FragColor = c;
}

mat4 saturationMatrix( float saturation ) {
    vec3 luminance = vec3( 0.3086, 0.6094, 0.0820 );
    float oneMinusSat = 1.0 - saturation;
    vec3 red = vec3( luminance.x * oneMinusSat );
    red.r += saturation;
    
    vec3 green = vec3( luminance.y * oneMinusSat );
    green.g += saturation;
    
    vec3 blue = vec3( luminance.z * oneMinusSat );
    blue.b += saturation;
    
    return mat4( 
        red,     0,
        green,   0,
        blue,    0,
        0, 0, 0, 1 );
}

uniform sampler2D texel;
uniform float saturation;

void main() {
	vec2 loc = gl_TexCoord[0].st;
  	vec4 c = texture2D(texel, loc);
  	
  	gl_FragColor = saturationMatrix(saturation) * c;
}
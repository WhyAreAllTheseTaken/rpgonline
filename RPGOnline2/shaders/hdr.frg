uniform sampler2D texel;

uniform float exposure;
uniform float gamma;

uniform float curve_angle;
uniform float curve_exponent;
uniform float contrast;
uniform float white_point;

uniform vec2 screenScale;

vec3 f(vec3 x) {
	vec3 a = vec3(1 / curve_angle); // angle
	vec3 c = vec3(curve_exponent); // e
	vec3 g = vec3(contrast); // contrast
	return ((1 / (1 - pow(pow(exposure * x, g) + a, c) - 1.5))) / 2;
}

vec3 t(vec3 x) {
	float w = white_point;
	return (f(x) - f(0)) / (f(w) - f(0));
}

vec3 sampleWithExposure(vec2 loc) {
	return texture2D(texel, loc).rgb * exposure;
}

vec3 getBloomColor(vec2 loc, vec2 d, vec2 samples) {
	float amount = 0.5;

	vec3 bloom = vec3(0);
	
	for (float x = loc.x - d.x / 2; x <= loc.x + d.x / 2; x += d.x / samples) {
		for (float y = loc.y- d.y / 2; y <= loc.y + d.y / 2; y += d.y / samples) {
			vec2 loc2 = vec2(x, y);
			float sqr_dist = (loc.x * amount - loc2.x * amount) * (loc.x * amount - loc2.x * amount) + (loc.y * amount - loc2.y * amount) * (loc.y * amount - loc2.y * amount) + 1;
						
			vec3 sample = sampleWithExposure(loc2);
			
			sample = sample - vec3(1, 1, 1);
			
			if (sample.r > 0) {
				bloom.r = bloom.r + sample.r / sqr_dist;
			}
			if (sample.g > 0) {
				bloom.g = bloom.g + sample.g / sqr_dist;
			}
			if (sample.b > 0) {
				bloom.b = bloom.b + sample.b / sqr_dist;
			}
		}
	}
	
	return bloom / (samples.x * samples.y);
}

void main() {
  	vec3 hdrColor = sampleWithExposure(gl_TexCoord[0].st);
  	
  	hdrColor = hdrColor + getBloomColor(gl_TexCoord[0].st, vec2(0.003, 0.003) * screenScale, vec2(7, 7));
  	
  	vec3 mapped = t(hdrColor);
  	
  	mapped = pow(mapped, vec3(1.0 / gamma));
  	
    gl_FragColor = vec4(mapped, 1);
}
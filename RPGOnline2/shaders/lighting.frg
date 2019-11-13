uniform sampler2D texel;

uniform vec4 ambientLight;

uniform vec2 worldScale;

struct Light {
	vec2 location;
	vec4 lightColor;
};

const int light_count_max = 32;

uniform int light_count;

uniform Light lights[light_count_max];

vec2 scale(vec2 p) {
	return p * worldScale / 2;
}

float sqr_dist(vec2 p1, vec2 p2) {
	return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y) + (0 - 1) * (0 - 1);
}

vec4 computeLight(Light light, vec2 loc) {
	float dist = sqr_dist(scale(loc), scale(light.location));
	dist /= 2;
	return light.lightColor * 1.1 / dist / (1 + length(ambientLight) * 2);
}

vec4 computeLights(vec2 loc) {
	if (light_count < 1) {
		return vec4(0);
	}
	
	vec4 total = vec4(0);
	
	for (int i = 0; i < min(light_count, light_count_max); i++) {
		total += computeLight(lights[i], loc);
	}
	
	return total;
}

void main()
{
	vec2 loc = gl_TexCoord[0].st;
  	vec4 objectColor = texture2D(texel, loc);
	
    vec3 result = (ambientLight / 1.1 + computeLights(loc)) * objectColor;
    
    gl_FragColor = vec4(result, 1);
}  
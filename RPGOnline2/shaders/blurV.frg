//https://github.com/genekogan/Processing-Shader-Examples/blob/master/TextureShaders/data/blur.glsl

//Fragment Shader
uniform sampler2D texture;
uniform int blurSize;       
uniform float sigma;        // The sigma value for the gaussian function: higher value means more blur
                            // A good value for 9x9 is around 3 to 5
                            // A good value for 7x7 is around 2.5 to 4
                            // A good value for 5x5 is around 2 to 3.5
                            // ... play around with this based on what you need :)
const int direction = 1;
const vec2 texOffset = vec2(1.0 / 1920.0, 1.0 / 1080.0);

const float PI = 3.14159265;

void main() {
  vec2 localOffset = direction == 0 ? vec2(texOffset.x, 0) : vec2(0, texOffset.y);

  vec2 p = gl_TexCoord[0].st;
  float numBlurPixelsPerSide = float(blurSize / 2);
  
  // Incremental Gaussian Coefficent Calculation (See GPU Gems 3 pp. 877 - 889)
  vec3 incrementalGaussian;
  incrementalGaussian.x = 1.0 / (sqrt(2.0 * PI) * sigma);
  incrementalGaussian.y = exp(-0.5 / (sigma * sigma));
  incrementalGaussian.z = incrementalGaussian.y * incrementalGaussian.y;

  vec4 avgValue = vec4(0.0, 0.0, 0.0, 0.0);
  float coefficientSum = 0.0;

  // Take the central sample first...
  avgValue += texture2D(texture, p) * incrementalGaussian.x;
  coefficientSum += incrementalGaussian.x;
  incrementalGaussian.xy *= incrementalGaussian.yz;

  // Go through the remaining 8 vertical samples (4 on each side of the center)
  for (float i = 1.0; i <= numBlurPixelsPerSide; i++) { 
    avgValue += texture2D(texture, p - i * localOffset) * incrementalGaussian.x;         
    avgValue += texture2D(texture, p + i * localOffset) * incrementalGaussian.x;         
    coefficientSum += 2.0 * incrementalGaussian.x;
    incrementalGaussian.xy *= incrementalGaussian.yz;
  }

  gl_FragColor = avgValue / coefficientSum;
}
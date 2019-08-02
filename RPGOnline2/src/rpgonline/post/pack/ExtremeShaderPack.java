package rpgonline.post.pack;

import rpgonline.post.ColorEffectsShader;
import rpgonline.post.DynamicHeatShader2;
import rpgonline.post.FragmentExpose;
import rpgonline.post.MotionBlur;
import rpgonline.post.MultiEffect;

/**
 * A high quality shader pack for high end devices.
 * 
 * @author Tomas
 */
@Deprecated
public class ExtremeShaderPack extends MultiEffect {
	/**
	 * Create the shader pack.
	 */
	public ExtremeShaderPack() {
		//super(new DynamicHeatShader2("get heat"), new FragmentExpose(), new EdgeResample(), new ColorBoostEffect(), new MotionBlur(0.35f));
		super(new DynamicHeatShader2(), new FragmentExpose(), new MotionBlur(0.35f), new ColorEffectsShader(1f /*s*/, 0f /*b*/, 1.25f /*c*/, 1.25f /*v*/, 0f /*h*/, 0.8f /*g*/));
	}
}
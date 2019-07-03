package rpgonline.post.pack;

import rpgonline.post.ColorEffectsShader;
import rpgonline.post.DynamicHeatShader2;
import rpgonline.post.FXAA;
import rpgonline.post.FragmentExpose;
import rpgonline.post.LowExpose;
import rpgonline.post.MotionBlur;
import rpgonline.post.MultiEffect;
import rpgonline.post.PostEffect;
import rpgonline.post.ToggledEffect;

public class ConfigurableEffectsPack extends MultiEffect {
	public static final ConfigurableEffectsPack INSTANCE = new ConfigurableEffectsPack();
	
	private static ToggledEffect heatShader;
	private static ToggledEffect expose;
	private static ToggledEffect expose2;
	private static ToggledEffect motionBlur;
	private static ToggledEffect colorEffects;
	private static ToggledEffect fxaa;
	
	private ConfigurableEffectsPack() {
		super(create());
	}

	public static PostEffect[] create() {
		heatShader = new ToggledEffect(new DynamicHeatShader2());
		expose = new ToggledEffect(new FragmentExpose(), true);
		expose2 = new ToggledEffect(new LowExpose());
		motionBlur = new ToggledEffect(new MotionBlur(0.4f), true);
		colorEffects = new ToggledEffect(new ColorEffectsShader(1f /*s*/, 0f /*b*/, 1.1f /*c*/, 1.25f /*v*/, 0f /*h*/, 1f /*g*/), true);
		fxaa = new ToggledEffect(new FXAA());
		
		return new PostEffect[] {
				heatShader,
				expose,
				expose2,
				colorEffects,
				motionBlur,
				fxaa,
		};
	}

	public static DynamicHeatShader2 getHeatShader() {
		return (DynamicHeatShader2) heatShader.getE();
	}

	public static void setHeatShader(boolean b) {
		ConfigurableEffectsPack.heatShader.setState(b);
	}

	public static FragmentExpose getExpose() {
		return (FragmentExpose) expose.getE();
	}

	public static void setExpose(boolean b) {
		ConfigurableEffectsPack.expose.setState(b);
	}

	public static LowExpose getExpose2() {
		return (LowExpose) expose2.getE();
	}

	public static void setExpose2(boolean b) {
		ConfigurableEffectsPack.expose2.setState(b);
	}

	public static MotionBlur getMotionBlur() {
		return (MotionBlur) motionBlur.getE();
	}

	public static void setMotionBlur(boolean b) {
		ConfigurableEffectsPack.motionBlur.setState(b);
	}

	public static ColorEffectsShader getColorEffects() {
		return (ColorEffectsShader) colorEffects.getE();
	}

	public static void setColorEffects(boolean b) {
		ConfigurableEffectsPack.colorEffects.setState(b);
	}

	public static FXAA getFxaa() {
		return (FXAA) fxaa.getE();
	}

	public static void setFxaa(boolean b) {
		ConfigurableEffectsPack.fxaa.setState(b);
	}
}

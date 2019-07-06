package rpgonline.post;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import slickshader.Shader;

public class ThemeEffect extends ShaderEffect {
	private Color bc;
	private Color lc;
	private Color mc;
	private Color hc;
	private Color wc;
	private float lp;
	private float mp;
	private float hp;

	public ThemeEffect(Color bc, Color lc, Color mc, Color hc, Color wc, float lp, float mp, float hp) {
		super(ThemeEffect.class.getResource("/generic.vrt"), ThemeEffect.class.getResource("/theming.frg"));
		this.bc = bc;
		this.lc = lc;
		this.mc = mc;
		this.hc = hc;
		this.wc = wc;
		this.lp = lp;
		this.mp = mp;
		this.hp = hp;
	}

	public ThemeEffect(Color lc, Color mc, Color hc, float lp, float mp, float hp) {
		this(Color.black, lc, mc, hc, Color.white, lp, mp, hp);
	}

	public ThemeEffect(Color lc, Color mc, Color hc) {
		this(Color.black, lc, mc, hc, Color.white, 0.1f, 0.35f, 0.75f);
	}
	
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformColor("bc", bc);
		shader.setUniformColor("lc", lc);
		shader.setUniformColor("mc", mc);
		shader.setUniformColor("hc", hc);
		shader.setUniformColor("wc", wc);
		
		shader.setUniformFloatVariable("lp", lp);
		shader.setUniformFloatVariable("mp", mp);
		shader.setUniformFloatVariable("hp", hp);
	}
}

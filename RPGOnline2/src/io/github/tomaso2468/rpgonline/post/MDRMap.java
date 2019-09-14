package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * An effect for mapping tones in MDR rendering. <b>This is set automatically when MDR is enabled.</b>
 * @author Tomas
 *
 */
public class MDRMap extends ShaderEffect {
	/**
	 * The exposure of the MDR tone map.
	 */
	private float exposure = 1f;
	/**
	 * The gamma of the screen.
	 */
	private float gamma = 1.2f;
	
	/**
	 * Constructs a new MDRMap with an exposure of 1 for a monitor with a gamma of 1.2.
	 */
	public MDRMap() {
		super(MDRMap.class.getResource("/mdr.frg"));
	}

	/**
	 * Constructs a new MDRMap.
	 * @param exposure The exposure.
	 * @param gamma The gamma of the monitor (normally 1.2).
	 */
	public MDRMap(float exposure, float gamma) {
		super(MDRMap.class.getResource("/mdr.frg"));
		this.exposure = exposure;
		this.gamma = gamma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initShader(Shader shader, GameContainer c) {
		super.initShader(shader, c);
		shader.setUniformFloatVariable("exposure", exposure);
		shader.setUniformFloatVariable("gamma", gamma);
	}
}

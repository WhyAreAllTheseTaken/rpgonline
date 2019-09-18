/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * <p>
 * An effect which shifts colors to a consistent palette.
 * </p>
 * <p>
 * Colors are interpolated between 5 colors. The points of interpolation switch
 * at 5 values. 3 of these switch points can be moved. The other 2 switch points
 * are set to 0 and 1. If HDR is in use this effect should be applied after tone
 * mapping.
 * </p>
 * <p>
 * When setting a color it is usually best to set it to full brightness and just
 * adjust hue and saturation as altering the brightness may cause strange
 * effects.
 * </p>
 * 
 * @author Tomas
 */
public class ThemeEffect extends ShaderEffect {
	/**
	 * The color used as the 1st point set to {@code 0} brightness. This should be
	 * black or a fog color.
	 */
	private Color bc;
	/**
	 * The color used as the 2nd point set to {@code lp} brightness. This should be
	 * the color of shadows/darkness.
	 */
	private Color lc;
	/**
	 * The color used as the 3rd point set to {@code mp} brightness. This should be
	 * the main color of the scene.
	 */
	private Color mc;
	/**
	 * The color used as the 4th point set to {@code hp} brightness. This should be
	 * the color used for the brighter parts of the scene (around but no on lights).
	 */
	private Color hc;
	/**
	 * The color used as the 5th point set to {@code 1} brightness. This should be
	 * the color of a full brightness light.
	 */
	private Color wc;
	/**
	 * The brightness required for the 2nd color point.
	 */
	private float lp;
	/**
	 * The brightness required for the 3rd color point.
	 */
	private float mp;
	/**
	 * The brightness required for the 4th color point.
	 */
	private float hp;

	/**
	 * Constructs a new ThemeEffect.
	 * 
	 * @param bc The color used as the 1st point set to {@code 0} brightness. This
	 *           should be black or a fog color.
	 * @param lc The color used as the 2nd point set to {@code lp} brightness. This
	 *           should be the color of shadows/darkness.
	 * @param mc The color used as the 3rd point set to {@code mp} brightness. This
	 *           should be the main color of the scene.
	 * @param hc The color used as the 4th point set to {@code hp} brightness. This
	 *           should be the color used for the brighter parts of the scene
	 *           (around but no on lights).
	 * @param wc The color used as the 5th point set to {@code 1} brightness. This
	 *           should be the color of a full brightness light.
	 * @param lp The brightness required for the 2nd color point.
	 * @param mp The brightness required for the 3rd color point.
	 * @param hp The brightness required for the 4th color point.
	 */
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

	/**
	 * Constructs a new ThemeEffect.
	 * 
	 * @param lc The color used as the 2nd point set to {@code lp} brightness. This
	 *           should be the color of shadows/darkness.
	 * @param mc The color used as the 3rd point set to {@code mp} brightness. This
	 *           should be the main color of the scene.
	 * @param hc The color used as the 4th point set to {@code hp} brightness. This
	 *           should be the color used for the brighter parts of the scene
	 *           (around but no on lights).
	 * @param lp The brightness required for the 2nd color point.
	 * @param mp The brightness required for the 3rd color point.
	 * @param hp The brightness required for the 4th color point.
	 */
	public ThemeEffect(Color lc, Color mc, Color hc, float lp, float mp, float hp) {
		this(Color.black, lc, mc, hc, Color.white, lp, mp, hp);
	}

	/**
	 * Constructs a new ThemeEffect.
	 * 
	 * @param lc The color used as the 2nd point set to {@code lp} brightness. This
	 *           should be the color of shadows/darkness.
	 * @param mc The color used as the 3rd point set to {@code mp} brightness. This
	 *           should be the main color of the scene.
	 * @param hc The color used as the 4th point set to {@code hp} brightness. This
	 *           should be the color used for the brighter parts of the scene
	 *           (around but no on lights).
	 */
	public ThemeEffect(Color lc, Color mc, Color hc) {
		this(Color.black, lc, mc, hc, Color.white, 0.1f, 0.35f, 0.75f);
	}

	/**
	 * {@inheritDoc}
	 */
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

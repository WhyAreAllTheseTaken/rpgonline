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
package io.github.tomaso2468.rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import io.github.tomaso2468.rpgonline.ColorUtils.SunColorGenerator;
import io.github.tomaso2468.rpgonline.texture.TextureMap;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * A layer that renders a moon.
 * @author Tomas
 *
 */
public abstract class MoonLayer implements SkyLayer {
	/**
	 * A sun color generator.
	 */
	private final SunColorGenerator sg;
	/**
	 * Constructs a new MoonLayer.
	 * @param sg A sun color generator.
	 */
	public MoonLayer(SunColorGenerator sg) {
		this.sg = sg;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		double time = getTime();
		float sx = (float) sg.getMoonX(time) * c.getWidth() / 2 + c.getWidth() / 2;
		float sy =  (float) sg.getMoonY(time) * c.getHeight() / 2 + c.getHeight() / 2;
		float size = 196 * (c.getHeight() / 1440f);
		
		Image img = TextureMap.getTexture("moon").getScaledCopy((int) size, (int) size);
		g.drawImage(img, sx - size / 2, sy - size / 2, light.brighter(1));
	}
	
	/**
	 * Gets the current time.
	 * @return A time in hours.
	 */
	public abstract double getTime();
}

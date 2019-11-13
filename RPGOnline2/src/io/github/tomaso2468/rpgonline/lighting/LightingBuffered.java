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
package io.github.tomaso2468.rpgonline.lighting;

import java.util.List;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.TextureMap;
import io.github.tomaso2468.rpgonline.render.ColorMode;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.world2d.LightSource;
import io.github.tomaso2468.rpgonline.world2d.World;

public class LightingBuffered implements LightingEngine {
	/**
	 * A buffer for lighting.
	 */
	protected Image lightBuffer;
	
	@Override
	public void preRender(Game game, Renderer renderer, List<LightSource> lights, World world, float sx, float sy, float zoom, float base_scale, float shake) {
		
	}

	@Override
	public void postRender(Game game, Renderer renderer, List<LightSource> lights, World world, float sx, float sy, float zoom, float base_scale, float shake) throws RenderException {
		Image currentBuffer = renderer.getCurrentTarget();
		if (lights.size() == 0) {
			Color light = world.getLightColor();
			renderer.setMode(RenderMode.MODE_2D_COLOR_NOVBO);
			renderer.setColorMode(ColorMode.MULTIPLY);
			renderer.drawQuad(0, 0, game.getWidth(), game.getHeight(), light);
			renderer.setColorMode(ColorMode.NORMAL);
			renderer.setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
		} else {
			if (lightBuffer == null) {
				lightBuffer = new Image(renderer, game.getWidth(), game.getHeight());
			} else if (game.getWidth() != lightBuffer.getWidth() || game.getHeight() != lightBuffer.getHeight()) {
				lightBuffer.destroy();
				lightBuffer = new Image(renderer, game.getWidth(), game.getHeight());
			}

			renderer.setRenderTarget(lightBuffer);

			renderer.clear();

			renderer.setColorMode(ColorMode.NORMAL);

			Color wl = world.getLightColor();

			renderer.setMode(RenderMode.MODE_2D_COLOR_NOVBO);
			renderer.drawQuad(0, 0, renderer.getWidth(), renderer.getHeight(), wl);
			renderer.setMode(RenderMode.MODE_2D_SPRITE_NOVBO);

			renderer.translate2D(game.getWidth() / 2, game.getHeight() / 2);

			renderer.scale2D(base_scale, base_scale);
			renderer.pushTransform();

			renderer.scale2D(zoom, zoom);
			if (shake > 0) {
				renderer.translate2D((float) (FastMath.random() * shake * 5),
						(float) (FastMath.random() * shake * 5));
			}

			renderer.setColorMode(ColorMode.SCREEN);

			for (LightSource l : lights) {
				Image img = TextureMap.getTexture("light").getScaledCopy(l.getBrightness() / 5);
				
				renderer.startUse(img);
				renderer.drawQuad((float) l.getLX() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 5 - sx,
						(float) l.getLY() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 5 - sy,
						img.getWidth(), img.getHeight(), Color.white);
				renderer.endUse(img);
			}

			renderer.resetTransform();

			renderer.setRenderTarget(currentBuffer);

			renderer.setColorMode(ColorMode.MULTIPLY);
			renderer.drawImage(lightBuffer, 0, 0);
			renderer.setColorMode(ColorMode.NORMAL);
		}
	}

}

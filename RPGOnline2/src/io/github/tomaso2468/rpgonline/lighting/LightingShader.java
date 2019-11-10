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

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.render.ColorMode;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.render.Shader;
import io.github.tomaso2468.rpgonline.world2d.LightSource;
import io.github.tomaso2468.rpgonline.world2d.World;
import io.github.tomaso2468.rpgonline.world2d.WorldState;

public class LightingShader implements LightingEngine {
	@Override
	public void preRender(Game game, Renderer renderer, List<LightSource> lights, World world, float sx, float sy,
			float zoom, float base_scale, float shake) throws RenderException {
		currentTarget = renderer.getCurrentTarget();
		
		if (lightBuffer == null) {
			lightBuffer = new Image(renderer, game.getWidth(), game.getHeight());
		} else if (game.getWidth() != lightBuffer.getWidth() || game.getHeight() != lightBuffer.getHeight()) {
			lightBuffer.destroy();
			lightBuffer = new Image(renderer, game.getWidth(), game.getHeight());
		}

		renderer.setRenderTarget(lightBuffer);
		renderer.clear();
	}
	
	private Image currentTarget;
	private Shader lightingShader;
	protected Image lightBuffer;

	@Override
	public void postRender(Game game, Renderer renderer, List<LightSource> lights, World world, float sx, float sy,
			float zoom, float base_scale, float shake) throws RenderException {
		renderer.setRenderTarget(currentTarget);
		
		renderer.resetTransform();
		
		if (lightingShader == null) {
			lightingShader = renderer.createShader(WorldState.class.getResource("/generic.vrt"),
					WorldState.class.getResource("/lighting.frg"));
		}
		renderer.useShader(lightingShader);

		lightingShader.setUniform("ambientLight", world.getLightColor());
		
		int light_count = lights.size();
		
		lightingShader.setUniform("light_count", light_count);

		float dist_x_f = game.getWidth() / base_scale / zoom / RPGConfig.getTileSize() / lightBuffer.getTextureWidth();
		float dist_y_f = game.getHeight() / base_scale / zoom / RPGConfig.getTileSize() / lightBuffer.getTextureHeight();
		
		for (int i = 0; i < lights.size(); i++) {
			LightSource light = lights.get(i);
			
			float x = (float) light.getLX() - sx / RPGConfig.getTileSize() + 0.5f;
			float y = (float) light.getLY() - sy / RPGConfig.getTileSize() + 0.5f;
			
			lightingShader.setUniformArrayStruct("lights", 0, "location", lightBuffer.getTextureWidth() / 2 + x / dist_x_f, -lightBuffer.getTextureHeight() / 2 + y / dist_y_f);
			lightingShader.setUniformArrayStruct("lights", 0, "lightColor", light.getColor());
//			lightingShader.setUniformArrayStruct("lights", 0, "lightColor", new Color(1f, 1f, 1f));
		}

		lightingShader.setUniform("worldScale", dist_x_f, dist_y_f);
		
		renderer.drawImage(lightBuffer, 0, 0);

		renderer.useShader(null);
		
		renderer.setMode(RenderMode.MODE_2D_COLOR_NOVBO);
		renderer.setColorMode(ColorMode.MULTIPLY);
//		renderer.drawQuad(0, 0, game.getWidth(), game.getHeight(), Color.white);
		renderer.setColorMode(ColorMode.NORMAL);
		renderer.setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
	}

}

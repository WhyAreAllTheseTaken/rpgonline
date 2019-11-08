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

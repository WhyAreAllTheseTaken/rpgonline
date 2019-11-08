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

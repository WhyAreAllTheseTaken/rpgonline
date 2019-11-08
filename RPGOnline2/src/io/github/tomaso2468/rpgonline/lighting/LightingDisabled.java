package io.github.tomaso2468.rpgonline.lighting;

import java.util.List;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.render.ColorMode;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.world2d.LightSource;
import io.github.tomaso2468.rpgonline.world2d.World;

public class LightingDisabled implements LightingEngine {

	@Override
	public void preRender(Game game, Renderer renderer, List<LightSource> lights, World world, float sx, float sy, float zoom, float base_scale, float shake) {
		
	}

	@Override
	public void postRender(Game game, Renderer renderer, List<LightSource> lights, World world, float sx, float sy, float zoom, float base_scale, float shake) {
		renderer.setMode(RenderMode.MODE_2D_COLOR_NOVBO);
		renderer.setColorMode(ColorMode.MULTIPLY);
		// This just resets the render mode.
		renderer.setColorMode(ColorMode.NORMAL);
		renderer.setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
	}
	
}

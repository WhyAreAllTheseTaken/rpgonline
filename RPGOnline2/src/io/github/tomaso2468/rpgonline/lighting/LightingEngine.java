package io.github.tomaso2468.rpgonline.lighting;

import java.util.List;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.world2d.LightSource;
import io.github.tomaso2468.rpgonline.world2d.World;

public interface LightingEngine {
	public void preRender(Game game, Renderer renderer, List<LightSource> lights, World world, float sx, float sy, float zoom, float base_scale, float shake) throws RenderException;
	public void postRender(Game game, Renderer renderer, List<LightSource> lights, World world, float sx, float sy, float zoom, float base_scale, float shake) throws RenderException;
}

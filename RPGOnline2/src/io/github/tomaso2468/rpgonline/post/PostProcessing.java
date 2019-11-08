package io.github.tomaso2468.rpgonline.post;

import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;

@FunctionalInterface
public interface PostProcessing {
	public void postProcess(Image input, Image output, Renderer renderer) throws RenderException;
}

package io.github.tomaso2468.rpgonline.post;

import java.net.URL;

import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.render.Shader;

public class ShaderPost implements PostProcessing {
	protected Shader shader;
	private URL vertex;
	private URL fragment;
	
	public ShaderPost(URL vertex, URL fragment) {
		this.vertex = vertex;
		this.fragment = fragment;
	}
	
	public ShaderPost(Shader shader) {
		this.shader = shader;
	}

	@Override
	public void postProcess(Image input, Image output, Renderer renderer) throws RenderException {
		if (shader == null) {
			shader = renderer.createShader(vertex, fragment);
			renderer.useShader(shader);
			initShader(shader, renderer);
		} else {
			renderer.useShader(shader);
		}
		updateShader(shader, renderer);
		
		preDraw(shader, renderer);
		renderer.drawImage(input, 0, 0);
		postDraw(shader, renderer);
		
		renderer.useShader(null);
	}
	
	public void initShader(Shader shader, Renderer renderer) throws RenderException {
		
	}
	
	public void updateShader(Shader shader, Renderer renderer) throws RenderException {
		
	}
	
	public void preDraw(Shader shader, Renderer renderer) throws RenderException {
		
	}
	
	public void postDraw(Shader shader, Renderer renderer) throws RenderException {
		
	}
}

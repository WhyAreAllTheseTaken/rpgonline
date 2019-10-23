package io.github.tomaso2468.rpgonline.render.opengl;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.Pbuffer;
import org.newdawn.slick.opengl.Texture;

import io.github.tomaso2468.rpgonline.TextureReference;

class SlickTexture implements TextureReference {
	Texture texture;
	int fbo;
	Pbuffer pbuffer;
	
	SlickTexture(Texture texture) {
		this.texture = texture;
	}

	@Override
	public int getWidth() {
		return texture.getImageWidth();
	}

	@Override
	public int getHeight() {
		return texture.getImageHeight();
	}

	@Override
	public float getLibraryWidth() {
		return texture.getWidth();
	}

	@Override
	public float getLibraryHeight() {
		return texture.getHeight();
	}
	
	@Override
	public void bind() {
		texture.bind();
	}

	@Override
	public void destroy() {
		texture.release();
		if (fbo != 0) {
			EXTFramebufferObject.glDeleteFramebuffersEXT(fbo);
		}
	}

}

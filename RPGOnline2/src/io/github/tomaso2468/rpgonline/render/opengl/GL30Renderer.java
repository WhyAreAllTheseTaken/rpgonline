package io.github.tomaso2468.rpgonline.render.opengl;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RenderException;
import io.github.tomaso2468.rpgonline.render.ColorMode;
import io.github.tomaso2468.rpgonline.render.RenderMode;

public abstract class GL30Renderer extends GL20Renderer {
	protected boolean offscreen = false;
	protected int offscreen_buffer;
	protected int fbo;
	protected Image fbo_image;

	@Override
	public int getRenderWidth() {
		if (fbo_image != null) {
			return (int) fbo_image.getWidth();
		} else {
			return super.getRenderWidth();
		}
	}

	@Override
	public int getRenderHeight() {
		if (fbo_image != null) {
			return (int) fbo_image.getHeight();
		} else {
			return super.getRenderHeight();
		}
	}

	protected void checkFBO(int fbo) throws RenderException {
		int framebuffer = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
		switch (framebuffer) {
		case GL30.GL_FRAMEBUFFER_COMPLETE:
			break;
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
			throw new RenderException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
			throw new RenderException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
			throw new RenderException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
			throw new RenderException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
		default:
			throw new RenderException("Unknown: " + framebuffer);
		}
	}

	@Override
	public void setRenderTarget(Image image) throws RenderException {
		if (image == null) {
			if (this.fbo != 0) {
				this.fbo = 0;
				this.fbo_image = null;
				GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);   
				setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
				setColorMode(ColorMode.NORMAL);
				resetTransform();
			}
			return;
		}
		if (((SlickTexture) image.getTexture()).fbo == 0) {
			if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
				Log.warn("FBOs are not supported on your system, falling back to pbuffers.");
				// Fallback to pbuffer
				super.setRenderTarget(image);
			}

			int fbo = GL30.glGenFramebuffers();
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
			
			Texture tex;
			try {
				tex = InternalTextureLoader.get().createTexture((int) image.getWidth(), (int) image.getHeight(), image.getFilter());
			} catch (IOException e) {
				throw new RenderException("Failed to create texture.");
			}

			int filter = image.getFilter() == Image.FILTER_LINEAR ? GL11.GL_LINEAR : GL11.GL_NEAREST;
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter); 
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
	        
	        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, tex.getTextureID(), 0);
	        
	        ((SlickTexture) image.getTexture()).texture = tex;
			((SlickTexture) image.getTexture()).fbo = fbo;
			
			checkFBO(fbo);
		}

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ((SlickTexture) image.getTexture()).fbo);
		this.fbo = ((SlickTexture) image.getTexture()).fbo;
		this.fbo_image = image;
		setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
		setColorMode(ColorMode.NORMAL);
		resetTransform();
	}
}

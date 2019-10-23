package io.github.tomaso2468.rpgonline.render.opengl;

import java.io.IOException;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RenderException;

public abstract class GL30Renderer extends GL20Renderer {
	protected int fbo;

	protected void bindFBO(int fbo) {
		this.fbo = fbo;
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fbo);
		GL11.glReadBuffer(EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT);
	}

	protected void unbindFBO(int fbo) {
		this.fbo = 0;
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		GL11.glReadBuffer(GL11.GL_BACK);

		GL11.glPopClientAttrib();
		GL11.glPopAttrib();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	protected void checkFBO(int fbo) throws RenderException {
		int framebuffer = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		switch (framebuffer) {
		case EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT:
			break;
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT:
			throw new RenderException(
					"FrameBuffer: " + fbo + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT:
			throw new RenderException("FrameBuffer: " + fbo
					+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT:
			throw new RenderException(
					"FrameBuffer: " + fbo + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT:
			throw new RenderException(
					"FrameBuffer: " + fbo + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT:
			throw new RenderException(
					"FrameBuffer: " + fbo + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT:
			throw new RenderException(
					"FrameBuffer: " + fbo + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception");
		default:
			throw new RenderException("Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer);
		}
	}

	@Override
	public void setRenderTarget(Image image) throws RenderException {
		if (image == null) {
			if (this.fbo != 0) {
				unbindFBO(fbo);
			}
			return;
		}
		if (((SlickTexture) image.getTexture()).fbo == 0) {
			if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
				Log.warn("FBOs are not supported on your system, falling back to pbuffers.");
				// Fallback to pbuffer
				super.setRenderTarget(image);
			}

			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			EXTFramebufferObject.glGenFramebuffersEXT(buffer);
			int fbo = buffer.get();

			// for some reason FBOs won't work on textures unless you've absolutely just
			// created them.
			try {
				Texture tex = InternalTextureLoader.get().createTexture((int) image.getWidth(), (int) image.getHeight(),
						image.getFilter());

				bindFBO(fbo);
				EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
						EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, tex.getTextureID(), 0);

				checkFBO(fbo);
				unbindFBO(fbo);

				// Clear our destination area before using it
				clear();

				// keep hold of the original content
				drawImage(image, 0, 0);
				((SlickTexture) image.getTexture()).texture = tex;
				((SlickTexture) image.getTexture()).fbo = fbo;

			} catch (IOException e) {
				throw new RenderException("Failed to create new texture for FBO", e);
			}
		}

		bindFBO(((SlickTexture) image.getTexture()).fbo);
	}
}

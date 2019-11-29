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
package io.github.tomaso2468.rpgonline.render.opengl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.render.ColorMode;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.RenderResourceException;

public abstract class GL30Renderer extends GL20Renderer {
	protected boolean offscreen = false;
	protected int offscreen_buffer;
	protected int fbo;
	protected Image fbo_image;
	protected boolean hdr;
	private List<Integer> fbos = new ArrayList<Integer>();

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
	
	@Override
	public Image getCurrentTarget() throws RenderException {
		if (fbo_image != null) {
			return fbo_image;
		} else {
			return super.getCurrentTarget();
		}
	}

	protected void checkFBO(int fbo) throws RenderException {
		int framebuffer = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
		
		if (framebuffer != GL30.GL_FRAMEBUFFER_COMPLETE) {
			throw new FBOException(framebuffer);
		}
	}
	
	@Override
	public boolean isHDR() {
		return hdr;
	}
	
	@Override
	public void useHDRBuffers(boolean hdr) throws RenderException {
		this.hdr = hdr;
	}
	
	protected Texture createHDR(int width, int height) {
		int tex = GL11.glGenTextures();
		
		int width_fold = InternalTextureLoader.get2Fold(width);
		int height_fold = InternalTextureLoader.get2Fold(height);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_RGBA16F, width_fold, height_fold, 0, GL11.GL_RGBA, GL11.GL_FLOAT, BufferUtils.createByteBuffer(width_fold * height_fold * 4 * 4));
		
		TextureImpl texture = new TextureImpl("createHDR", GL11.GL_TEXTURE_2D, tex);
		
		texture.setTextureWidth(InternalTextureLoader.get2Fold(width));
		texture.setTextureHeight(InternalTextureLoader.get2Fold(height));
		
		texture.setWidth(width);
		texture.setHeight(height);
		
		return texture;
	}

	private boolean fbo_ext_warn = false;
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
				if (!fbo_ext_warn) Log.warn("FBOs are not supported on your system, falling back to pbuffers.");
				fbo_ext_warn = true;
				// Fallback to pbuffer
				super.setRenderTarget(image);
				return;
			}

			int fbo = GL30.glGenFramebuffers();
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
			
			Texture tex;
			try {
				tex = hdr ? createHDR((int) image.getWidth(), (int) image.getHeight()) : InternalTextureLoader.get().createTexture((int) image.getWidth(), (int) image.getHeight(), image.getFilter().slickMapping);
			} catch (IOException e) {
				throw new RenderResourceException("Failed to create texture.");
			}

			int filter = image.getFilter().glMapping;
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter); 
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
	        
	        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, tex.getTextureID(), 0);
	        
	        ((SlickTexture) image.getTexture()).texture = tex;
			((SlickTexture) image.getTexture()).fbo = fbo;
			
			image.ensureInverted();
			
			checkFBO(fbo);
			
			fbos.add(fbo);
		}

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ((SlickTexture) image.getTexture()).fbo);
		this.fbo = ((SlickTexture) image.getTexture()).fbo;
		this.fbo_image = image;
		setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
		setColorMode(ColorMode.NORMAL);
		resetTransform();
	}
	
	@Override
	public void exit(Game game) {
		for (int i : fbos) {
			GL30.glDeleteFramebuffers(i);
		}
	}
}

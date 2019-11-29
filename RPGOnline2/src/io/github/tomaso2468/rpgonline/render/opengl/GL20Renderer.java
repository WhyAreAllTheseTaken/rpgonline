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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.RenderTexture;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.render.ColorMode;
import io.github.tomaso2468.rpgonline.render.FeatureException;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.RenderResourceException;
import io.github.tomaso2468.rpgonline.render.Shader;
import io.github.tomaso2468.rpgonline.render.ShaderCompileException;
import io.github.tomaso2468.rpgonline.render.ShaderLinkException;

public abstract class GL20Renderer extends GL12Renderer {
	@Override
	public void useShader(Shader shader) {
		if (shader == null) {
			GL20.glUseProgram(0);
		} else {
			GL20.glUseProgram(((GLShader) shader).program);
		}
	}

	protected String read(URL url) throws IOException {
		List<Byte> data = new ArrayList<>();
		InputStream in = new BufferedInputStream(url.openStream());

		while (in.available() > 0) {
			data.add((byte) in.read());
		}

		in.close();

		byte[] data2 = new byte[data.size()];
		for (int i = 0; i < data.size(); i++) {
			data2[i] = data.get(i);
		}

		return new String(data2);
	}

	public static final int MAX_LOG_LENGTH = Short.MAX_VALUE;

	protected int compileShader(int type, String src) throws RenderException {
		int shader = GL20.glCreateShader(type);
		GL20.glShaderSource(shader, src);
		GL20.glCompileShader(shader);

		int errorCheck = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
		if (errorCheck != GL11.GL_TRUE) {
			String log = GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH));
			
			GL20.glDeleteShader(shader);
			throw new ShaderCompileException("Error compiling shaders:\n" + log);
		}
		return shader;
	}

	protected int createProgram(int vertex, int fragment) throws RenderException {
		int program = GL20.glCreateProgram();
		GL20.glAttachShader(program, vertex);
		GL20.glAttachShader(program, fragment);

		GL20.glLinkProgram(program);
		int errorCheck = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);
		if (errorCheck != GL11.GL_TRUE) {
			String log = GL20.glGetProgramInfoLog(program, MAX_LOG_LENGTH);
			throw new ShaderLinkException("Error linking program:\n" + log);
		}

		GL20.glValidateProgram(program);
		int errorCheck2 = GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS);
		if (errorCheck2 != GL11.GL_TRUE) {
			String log = GL20.glGetProgramInfoLog(program, MAX_LOG_LENGTH);
			throw new ShaderLinkException("Error validating program:\n" + log);
		}

		return program;
	}

	@Override
	public Shader createShader(URL vertex, URL fragment) throws RenderException {
		Log.debug("Compiling shader v=" + vertex.toString() + " f=" + fragment.toString());
		
		String vertexData, fragmentData;
		try {
			vertexData = read(vertex);
		} catch (IOException e) {
			throw new RenderResourceException("Error reading a vertex shader ", e);
		}
		try {
			fragmentData = read(fragment);
		} catch (IOException e) {
			throw new RenderResourceException("Error reading a fragment shader", e);
		}

		int vertexShader = compileShader(GL20.GL_VERTEX_SHADER, vertexData);
		int fragmentShader = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentData);

		int program = createProgram(vertexShader, fragmentShader);

		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);

		return new GLShader(program);
	}

	@Override
	public void deleteShader(Shader shader) throws RenderException {
		GL20.glDeleteShader(((GLShader) shader).program);
	}

	@Override
	public int getRenderWidth() {
		if (currentBuffer != null) {
			return currentBuffer.getWidth();
		} else {
			return super.getRenderWidth();
		}
	}

	@Override
	public int getRenderHeight() {
		if (currentBuffer != null) {
			return currentBuffer.getHeight();
		} else {
			return super.getRenderHeight();
		}
	}
	
	@Override
	public Image getCurrentTarget() throws RenderException {
		return currentImage;
	}

	protected Pbuffer currentBuffer = null;
	protected Image currentImage = null;

	protected void unbindBuffer(Pbuffer pbuffer, Image image) throws RenderException {
		this.currentBuffer = null;
		this.currentImage = null;

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((SlickTexture) image.getTexture()).texture.getTextureID());
		pbuffer.bindTexImage(Pbuffer.FRONT_LEFT_BUFFER);

		try {
			Display.makeCurrent();
		} catch (LWJGLException e) {
			throw new RenderException("Error making display current.");
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glPopClientAttrib();
		GL11.glPopAttrib();
	}

	protected void bindBuffer(Pbuffer pbuffer, Image image) throws RenderException {
		GL11.glDisable(GL11.GL_TEXTURE);
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		this.currentBuffer = pbuffer;
		this.currentImage = image;
		try {
			if (pbuffer.isBufferLost()) {
				pbuffer.destroy();
				Texture tex;
				try {
					tex = InternalTextureLoader.get().createTexture((int) image.getWidth(), (int) image.getHeight(),
							image.getFilter().slickMapping);
				} catch (IOException e) {
					throw new RenderResourceException("An error occured creating a texture.", e);
				}

				final RenderTexture rt = new RenderTexture(false, true, false, false, RenderTexture.RENDER_TEXTURE_2D,
						0);
				try {
					pbuffer = new Pbuffer((int) image.getWidth(), (int) image.getHeight(), new PixelFormat(8, 0, 0), rt,
							null);
				} catch (LWJGLException e) {
					throw new RenderResourceException("Error occured creating pbuffer.", e);
				}

				try {
					pbuffer.makeCurrent();
				} catch (LWJGLException e) {
					throw new RenderException("Error occured changing render target to pbuffer.", e);
				}

				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDisable(GL11.GL_LIGHTING);

				GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
				GL11.glClearDepth(1);

				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				GL11.glViewport(0, 0, (int) image.getWidth(), (int) image.getHeight());
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();

				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0, (int) image.getWidth(), 0, (int) image.getHeight(), 1, -1);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);

				GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
				pbuffer.releaseTexImage(Pbuffer.FRONT_LEFT_BUFFER);
				render(image, 0, 0, image.getWidth(), image.getHeight());
				((SlickTexture) image.getTexture()).pbuffer = pbuffer;
				((SlickTexture) image.getTexture()).texture = tex;

				try {
					Display.makeCurrent();
				} catch (LWJGLException e) {
					throw new RenderException("Error occured changing render target to screen.", e);
				}
			}

			pbuffer.makeCurrent();
		} catch (LWJGLException e) {
			throw new RenderException("Could not recreate Pbuffer", e);
		}

		// Put the renderer contents to the texture
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((SlickTexture) image.getTexture()).texture.getTextureID());
		pbuffer.releaseTexImage(Pbuffer.FRONT_LEFT_BUFFER);
		TextureImpl.unbind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, (int) image.getWidth(), (int) image.getHeight());
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, (int) image.getWidth(), 0, (int) image.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	@Override
	public void setRenderTarget(Image image) throws RenderException {
		if (image == null) {
			if (this.currentBuffer != null) {
				unbindBuffer(this.currentBuffer, image);
				setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
			}
			return;
		}
		if (((SlickTexture) image.getTexture()).pbuffer == null) {
			if ((Pbuffer.getCapabilities() & Pbuffer.PBUFFER_SUPPORTED) == 0) {
				throw new FeatureException("PBuffers are not supported on your system.");
			}
			if ((Pbuffer.getCapabilities() & Pbuffer.RENDER_TEXTURE_SUPPORTED) == 0) {
				throw new FeatureException(
						"Cannot render to a texture due to lack of render-to-texture support on this system.");
			}

			Texture tex;
			try {
				tex = InternalTextureLoader.get().createTexture((int) image.getWidth(), (int) image.getHeight(),
						image.getFilter().slickMapping);
			} catch (IOException e) {
				throw new RenderResourceException("An error occured creating a texture.", e);
			}

			final RenderTexture rt = new RenderTexture(false, true, false, false, RenderTexture.RENDER_TEXTURE_2D, 0);
			Pbuffer pbuffer;
			try {
				pbuffer = new Pbuffer((int) image.getWidth(), (int) image.getHeight(), new PixelFormat(8, 0, 0), rt,
						null);
			} catch (LWJGLException e) {
				throw new RenderResourceException("Error occured creating pbuffer.", e);
			}

			try {
				pbuffer.makeCurrent();
			} catch (LWJGLException e) {
				throw new RenderException("Error occured changing render target to pbuffer.", e);
			}

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);

			GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			GL11.glClearDepth(1);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL11.glViewport(0, 0, (int) image.getWidth(), (int) image.getHeight());
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, (int) image.getWidth(), 0, (int) image.getHeight(), 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
			pbuffer.releaseTexImage(Pbuffer.FRONT_LEFT_BUFFER);
			render(image, 0, 0, image.getWidth(), image.getHeight());
			((SlickTexture) image.getTexture()).pbuffer = pbuffer;
			((SlickTexture) image.getTexture()).texture = tex;

			try {
				Display.makeCurrent();
			} catch (LWJGLException e) {
				throw new RenderException("Error occured changing render target to screen.", e);
			}
		}
		bindBuffer(((SlickTexture) image.getTexture()).pbuffer, image);
		setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
		setColorMode(ColorMode.NORMAL);
		resetTransform();
	}
}

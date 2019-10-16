package io.github.tomaso2468.rpgonline.render.opengl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.renderer.SGL;

import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RenderException;
import io.github.tomaso2468.rpgonline.TextureReference;
import io.github.tomaso2468.rpgonline.render.ColorMode;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.Renderer;

public abstract class GL11Renderer implements Renderer {
	private RenderMode mode = RenderMode.MODE_NONE;
	private ColorMode colorMode = ColorMode.NORMAL;
	private boolean pushed;
	private List<FloatBuffer> stack = new ArrayList<>();
	private int stackIndex;
	private float sx, sy;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderEmbedded(Image img, float x, float y, float w, float h) {
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY());
		GL11.glVertex3f(x, y, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY() + img.getTextureHeight());
		GL11.glVertex3f(x, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(), img.getTextureOffsetY()
				+ img.getTextureHeight());
		GL11.glVertex3f(x + w, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(), img.getTextureOffsetY());
		GL11.glVertex3f(x + w, y, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Image img, float x, float y, float w, float h) {
		startUse(img);
		renderEmbedded(img, x, y, w, h);
		endUse(img);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startUse(Image img) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endUse(Image img) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderFiltered(Image img, float x, float y, float w, float h, Color c) {
		startUse(img);
		
		GL11.glColor4f(c.r, c.g, c.b, c.a);
		
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY());
		GL11.glVertex3f(x, y, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY() + img.getTextureHeight());
		GL11.glVertex3f(x, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(), img.getTextureOffsetY()
				+ img.getTextureHeight());
		GL11.glVertex3f(x + w, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(), img.getTextureOffsetY());
		GL11.glVertex3f(x + w, y, 0);
		
		endUse(img);
	}

	@Override
	public void renderSheared(Image img, float x, float y, float w, float h, float hshear, float vshear) {
		startUse(img);
		
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY());
		GL11.glVertex3f(x, y, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY() + img.getTextureHeight());
		GL11.glVertex3f(x, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(), img.getTextureOffsetY()
				+ img.getTextureHeight());
		GL11.glVertex3f(x + w + hshear, y + h + vshear, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(), img.getTextureOffsetY());
		GL11.glVertex3f(x + w, y, 0);
		
		endUse(img);
	}
	
	@Override
	public void drawQuad(float x, float y, float w, float h, Color c) {
		// Fallthrough is used here. Later java versions could use a newer switch statement.
		switch(mode) {
		case MODE_2D_COLOR_NOVBO:
		case MODE_2D_LINES_NOVBO:
		case MODE_2D_SPRITE_NOVBO:
			GL11.glColor4f(c.r, c.g, c.b, c.a);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + w, y);
			GL11.glVertex2f(x + w, y + h);
			GL11.glVertex2f(x, y + h);
			GL11.glEnd();
			break;
		case MODE_NONE:
			break;
		default:
			break;
		}
	}

	@Override
	public void translate2D(float x, float y) {
		if (!pushed) {
			GL11.glPushMatrix();
			pushed = true;
		}
		GL11.glTranslatef(x, y, 0);
	}

	@Override
	public void scale2D(float x, float y) {
		if (!pushed) {
			GL11.glPushMatrix();
			pushed = true;
		}
		GL11.glScalef(x, y, 1);
		this.sx *= x;
		this.sy *= y;
	}
	
	@Override
	public void rotate2D(float x, float y, float a) {
		if (!pushed) {
			GL11.glPushMatrix();
			pushed = true;
		}
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(a, 0, 0, a);
		GL11.glTranslatef(-x, -y, 0);
	}
	
	@Override
	public void transform2D(Transform trans) {
		float[] tm = trans.getMatrixPosition(); // get the transform matrix

		// pad the transform to get a 4x4 3d affine transform
		float[] toBuffer = { tm[0], tm[3], 0, tm[6], tm[1], tm[4], 0, tm[7], 0, 0, 1, 0, tm[2], tm[5], 0, 1 };

		// GL11 wants a "direct" FloatBuffer, but you can only get that by creating a
		// direct ByteBuffer
		// and then creating a FloatBuffer as a view of that ByteBuffer.
		// the ByteBuffer is allocated 16*4 bytes, because there are 16 floats and each
		// float needs 4 bytes
		ByteBuffer bb = ByteBuffer.allocateDirect(16 * 4);

		// this has something to do with the default byte order setting in Java being
		// inappropriate
		bb.order(ByteOrder.nativeOrder());

		for (float f : toBuffer) {
			bb.putFloat(f);
		}
		bb.rewind();
		FloatBuffer transformBuffer = bb.asFloatBuffer();

		GL11.glMultMatrix(transformBuffer);
	}
	
	@Override
	public void resetTransform() {
		sx = 1;
		sy = 1;
		
		if (pushed) {
			GL11.glPopMatrix();
			pushed = false;
		}
	}
	
	@Override
	public void pushTransform() {
		FloatBuffer buffer;
		if (stackIndex >= stack.size()) {
			buffer = BufferUtils.createFloatBuffer(18);
			stack.add(buffer);
		} else {
			buffer = (FloatBuffer) stack.get(stackIndex);
		}
		
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, buffer);
		buffer.put(16, sx);
		buffer.put(17, sy);
		stackIndex += 1;
	}
	
	@Override
	public void popTransform() {
		stackIndex -= 1;
		FloatBuffer oldBuffer = (FloatBuffer) stack.get(stackIndex);
		GL11.glLoadMatrix(oldBuffer);
		sx = oldBuffer.get(16);
		sy = oldBuffer.get(17);
	}

	@Override
	public Graphics getGUIGraphics() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void clear() {
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f );
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	@Override
	public RenderMode getMode(RenderMode mode) {
		return mode;
	}
	
	@Override
	public void setMode(RenderMode mode) {
		if (this.mode == mode) {
			return;
		}
		this.mode = mode;
		
		if (mode == RenderMode.MODE_2D_COLOR_NOVBO || mode == RenderMode.MODE_2D_LINES_NOVBO || mode == RenderMode.MODE_2D_SPRITE_NOVBO) {
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, getWidth(), getHeight(), 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
		} else if (mode == RenderMode.MODE_NONE) {
			//Do nothing
		}
		
		if (mode == RenderMode.MODE_2D_SPRITE_NOVBO) {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);        
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);                    
	        
			GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
	        GL11.glClearDepth(1);                                       
	        
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}
	
	@Override
	public TextureReference getPNG(URL url) throws IOException {
		return new SlickTexture(TextureLoader.getTexture("PNG", new BufferedInputStream(url.openStream())));
	}
	
	@Override
	public void copyArea(Image buffer, int x, int y) {
		Texture texture = ((SlickTexture) buffer.getTexture()).texture;
		int format = texture.hasAlpha() ? GL11.GL_RGBA : GL11.GL_RGB;
		texture.bind();
		GL11.glCopyTexImage2D(SGL.GL_TEXTURE_2D, 0, format, x, getHeight() - (y + (int) buffer.getHeight()), texture.getTextureWidth(), texture.getTextureHeight(), 0);
		buffer.ensureInverted();
	}
	
	@Override
	public TextureReference createEmptyTexture(int width, int height) throws RenderException {
		try {
			return new SlickTexture(InternalTextureLoader.get().createTexture(width, height, Image.FILTER_NEAREST));
		} catch (IOException e) {
			throw new RenderException("Failed to create empty image", e);
		}
	}
	
	@Override
	public String getVendor() {
		return GL11.glGetString(GL11.GL_VENDOR);
	}
	
	@Override
	public String getVersion() {
		return GL11.glGetString(GL11.GL_VERSION);
	}
	
	@Override
	public String getRendererGL() {
		return "OpenGL";
	}
	
	@Override
	public void setColorMode(ColorMode mode) {
		if (this.colorMode == mode) {
			return;
		}
		this.colorMode = mode;
		
		switch(mode) {
		case ADD:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			break;
		case ALPHA_BLEND:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, false);
			GL11.glBlendFunc(GL11.GL_DST_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);
			break;
		case ALPHA_MAP:
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColorMask(false, false, false, true);
			break;
		case MULTIPLY:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, true);
			GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_COLOR, GL11.GL_SRC_COLOR);
			break;
		case NORMAL:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, true);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			break;
		case SCREEN:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_COLOR);
			break;
		default:
			break;
		}
	}
	
	@Override
	public ColorMode getColorMode(ColorMode mode) {
		return mode;
	}
	
	private int fbo;
	
	@Override
	public void setRenderTarget(Image image) throws RenderException {
		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
			throw new RenderException("Framebuffers are not supported on your system.");
		}
		
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		EXTFramebufferObject.glGenFramebuffersEXT(buffer); 
		fbo = buffer.get();
		
		Texture tex = InternalTextureLoader.get().createTexture((int) image.getWidth(), (int) image.getHeight(), image.getFilter());
		
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fbo);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 
													   EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT,
													   GL11.GL_TEXTURE_2D, tex.getTextureID(), 0);
		
		int framebuffer = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT); 
		switch ( framebuffer ) {
			case EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT:
				break;
			case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT:
				throw new SlickException( "FrameBuffer: " + fbo
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception" );
			case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT:
				throw new SlickException( "FrameBuffer: " + fbo
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception" );
			case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT:
				throw new SlickException( "FrameBuffer: " + fbo
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception" );
			case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT:
				throw new SlickException( "FrameBuffer: " + fbo
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception" );
			case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT:
				throw new SlickException( "FrameBuffer: " + fbo
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception" );
			case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT:
				throw new SlickException( "FrameBuffer: " + fbo
						+ ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception" );
			default:
				throw new SlickException( "Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer);
		}
		
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		GL11.glReadBuffer(GL11.GL_BACK); 
		
		// Clear our destination area before using it
		clear();
		flush();
		
		// keep hold of the original content
		drawImage(image, 0, 0);
		((SlickTexture) image.getTexture()).texture = tex;
	}
}

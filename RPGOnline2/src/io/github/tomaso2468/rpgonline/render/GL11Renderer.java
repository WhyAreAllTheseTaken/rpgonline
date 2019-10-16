package io.github.tomaso2468.rpgonline.render;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.Image;

public abstract class GL11Renderer implements Renderer {
	private RenderMode mode = RenderMode.MODE_NONE;
	
	@Override
	public void renderEmbedded(Image img, float x, float y, float w, float h) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Image img, float x, float y, float w, float h) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startUse(Image img) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endUse(Image img) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderFiltered(Image img, float x, float y, float w, float h, Color c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderSheared(Image img, float x, float y, float w, float h, float hshear, float vshear) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void drawQuad(float x, float y, float w, float h, Color c) {
		// Fallthrough is used here. Later java versions could use a newer switch statement.
		switch(mode) {
		case MODE_2D_COLOR_NOVBO:
		case MODE_2D_LINES_NOVBO:
		case MODE_2D_SPRITE_NOVBO:
			c.bind();

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
	public void translate(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(float x, float y) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void rotate(float x, float y, float a) {
		// TODO Auto-generated method stub
		
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
	}
}

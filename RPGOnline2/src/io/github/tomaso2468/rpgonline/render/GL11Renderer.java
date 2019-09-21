package io.github.tomaso2468.rpgonline.render;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * An OpenGL renderer for opengl 1.1.
 * @author Tomas
 *
 */
public class GL11Renderer implements Renderer {
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
	
	protected Object callStatic(Class<?> c, String method, Class<?>[] types, Object... args) {
		return callInstance(c, method, types, null, args);
	}
	
	protected Object callInstance(Class<?> c, String method, Class<?>[] types, Object o, Object... args) {
		try {
			Method m = c.getDeclaredMethod(method, types);
			m.setAccessible(true);
			
			Object r = m.invoke(o, args);
			
			return r;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new Error("Missing render method: " + method, e);
		}
	}
	
	protected Object getFieldInstance(Class<?> c, String field, Object o) {
		try {
			Field f = c.getDeclaredField(field);
			f.setAccessible(true);
			
			return f.get(o);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			throw new Error("Missing render field: " + field, e);
		}
	}
	
	protected Object getFieldStatic(Class<?> c, String field) {
		try {
			Field f = c.getDeclaredField(field);
			f.setAccessible(true);
			
			return f.get(null);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			throw new Error("Missing render field: " + field, e);
		}
	}
	
	protected void setFieldInstance(Class<?> c, String field, Object o, Object value) {
		try {
			Field f = c.getDeclaredField(field);
			f.setAccessible(true);
			
			f.set(o, value);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			throw new Error("Missing render field: " + field, e);
		}
	}
	
	protected void setFieldStatic(Class<?> c, String field, Object value) {
		try {
			Field f = c.getDeclaredField(field);
			f.setAccessible(true);
			
			f.set(null, value);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			throw new Error("Missing render field: " + field, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Image img, float x, float y, float w, float h) {
		startUse(img);
		
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startUse(Image img) {
		if (getFieldStatic(Image.class, "inUse") != null) {
			throw new RuntimeException("A texture is already in use.");
		}
		setFieldStatic(Image.class, "inUse", img);
		callInstance(Image.class, "init", new Class<?>[0], img);
		GL11.glColor4f(1, 1, 1, 1);
		
		img.getTexture().bind();
		
		GL11.glBegin(GL11.GL_QUADS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endUse(Image img) {
		setFieldStatic(Image.class, "inUse", null);
		
		GL11.glEnd();
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
	public void translate(Graphics g, float x, float y) {
		callInstance(g.getClass(), "checkPush", new Class<?>[0], g);

		callInstance(g.getClass(), "predraw", new Class<?>[0], g);
		GL11.glTranslatef(x, y, 0);
		callInstance(g.getClass(), "postdraw", new Class<?>[0], g);
	}

	@Override
	public void scale(Graphics g, float x, float y) {
		callInstance(g.getClass(), "checkPush", new Class<?>[0], g);

		callInstance(g.getClass(), "predraw", new Class<?>[0], g);
		GL11.glScalef(x, y, 1);
		callInstance(g.getClass(), "postdraw", new Class<?>[0], g);
	}

	@Override
	public io.github.tomaso2468.rpgonline.render.Graphics getGUIGraphics(Graphics g) {
		return new SlickGraphics(g);
	}

}

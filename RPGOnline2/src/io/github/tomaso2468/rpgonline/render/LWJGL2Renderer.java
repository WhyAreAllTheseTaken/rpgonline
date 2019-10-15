package io.github.tomaso2468.rpgonline.render;

import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RenderException;

public class LWJGL2Renderer implements GLRenderer {
	private int width = 640;
	private int height = 480;
	private boolean init = false;

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
	public void translate(Graphics g, float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(Graphics g, float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public Graphics getGUIGraphics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScreenWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}

	@Override
	public int getScreenHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

	@Override
	public int getWidth() {
		return Display.getWidth();
	}

	@Override
	public int getHeight() {
		return Display.getHeight();
	}

	@Override
	public void sync(float fps) {
		Display.sync((int) fps);
	}

	@Override
	public void init(Game game) throws RenderException {
		init = true;

		try {
			setDisplayMode(width, height, game.isFullscreen());
			Display.create();
		} catch (LWJGLException e) {
			throw new RenderException("Error creating display.", e);
		}
	}

	/**
	 * Set the display mode to be used
	 * 
	 * @param width      The width of the display required
	 * @param height     The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 * @throws LWJGLException 
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen) throws LWJGLException {

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == fullscreen)) {
			return;
		}

		DisplayMode targetDisplayMode = null;

		if (fullscreen) {
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			int freq = 0;

			for (int i = 0; i < modes.length; i++) {
				DisplayMode current = modes[i];

				if ((current.getWidth() == width) && (current.getHeight() == height)) {
					if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
						if ((targetDisplayMode == null)
								|| (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
							targetDisplayMode = current;
							freq = targetDisplayMode.getFrequency();
						}
					}

					// if we've found a match for bpp and frequence against the
					// original display mode then it's probably best to go for this one
					// since it's most likely compatible with the monitor
					if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
							&& (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
						targetDisplayMode = current;
						break;
					}
				}
			}
		} else {
			targetDisplayMode = new DisplayMode(width, height);
		}

		if (targetDisplayMode == null) {
			System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
			return;
		}

		Display.setDisplayMode(targetDisplayMode);
		Display.setFullscreen(fullscreen);
	}

	@Override
	public void exit(Game game) {
		Display.destroy();
	}

	@Override
	public void setFullscreen(boolean fullscreen) throws RenderException {
		try {
			setDisplayMode(width, height, fullscreen);
		} catch (LWJGLException e) {
			throw new RenderException("Error changing setting fullscreen status to " + fullscreen, e);
		}
	}

	@Override
	public void setResolution(int width, int height) throws RenderException {
		this.width = width;
		this.height = height;

		if (init) {
			try {
				setDisplayMode(width, height, Display.isFullscreen());
			} catch (LWJGLException e) {
				throw new RenderException("Error changing setting resolution", e);
			}
		}
	}

	@Override
	public void doUpdate() {
		Display.update();
	}

	@Override
	public boolean displayClosePressed() {
		return Display.isCloseRequested();
	}
	
	@Override
	public void setVSync(boolean vsync) {
		Display.setVSyncEnabled(vsync);
	}

	@Override
	public void setWindowTitle(String title) {
		Display.setTitle(title);
	}

}

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

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.input.Input;
import io.github.tomaso2468.rpgonline.render.RenderException;

public class LWJGL2Renderer extends GL30Renderer {
	private int width = 640;
	private int height = 480;
	private boolean init = false;
	private boolean antialias = false;

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
			if (antialias) {
				Display.create(new PixelFormat(8,8,0,2));
				GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
				GL11.glEnable(GL11.GL_LINE_SMOOTH);
			} else {
				Display.create();
				GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
				GL11.glDisable(GL11.GL_LINE_SMOOTH);
			}
			GL11.glViewport(0, 0, width, height);
		} catch (LWJGLException e) {
			throw new RenderException("Error creating display.", e);
		}
		Display.setVSyncEnabled(game.isVsync());
		Display.setTitle(game.getTitle());
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
			GL11.glViewport(0, 0, width, height);
		} catch (LWJGLException e) {
			throw new RenderException("Error changing fullscreen status to " + fullscreen, e);
		}
	}

	@Override
	public void setResolution(int width, int height) throws RenderException {
		this.width = width;
		this.height = height;

		if (init) {
			try {
				setDisplayMode(width, height, Display.isFullscreen());
				GL11.glViewport(0, 0, width, height);
			} catch (LWJGLException e) {
				throw new RenderException("Error changing resolution to " + width + "x" + height, e);
			}
		}
	}
	
	@Override
	public void setDisplay(int width, int height, boolean fullscreen) throws RenderException {
		this.width = width;
		this.height = height;

		if (init) {
			try {
				setDisplayMode(width, height, fullscreen);
				GL11.glViewport(0, 0, width, height);
			} catch (LWJGLException e) {
				throw new RenderException("Error changing display mode to " + width + "x" + height + " (fullscreen=" + fullscreen + ")", e);
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

	@Override
	public Input getInput() {
		return new LWJGL2Input(this);
	}

	@Override
	public void writeImage(Image image, File file, boolean writeAlpha) throws IOException, RenderException {
		try {
			ImageOut.write(
					new org.newdawn.slick.Image(((SlickTexture) image.getTexture()).texture),
					file.getAbsolutePath(), writeAlpha);
		} catch (SlickException e) {
			throw new RenderException(e);
		}
	}
	
	@Override
	public void setIcon(URL icon) {
		if (icon == null) {
			return;
		}
		ByteBuffer[] list = loadIcon(icon);
		Display.setIcon(list);
	}
	
	public static ByteBuffer[] loadIcon(URL icon)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(icon);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = loadIconInstance(image, 128);
        buffers[1] = loadIconInstance(image, 32);
        buffers[2] = loadIconInstance(image, 16);
        return buffers;
    }
     
    private static ByteBuffer loadIconInstance(BufferedImage image, int dimension)
    {
        BufferedImage scaledIcon = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledIcon.createGraphics();
        double ratio = 1;
        if(image.getWidth() > scaledIcon.getWidth())
        {
            ratio = (double) (scaledIcon.getWidth()) / image.getWidth();
        }
        else
        {
            ratio = (int) (scaledIcon.getWidth() / image.getWidth());
        }
        if(image.getHeight() > scaledIcon.getHeight())
        {
            double r2 = (double) (scaledIcon.getHeight()) / image.getHeight();
            if(r2 < ratio)
            {
                ratio = r2;
            }
        }
        else
        {
            double r2 =  (int) (scaledIcon.getHeight() / image.getHeight());
            if(r2 < ratio)
            {
                ratio = r2;
            }
        }
        double width = image.getWidth() * ratio;
        double height = image.getHeight() * ratio;
        g.drawImage(image, (int) ((scaledIcon.getWidth() - width) / 2), (int) ((scaledIcon.getHeight() - height) / 2),
                (int) (width), (int) (height), null);
        g.dispose();
         
        byte[] imageBuffer = new byte[dimension*dimension*4];
        int counter = 0;
        for(int i = 0; i < dimension; i++)
        {
            for(int j = 0; j < dimension; j++)
            {
                int colorSpace = scaledIcon.getRGB(j, i);
                imageBuffer[counter + 0] =(byte)((colorSpace << 8) >> 24 );
                imageBuffer[counter + 1] =(byte)((colorSpace << 16) >> 24 );
                imageBuffer[counter + 2] =(byte)((colorSpace << 24) >> 24 );
                imageBuffer[counter + 3] =(byte)(colorSpace >> 24 );
                counter += 4;
            }
        }
        return ByteBuffer.wrap(imageBuffer);
    }

	@Override
	public void setAntialias(boolean antialias) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMouseGrab(boolean mouseGrabbed) {
		Mouse.setGrabbed(mouseGrabbed);
	}
}

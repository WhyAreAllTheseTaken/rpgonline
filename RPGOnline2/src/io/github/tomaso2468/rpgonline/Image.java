package io.github.tomaso2468.rpgonline;

import org.newdawn.slick.opengl.Texture;

public class Image {
	public Image(Texture tex) {
		
	}
	public static final int FILTER_NEAREST = 0;
	public static final int FILTER_LINEAR = 1;
	public void setFilter(int filterMode) {
		// TODO Auto-generated method stub
		
	}
	public Texture getTexture() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void write(String dest, boolean writeAlpha) {
		
	}
	
	public void write(String dest) {
		write(dest, true);
	}
	public int getTextureOffsetX() {
		// TODO Auto-generated method stub
		return 0;
	}
	public float getTextureWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getTextureOffsetY() {
		// TODO Auto-generated method stub
		return 0;
	}
	public float getTextureHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Image getScaledCopy(int w, int h) {
		// TODO Auto-generated method stub
		return null;
	}
}

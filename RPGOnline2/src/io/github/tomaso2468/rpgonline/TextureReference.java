package io.github.tomaso2468.rpgonline;

public interface TextureReference {
	public int getWidth();
	public int getHeight();
	public float getLibraryWidth();
	public float getLibraryHeight();
	public void bind();
	public void destroy();
}

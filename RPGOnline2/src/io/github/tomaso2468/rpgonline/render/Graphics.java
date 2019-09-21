package io.github.tomaso2468.rpgonline.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

public interface Graphics {
	public void setColor(Color c);
	
	public void drawRect(float x, float y, float w, float h);
	public void fillRect(float x, float y, float w, float h);
	
	public void drawOval(float x, float y, float w, float h);
	public void fillOval(float x, float y, float w, float h);
	
	public void drawLine(float x, float y, float x2, float y2);
	
	public void translate(float x, float y);
	public void scale(float x, float y);
	public void rotate(float a);
	public void pushTransform();
	public void popTransform();
	
	public void drawImage(Image img, float x, float y);
	
	public org.newdawn.slick.Graphics beginSlick();
	public void endSlick();
	
	public Font getFont();
	public void drawString(String str, float x, float y);
}

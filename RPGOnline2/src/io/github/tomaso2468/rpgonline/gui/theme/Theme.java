package io.github.tomaso2468.rpgonline.gui.theme;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.Button;
import io.github.tomaso2468.rpgonline.gui.Component;
import io.github.tomaso2468.rpgonline.gui.Container;
import io.github.tomaso2468.rpgonline.gui.Label;
import io.github.tomaso2468.rpgonline.gui.PasswordField;
import io.github.tomaso2468.rpgonline.gui.Picture;
import io.github.tomaso2468.rpgonline.gui.ProgressBar;
import io.github.tomaso2468.rpgonline.gui.Switch;
import io.github.tomaso2468.rpgonline.gui.TextComponent;

public interface Theme {
	public void paintComponent(Graphics g, float scaling, Component c);
	public void paintContainer(Graphics g, float scaling, Container c);
	public void paintProgressBar(Graphics g, float scaling, ProgressBar pb);
	public void paintButton(Graphics g, float scaling, Button b);
	public Rectangle calculateButtonBounds(Button b);
	public void paintSwitch(Graphics g, float scaling, Switch s);
	public void paintPicture(Graphics g, float scaling, Picture p);
	public void paintLabel(Graphics g, float scaling, Label l);
	public void paintText(Graphics g, float scaling, TextComponent tf);
	public void paintPassword(Graphics g, float scaling, PasswordField tf);
}

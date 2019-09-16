package io.github.tomaso2468.rpgonline.gui.theme;

import org.newdawn.slick.Color;
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

public class DefaultTheme implements Theme {
	@Override
	public void paintComponent(Graphics g, float scaling, Component c) {
		g.setColor(Color.white);
		g.fillRect(0, 0, c.getW() * scaling, c.getH() * scaling);
		g.setColor(Color.gray);
		g.drawRect(0, 0, c.getW() * scaling, c.getH() * scaling);
	}

	@Override
	public void paintContainer(Graphics g, float scaling, Container c) {
		g.setColor(Color.white);
		g.fillRect(0, 0, c.getW() * scaling, c.getH() * scaling);
		g.setColor(Color.black);
		g.drawRect(0, 0, c.getW() * scaling, c.getH() * scaling);
	}

	@Override
	public void paintProgressBar(Graphics g, float scaling, ProgressBar pb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintButton(Graphics g, float scaling, Button b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle calculateButtonBounds(Button b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paintSwitch(Graphics g, float scaling, Switch s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintPicture(Graphics g, float scaling, Picture p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintLabel(Graphics g, float scaling, Label l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintText(Graphics g, float scaling, TextComponent tf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintPassword(Graphics g, float scaling, PasswordField tf) {
		// TODO Auto-generated method stub
		
	}
}

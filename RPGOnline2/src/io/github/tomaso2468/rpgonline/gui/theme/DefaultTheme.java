package io.github.tomaso2468.rpgonline.gui.theme;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.Button;
import io.github.tomaso2468.rpgonline.gui.CheckBox;
import io.github.tomaso2468.rpgonline.gui.Component;
import io.github.tomaso2468.rpgonline.gui.Container;
import io.github.tomaso2468.rpgonline.gui.Label;
import io.github.tomaso2468.rpgonline.gui.List.ListElement;
import io.github.tomaso2468.rpgonline.gui.PasswordField;
import io.github.tomaso2468.rpgonline.gui.Picture;
import io.github.tomaso2468.rpgonline.gui.ProgressBar;
import io.github.tomaso2468.rpgonline.gui.RadioButton;
import io.github.tomaso2468.rpgonline.gui.ScrollBar;
import io.github.tomaso2468.rpgonline.gui.Slider;
import io.github.tomaso2468.rpgonline.gui.Switch;
import io.github.tomaso2468.rpgonline.gui.TabButton;
import io.github.tomaso2468.rpgonline.gui.TextComponent;
import io.github.tomaso2468.rpgonline.gui.ToolBar;

public class DefaultTheme implements Theme {
	private Font font;
	private float spacing = 4;
	@Override
	public void paintComponent(Graphics g, float scaling, Component c) {
		g.setColor(Color.black);
		g.fillRect(0, 0, c.getW() * scaling, c.getH() * scaling);
		g.setColor(Color.gray);
		g.drawRect(0, 0, c.getW() * scaling, c.getH() * scaling);
	}

	@Override
	public void paintContainer(Graphics g, float scaling, Container c) {
		g.setColor(Color.black);
		g.fillRect(0, 0, c.getW() * scaling, c.getH() * scaling);
		g.setColor(Color.white);
		g.drawRect(0, 0, c.getW() * scaling, c.getH() * scaling);
	}

	@Override
	public void paintProgressBar(Graphics g, float scaling, ProgressBar c) {
		paintComponent(g, scaling, c);
		g.setColor(Color.gray);
		g.fillRect(0, 0, c.getW() * scaling * (c.getValue() / (float) c.getMax()), c.getH() * scaling);
	}

	@Override
	public void paintButton(Graphics g, float scaling, Button c) {
		paintComponent(g, scaling, c);
		g.setColor(Color.white);
		g.drawString(c.getText(),
				c.getW() * scaling / 2 - g.getFont().getWidth(c.getText()),
				c.getH() * scaling / 2 - g.getFont().getHeight(c.getText()));
	}

	@Override
	public Rectangle calculateButtonBounds(Container c, Button b) {
		return new Rectangle(0, 0, font.getWidth(b.getText()) + spacing * 2, font.getHeight(b.getText()) + spacing * 2);
	}

	@Override
	public void paintSwitch(Graphics g, float scaling, Switch c) {
		paintComponent(g, scaling, c);
		if (c.isState()) {
			g.setColor(Color.green);
			g.fillRect(c.getW() * scaling / 2, 0, c.getW() * scaling / 2, c.getH() * scaling);
		} else {
			g.setColor(Color.red);
			g.fillRect(0, 0, c.getW() * scaling / 2, c.getH() * scaling);
		}
	}

	@Override
	public void paintPicture(Graphics g, float scaling, Picture p) {
		g.drawImage(p.getImage().getScaledCopy((int) (p.getW() * scaling), (int) (p.getH() * scaling)), 0, 0);
	}

	@Override
	public void paintLabel(Graphics g, float scaling, Label c) {
		g.setColor(Color.white);
		g.drawString(c.getText(),
				c.getW() * scaling / 2 - g.getFont().getWidth(c.getText()) / 2,
				c.getH() * scaling / 2 - g.getFont().getHeight(c.getText()) / 2);
	}

	@Override
	public Rectangle calculateLabelBounds(Container c, Label b) {
		return new Rectangle(0, 0, font.getWidth(b.getText()) + spacing * 2, font.getHeight(b.getText()) + spacing * 2);
	}

	@Override
	public void paintText(Graphics g, float scaling, TextComponent tf) {
		paintComponent(g, scaling, tf);
		g.setColor(Color.white);
		g.drawString(tf.getText(), spacing * scaling, spacing * scaling);
	}

	@Override
	public void paintPassword(Graphics g, float scaling, PasswordField tf) {
		paintComponent(g, scaling, tf);
		String s = "";
		for (int i = 0; i < tf.getText().length(); i++) {
			s += "*";
		}
		g.setColor(Color.white);
		g.drawString(s, spacing * scaling, spacing * scaling);
	}

	@Override
	public void paintScrollBar(Graphics g, float scaling, ScrollBar c) {
		paintComponent(g, scaling, c);
		g.setColor(Color.white);
		g.fillRect(0, 0, c.getW() * scaling, c.getH() * scaling * (c.getPos() / (float) c.getMax()));
	}

	@Override
	public Rectangle calculateScrollBarBounds(Container c, ScrollBar sb) {
		return new Rectangle(0, 0, spacing * 3, c.getH());
	}

	@Override
	public float getScrollableBorder() {
		return spacing;
	}

	@Override
	public Rectangle calculateSliderBounds(Container c, Slider slider) {
		return new Rectangle(0, 0, c.getW(), spacing * 4);
	}

	@Override
	public void paintSlider(Graphics g, float scaling, Slider c) {
		paintComponent(g, scaling, c);
		g.setColor(Color.white);
		g.fillRect(0, 0, c.getW() * scaling * (c.getPos() / (float) c.getMax()), c.getH() * scaling);
	}

	@Override
	public void paintCheckBox(Graphics g, float scaling, CheckBox c) {
		g.setColor(c.isState() ? Color.green : Color.red);
		g.fillRect(0, 0, spacing * 4 * scaling, spacing * 4 * scaling);
		g.setColor(Color.white);
		g.drawString(c.getText(), spacing * 5 * scaling, c.getH() * scaling / 2 - g.getFont().getHeight(c.getText()) / 2);
	}

	@Override
	public Rectangle calculateCheckBoxBounds(Container c, CheckBox checkBox) {
		return new Rectangle(0, 0, spacing * 4, spacing * 5 + font.getWidth(checkBox.getText()));
	}

	@Override
	public void paintRadioButton(Graphics g, float scaling, RadioButton c) {
		g.setColor(c.isState() ? Color.green : Color.red);
		g.fillOval(0, 0, spacing * 4 * scaling, spacing * 4 * scaling);
		g.setColor(Color.white);
		g.drawString(c.getText(), spacing * 5 * scaling, c.getH() * scaling / 2 - g.getFont().getHeight(c.getText()) / 2);
	}

	@Override
	public void paintListElement(Graphics g, float scaling, ListElement c) {
		if (c.isState()) {
			g.setColor(Color.black);
		} else {
			g.setColor(Color.darkGray);
		}
		g.fillRect(0, 0, c.getW(), c.getH());
		g.setColor(Color.white);
		g.drawString(c.getText(),
				c.getW() * scaling / 2 - g.getFont().getWidth(c.getText()) / 2,
				c.getH() * scaling / 2 - g.getFont().getHeight(c.getText()) / 2);
	}

	@Override
	public Rectangle calculateListElementBounds(Container c, ListElement listElement) {
		return new Rectangle(0, 0, c.getW(), spacing * 4);
	}

	@Override
	public Rectangle calculateToolBarBounds(Container c, ToolBar toolBar) {
		return new Rectangle(0, 0, c.getW(), font.getHeight("Q"));
	}

	@Override
	public void paintTab(Graphics g, float scaling, TabButton c) {
		paintButton(g, scaling, c);
	}

	@Override
	public Rectangle calculateTabBounds(Container c, TabButton b) {
		return calculateButtonBounds(c, b);
	}

	@Override
	public void predraw(Graphics g) {
		if (font == null) font = g.getFont();
	}
}
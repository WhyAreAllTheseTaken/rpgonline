package io.github.tomaso2468.rpgonline.gui.theme;

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

public interface Theme {
	public void paintComponent(Graphics g, float scaling, Component c);
	public void paintContainer(Graphics g, float scaling, Container c);
	public void paintProgressBar(Graphics g, float scaling, ProgressBar pb);
	public void paintButton(Graphics g, float scaling, Button b);
	public Rectangle calculateButtonBounds(Container c, Button b);
	public void paintSwitch(Graphics g, float scaling, Switch s);
	public void paintPicture(Graphics g, float scaling, Picture p);
	public void paintLabel(Graphics g, float scaling, Label l);
	public Rectangle calculateLabelBounds(Container c, Label l);
	public void paintText(Graphics g, float scaling, TextComponent tf);
	public void paintPassword(Graphics g, float scaling, PasswordField tf);
	public void paintScrollBar(Graphics g, float scaling, ScrollBar sb);
	public Rectangle calculateScrollBarBounds(Container c, ScrollBar sb);
	public float getScrollableBorder();
	public Rectangle calculateSliderBounds(Container c, Slider slider);
	public void paintSlider(Graphics g, float scaling, Slider slider);
	public void paintCheckBox(Graphics g, float scaling, CheckBox checkBox);
	public Rectangle calculateCheckBoxBounds(Container c, CheckBox checkBox);
	public void paintRadioButton(Graphics g, float scaling, RadioButton b);
	public void paintListElement(Graphics g, float scaling, ListElement listElement);
	public Rectangle calculateListElementBounds(Container c, ListElement listElement);
	public Rectangle calculateToolBarBounds(Container c, ToolBar toolBar);
	public void paintTab(Graphics g, float scaling, TabButton tabButton);
	public Rectangle calculateTabBounds(Container c, TabButton b);
}

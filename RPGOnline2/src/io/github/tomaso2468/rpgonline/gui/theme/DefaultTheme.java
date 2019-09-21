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
package io.github.tomaso2468.rpgonline.gui.theme;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
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
import io.github.tomaso2468.rpgonline.render.Graphics;

/**
 * The default theme used for the GUI system.
 * 
 * @author Tomas
 *
 */
public class DefaultTheme implements Theme {
	/**
	 * The theme font.
	 */
	private Font font;
	/**
	 * The default spacing.
	 */
	private float spacing = 4;
	/**
	 * The display scaling.
	 */
	private float scaling = 1;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintComponent(Graphics g, float scaling, Component c) {
		g.setColor(Color.black);
		g.fillRect(0, 0, c.getW(), c.getH());
		g.setColor(Color.gray);
		g.drawRect(0, 0, c.getW(), c.getH());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintContainer(Graphics g, float scaling, Container c) {
		g.setColor(Color.black);
		g.fillRect(0, 0, c.getW(), c.getH());
		g.setColor(Color.white);
		g.drawRect(0, 0, c.getW(), c.getH());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintProgressBar(Graphics g, float scaling, ProgressBar c) {
		paintComponent(g, scaling, c);
		if (c.isIntermediate()) {
			g.setColor(Color.gray);
			g.fillRect(c.getW() - (float) (c.getW() * (FastMath.sin(System.currentTimeMillis() / 1000.0) + 1) / 2) / 2,
					0, (float) (c.getW() * (FastMath.sin(System.currentTimeMillis() / 1000.0) + 1) / 2), c.getH());
		} else {
			g.setColor(Color.gray);
			g.fillRect(0, 0, c.getW() * (c.getValue() / (float) c.getMax()), c.getH());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintButton(Graphics g, float scaling, Button c) {
		g.setColor(c.isState() ? Color.darkGray : Color.black);
		g.fillRect(0, 0, c.getW(), c.getH());
		g.setColor(Color.gray);
		g.drawRect(0, 0, c.getW(), c.getH());
		g.setColor(Color.white);
		g.scale(1 / scaling, 1 / scaling);
		g.drawString(c.getText(), c.getW() * scaling / 2 - g.getFont().getWidth(c.getText()) / 2,
				c.getH() * scaling / 2 - g.getFont().getHeight(c.getText()) / 2);
		g.scale(scaling, scaling);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle calculateButtonBounds(Container c, Button b) {
		return new Rectangle(0, 0, font.getWidth(b.getText()) / scaling + spacing * 2,
				font.getHeight(b.getText()) / scaling + spacing * 2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintSwitch(Graphics g, float scaling, Switch c) {
		paintComponent(g, scaling, c);
		if (c.isState()) {
			g.setColor(Color.green);
			g.fillRect(c.getW() / 2, 0, c.getW() / 2, c.getH());
		} else {
			g.setColor(Color.red);
			g.fillRect(0, 0, c.getW() / 2, c.getH());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintPicture(Graphics g, float scaling, Picture p) {
		g.drawImage(p.getImage().getScaledCopy((int) (p.getW()), (int) (p.getH())), 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintLabel(Graphics g, float scaling, Label c) {
		g.setColor(Color.white);
		g.scale(1 / scaling, 1 / scaling);
		g.drawString(c.getText(), c.getW() * scaling / 2 - g.getFont().getWidth(c.getText()) / 2,
				c.getH() * scaling / 2 - g.getFont().getHeight(c.getText()) / 2);
		g.scale(scaling, scaling);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle calculateLabelBounds(Container c, Label b) {
		return new Rectangle(0, 0, font.getWidth(b.getText()) / scaling + spacing * 2,
				font.getHeight(b.getText()) / scaling + spacing * 2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintText(Graphics g, float scaling, TextComponent tf) {
		paintComponent(g, scaling, tf);
		g.setColor(Color.white);
		g.scale(1 / scaling, 1 / scaling);
		g.drawString(new StringBuilder(tf.getText()).insert(tf.getIndex(), "|").toString(), spacing * scaling, spacing * scaling);
		g.scale(scaling, scaling);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintPassword(Graphics g, float scaling, PasswordField tf) {
		paintComponent(g, scaling, tf);
		String s = "";
		for (int i = 0; i < tf.getText().length(); i++) {
			s += "*";
		}
		g.setColor(Color.white);
		g.scale(1 / scaling, 1 / scaling);
		g.drawString(new StringBuilder(s).insert(tf.getIndex(), "|").toString(), spacing * scaling, spacing * scaling);
		g.scale(scaling, scaling);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintScrollBar(Graphics g, float scaling, ScrollBar c) {
		paintComponent(g, scaling, c);
		g.setColor(Color.gray);
		g.fillRect(0, 0, c.getW(), c.getH() * (c.getPos() / (float) c.getMax()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle calculateScrollBarBounds(Container c, ScrollBar sb) {
		return new Rectangle(0, 0, spacing * 3, c.getH() / scaling);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getScrollableBorder() {
		return spacing;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle calculateSliderBounds(Container c, Slider slider) {
		return new Rectangle(0, 0, c.getW(), spacing * 4);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintSlider(Graphics g, float scaling, Slider c) {
		paintComponent(g, scaling, c);
		g.setColor(Color.white);
		g.fillRect(0, 0, c.getW() * (c.getPos() / (float) c.getMax()), c.getH());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintCheckBox(Graphics g, float scaling, CheckBox c) {
		g.setColor(c.isState() ? Color.green : Color.red);
		g.fillRect(0, 0, spacing * 4, spacing * 4);
		g.setColor(Color.white);
		g.scale(1 / scaling, 1 / scaling);
		g.drawString(c.getText(), spacing * 5 * scaling, spacing * 4 / 2 * scaling - g.getFont().getHeight(c.getText()) / 2);
		g.scale(scaling, scaling);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle calculateCheckBoxBounds(Container c, CheckBox checkBox) {
		return new Rectangle(0, 0, spacing * 5 + font.getWidth(checkBox.getText()) / scaling, spacing * 4);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintRadioButton(Graphics g, float scaling, RadioButton c) {
		g.setColor(c.isState() ? Color.green : Color.red);
		g.fillOval(0, 0, spacing * 4, spacing * 4);
		g.setColor(Color.white);
		g.scale(1 / scaling, 1 / scaling);
		g.drawString(c.getText(), spacing * 5 * scaling, spacing * 4 / 2 * scaling - g.getFont().getHeight(c.getText()) / 2);
		g.scale(scaling, scaling);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintListElement(Graphics g, float scaling, ListElement c) {
		if (c.isState()) {
			g.setColor(Color.black);
		} else {
			g.setColor(Color.darkGray);
		}
		g.fillRect(0, 0, c.getW(), c.getH());
		g.setColor(Color.white);
		g.scale(1 / scaling, 1 / scaling);
		g.drawString(c.getText(), c.getW() / 2 * scaling - g.getFont().getWidth(c.getText()) / 2,
				c.getH() / 2 * scaling - g.getFont().getHeight(c.getText()) / 2);
		g.scale(scaling, scaling);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle calculateListElementBounds(Container c, ListElement listElement) {
		return new Rectangle(0, 0, c.getW(), spacing * 4);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle calculateToolBarBounds(Container c, ToolBar toolBar) {
		return new Rectangle(0, 0, c.getW(), font.getHeight("Q") / scaling);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintTab(Graphics g, float scaling, TabButton c) {
		paintButton(g, scaling, c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle calculateTabBounds(Container c, TabButton b) {
		return calculateButtonBounds(c, b);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void predraw(Graphics g) {
		if (font == null)
			font = g.getFont();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(GameContainer c, float scaling) {
		font = c.getDefaultFont();
		this.scaling = scaling;
	}
}
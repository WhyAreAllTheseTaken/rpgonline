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

import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.Game;
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
 * The interface for defining a theme for the GUI system. Scaling must be done when rendering a component with this interface.
 * @author Tomaso2468
 *
 */
public interface Theme {
	/**
	 * Paints the graphics of the component.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param c The component to render.
	 */
	public void paintComponent(Game game, Graphics g, float scaling, Component c);
	/**
	 * Paints the graphics of the container without painting components in the container.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param c The container to render.
	 */
	public void paintContainer(Game game, Graphics g, float scaling, Container c);
	/**
	 * Paints the graphics of the progress bar.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param pb The progress bar to render.
	 */
	public void paintProgressBar(Game game, Graphics g, float scaling, ProgressBar pb);
	/**
	 * Paints the graphics of the button.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param b The button to render.
	 */
	public void paintButton(Game game, Graphics g, float scaling, Button b);
	/**
	 * Calculates the bounds for a button based on the specified container.
	 * @param c The container the component is in.
	 * @param b The button for bounds to be calculated for.
	 * @return A rectangle object.
	 */
	public Rectangle calculateButtonBounds(Container c, Button b);
	/**
	 * Paints the graphics of the switch.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param s The switch to render.
	 */
	public void paintSwitch(Game game, Graphics g, float scaling, Switch s);
	/**
	 * Paints the graphics of the picture.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param p The picture to render.
	 */
	public void paintPicture(Game game, Graphics g, float scaling, Picture p);
	/**
	 * Paints the graphics of the label.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param l The label to render.
	 */
	public void paintLabel(Game game, Graphics g, float scaling, Label l);
	/**
	 * Calculates the bounds for a label based on the specified container.
	 * @param c The container the component is in.
	 * @param l The label for bounds to be calculated for.
	 * @return A rectangle object.
	 */
	public Rectangle calculateLabelBounds(Container c, Label l);
	/**
	 * Paints the graphics of the text component.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param tf The text component to render.
	 */
	public void paintText(Game game, Graphics g, float scaling, TextComponent tf);
	/**
	 * Paints the graphics of the password field.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param tf The password field to render.
	 */
	public void paintPassword(Game game, Graphics g, float scaling, PasswordField tf);
	/**
	 * Paints the graphics of the scroll bar.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param sb The scroll bar to render.
	 */
	public void paintScrollBar(Game game, Graphics g, float scaling, ScrollBar sb);
	/**
	 * Calculates the bounds for a scroll bar based on the specified container.
	 * @param c The container the component is in.
	 * @param sb The scroll bar for bounds to be calculated for.
	 * @return A rectangle object.
	 */
	public Rectangle calculateScrollBarBounds(Container c, ScrollBar sb);
	public float getScrollableBorder();
	/**
	 * Calculates the bounds for a slider based on the specified container.
	 * @param c The container the component is in.
	 * @param slider The slider for bounds to be calculated for.
	 * @return A rectangle object.
	 */
	public Rectangle calculateSliderBounds(Container c, Slider slider);
	/**
	 * Paints the graphics of the slider.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param slider The slider to render.
	 */
	public void paintSlider(Game game, Graphics g, float scaling, Slider slider);
	/**
	 * Paints the graphics of the check box.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param checkBox The checkBox to render.
	 */
	public void paintCheckBox(Game game, Graphics g, float scaling, CheckBox checkBox);
	/**
	 * Calculates the bounds for a checkBox based on the specified container.
	 * @param c The container the component is in.
	 * @param checkBox The checkBox for bounds to be calculated for.
	 * @return A rectangle object.
	 */
	public Rectangle calculateCheckBoxBounds(Container c, CheckBox checkBox);
	/**
	 * Paints the graphics of the radio button.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param pb The radio button to render.
	 */
	public void paintRadioButton(Game game, Graphics g, float scaling, RadioButton b);
	/**
	 * Paints the graphics of the list element.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param listElement The list element to render.
	 */
	public void paintListElement(Game game, Graphics g, float scaling, ListElement listElement);
	/**
	 * Calculates the bounds for a list element based on the specified container.
	 * @param c The container the component is in.
	 * @param listElement The list element for bounds to be calculated for.
	 * @return A rectangle object.
	 */
	public Rectangle calculateListElementBounds(Container c, ListElement listElement);
	/**
	 * Calculates the bounds for a tool bar based on the specified container.
	 * @param c The container the component is in.
	 * @param toolBar The tool bar for bounds to be calculated for.
	 * @return A rectangle object.
	 */
	public Rectangle calculateToolBarBounds(Container c, ToolBar toolBar);
	/**
	 * Paints the graphics of the tab button.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the GUI.
	 * @param tabButton The tab button to render.
	 */
	public void paintTab(Game game, Graphics g, float scaling, TabButton tabButton);
	/**
	 * Calculates the bounds for a tab button based on the specified container.
	 * @param c The container the component is in.
	 * @param b The tab button for bounds to be calculated for.
	 * @return A rectangle object.
	 */
	public Rectangle calculateTabBounds(Container c, TabButton b);
	/**
	 * A method called before drawing occurs on any frame.
	 * @param g The current graphics context.
	 */
	public void predraw(Game game, Graphics g);
	/**
	 * Initialises the theme with the game container.
	 * @param c The game container to use.
	 * @param scaling The scaling factor for the display.
	 */
	public void init(Game c, float scaling);
}

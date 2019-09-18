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
package io.github.tomaso2468.rpgonline.gui;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * The root class providing text input support.
 * @author Tomas
 *
 */
public abstract class TextComponent extends Component {
	/**
	 * The default cooldown for typing.
	 */
	public static final float DEFAULT_COOLDOWN = 0.05f;
	/**
	 * The text held in this component.
	 */
	private StringBuilder text;
	/**
	 * The index of the selected character.
	 */
	private int index;
	/**
	 * The cooldown for typing.
	 */
	private float cooldown = 0f;

	/**
	 * Constructs a new TextComponent.
	 * @param text The default text for this component.
	 */
	public TextComponent(String text) {
		super();
		this.setText(text);
	}
	
	/**
	 * Constructs a new TextComponent.
	 */
	public TextComponent() {
		super();
	}

	/**
	 * Gets the text content of this component.
	 * @return A string.
	 */
	public String getText() {
		return text.toString();
	}

	/**
	 * Sets the text content of this component.
	 * @param text A string.
	 */
	public void setText(String text) {
		this.text = new StringBuilder(text);
		setIndex(text.length());
	}
	
	/**
	 * Determines if multiple lines should be allowed in this text component. This method should be overridden to set the value of this component.
	 * @return {@code true} if multiple lines should be allowed, {@code false} otherwise.
	 */
	protected boolean isMultiLine() {
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delta) {
		super.update(delta);
		
		cooldown -= delta;
		
		if (cooldown > 0) {
			return;
		}
		
		boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
		boolean ctrl = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
		boolean alt = Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU);

		processKeys(shift, ctrl, alt);
	}
	
	/**
	 * Inserts a string at the selected index.
	 * @param s The string to insert.
	 */
	public void insert(String s) {
		text.insert(index, s);
		index += 1;
	}
	
	/**
	 * Inserts a character at the selected index.
	 * @param s The character to insert.
	 */
	public void insert(char s) {
		insert(s + "");
	}
	
	/**
	 * Processes the keyboard input for this component.
	 * @param shift {@code true} if the SHIFT key is pressed, {@code false} otherwise.
	 * @param ctrl {@code true} if the CTRL key is pressed, {@code false} otherwise.
	 * @param alt {@code true} if the ALT key is pressed, {@code false} otherwise.
	 */
	protected void processKeys(boolean shift, boolean ctrl, boolean alt) {
		int[] codes = {
				Keyboard.KEY_0,
				Keyboard.KEY_1,
				Keyboard.KEY_2,
				Keyboard.KEY_3,
				Keyboard.KEY_4,
				Keyboard.KEY_5,
				Keyboard.KEY_6,
				Keyboard.KEY_7,
				Keyboard.KEY_8,
				Keyboard.KEY_9,
				Keyboard.KEY_A,
				Keyboard.KEY_B,
				Keyboard.KEY_C,
				Keyboard.KEY_D,
				Keyboard.KEY_E,
				Keyboard.KEY_F,
				Keyboard.KEY_G,
				Keyboard.KEY_H,
				Keyboard.KEY_I,
				Keyboard.KEY_J,
				Keyboard.KEY_K,
				Keyboard.KEY_L,
				Keyboard.KEY_M,
				Keyboard.KEY_N,
				Keyboard.KEY_O,
				Keyboard.KEY_P,
				Keyboard.KEY_Q,
				Keyboard.KEY_R,
				Keyboard.KEY_S,
				Keyboard.KEY_T,
				Keyboard.KEY_U,
				Keyboard.KEY_V,
				Keyboard.KEY_W,
				Keyboard.KEY_X,
				Keyboard.KEY_Y,
				Keyboard.KEY_Z,
				Keyboard.KEY_MINUS,
				Keyboard.KEY_EQUALS,
				Keyboard.KEY_BACK,
				Keyboard.KEY_TAB,
				Keyboard.KEY_LBRACKET,
				Keyboard.KEY_RBRACKET,
				Keyboard.KEY_RETURN,
				Keyboard.KEY_SEMICOLON,
				Keyboard.KEY_APOSTROPHE,
				Keyboard.KEY_GRAVE,
				Keyboard.KEY_BACKSLASH,
				Keyboard.KEY_COMMA,
				Keyboard.KEY_PERIOD,
				Keyboard.KEY_SLASH,
				Keyboard.KEY_MULTIPLY,
				Keyboard.KEY_SPACE,
				Keyboard.KEY_F1,
				Keyboard.KEY_F2,
				Keyboard.KEY_F3,
				Keyboard.KEY_F4,
				Keyboard.KEY_F5,
				Keyboard.KEY_F6,
				Keyboard.KEY_F7,
				Keyboard.KEY_F8,
				Keyboard.KEY_F9,
				Keyboard.KEY_F10,
				Keyboard.KEY_F11,
				Keyboard.KEY_F12,
				Keyboard.KEY_F13,
				Keyboard.KEY_F14,
				Keyboard.KEY_F15,
				Keyboard.KEY_F16,
				Keyboard.KEY_F17,
				Keyboard.KEY_F18,
				Keyboard.KEY_F19,
				Keyboard.KEY_NUMPAD0,
				Keyboard.KEY_NUMPAD1,
				Keyboard.KEY_NUMPAD2,
				Keyboard.KEY_NUMPAD3,
				Keyboard.KEY_NUMPAD4,
				Keyboard.KEY_NUMPAD5,
				Keyboard.KEY_NUMPAD6,
				Keyboard.KEY_NUMPAD7,
				Keyboard.KEY_NUMPAD8,
				Keyboard.KEY_NUMPAD9,
				Keyboard.KEY_SUBTRACT,
				Keyboard.KEY_ADD,
				Keyboard.KEY_DECIMAL,
				Keyboard.KEY_YEN,
				Keyboard.KEY_AT,
				Keyboard.KEY_COLON,
				Keyboard.KEY_NUMPADENTER,
				Keyboard.KEY_DIVIDE,
				Keyboard.KEY_HOME,
				Keyboard.KEY_PRIOR,
				Keyboard.KEY_LEFT,
				Keyboard.KEY_RIGHT,
				Keyboard.KEY_END,
				Keyboard.KEY_CLEAR,
		};
		for (int c : codes) {
			if (Keyboard.isKeyDown(c)) {
				processKey(shift, ctrl, alt, c);
				cooldown = DEFAULT_COOLDOWN;
			}
		}
	}
	
	/**
	 * Processes a single key.
	 * @param shift {@code true} if the SHIFT key is pressed, {@code false} otherwise.
	 * @param ctrl {@code true} if the CTRL key is pressed, {@code false} otherwise.
	 * @param alt {@code true} if the ALT key is pressed, {@code false} otherwise.
	 */
	protected void processKey(boolean shift, boolean ctrl, boolean alt, int code) {
		switch (code) {
		case Keyboard.KEY_RETURN:
			if (isMultiLine()) {
				insert("\n");
			}
			break;
		case Keyboard.KEY_NUMPADENTER:
			if (isMultiLine()) {
				insert("\n");
			}
			break;
		case Keyboard.KEY_BACK:
			text.deleteCharAt(text.length() - 1);
			index -= 1;
			break;
		case Keyboard.KEY_HOME:
			index = 0;
			break;
		case Keyboard.KEY_END:
			index = text.length();
			break;
		case Keyboard.KEY_PRIOR:
			index = 0;
			break;
		case Keyboard.KEY_NEXT:
			index = text.length();
			break;
		case Keyboard.KEY_LEFT:
			index -= 1;
			if (index < 0) {
				index = 0;
			}
			break;
		case Keyboard.KEY_RIGHT:
			index += 1;
			if (index > text.length()) {
				index = text.length();
			}
			break;
		case Keyboard.KEY_CLEAR:
			setText("");
			break;
		default:
			insert(processChar(shift, ctrl, alt, code));
		}
	}
	
	/**
	 * Processes a single character.
	 * @param shift {@code true} if the SHIFT key is pressed, {@code false} otherwise.
	 * @param ctrl {@code true} if the CTRL key is pressed, {@code false} otherwise.
	 * @param alt {@code true} if the ALT key is pressed, {@code false} otherwise.
	 */
	protected String processChar(boolean shift, boolean ctrl, boolean alt, int code) {
		switch (code) {
		case Keyboard.KEY_ADD:
			return shift ? "+" : "+";
		case Keyboard.KEY_APOSTROPHE:
			return shift ? "@" : "'";
		case Keyboard.KEY_AT:
			return shift ? "@" : "@";
		case Keyboard.KEY_BACKSLASH:
			return shift ? "|" : "\\";
		case Keyboard.KEY_COLON:
			return shift ? "¦" : ":";
		case Keyboard.KEY_COMMA:
			return shift ? "<" : ",";
		case Keyboard.KEY_DECIMAL:
			return shift ? ">" : ".";
		case Keyboard.KEY_DIVIDE:
			return shift ? "?" : "/";
		case Keyboard.KEY_EQUALS:
			return shift ? "+" : "=";
		case Keyboard.KEY_GRAVE:
			return shift ? "¬" : "`";
		case Keyboard.KEY_LBRACKET:
			return shift ? "{" : "[";
		case Keyboard.KEY_RBRACKET:
			return shift ? "}" : "]";
		case Keyboard.KEY_MINUS:
			return shift ? "_" : "-";
		case Keyboard.KEY_MULTIPLY:
			return shift ? "*" : "*";
		case Keyboard.KEY_NUMPAD0:
			return "0";
		case Keyboard.KEY_NUMPAD1:
			return "1";
		case Keyboard.KEY_NUMPAD2:
			return "2";
		case Keyboard.KEY_NUMPAD3:
			return "3";
		case Keyboard.KEY_NUMPAD4:
			return "4";
		case Keyboard.KEY_NUMPAD5:
			return "5";
		case Keyboard.KEY_NUMPAD6:
			return "6";
		case Keyboard.KEY_NUMPAD7:
			return "7";
		case Keyboard.KEY_NUMPAD8:
			return "8";
		case Keyboard.KEY_NUMPAD9:
			return "9";
		case Keyboard.KEY_NUMPADCOMMA:
			return shift ? "," : ",";
		case Keyboard.KEY_NUMPADEQUALS:
			return shift ? "=" : "=";
		case Keyboard.KEY_PERIOD:
			return shift ? ">" : ".";
		case Keyboard.KEY_SEMICOLON:
			return shift ? ":" : ";";
		case Keyboard.KEY_SLASH:
			return shift ? "?" : "/";
		case Keyboard.KEY_SUBTRACT:
			return shift ? "_" : "-";
		case Keyboard.KEY_TAB:
			return shift ? "\t" : "\t";
		case Keyboard.KEY_YEN:
			return shift ? "\u00A5" : "\u00A5";
		case Keyboard.KEY_0:
			return shift ? "!" : "0";
		case Keyboard.KEY_1:
			return shift ? "\"" : "1";
		case Keyboard.KEY_2:
			return shift ? "£" : "2";
		case Keyboard.KEY_3:
			return shift ? "$" : "3";
		case Keyboard.KEY_4:
			return shift ? "%" : "4";
		case Keyboard.KEY_5:
			return shift ? "^" : "5";
		case Keyboard.KEY_6:
			return shift ? "&" : "6";
		case Keyboard.KEY_7:
			return shift ? "*" : "7";
		case Keyboard.KEY_8:
			return shift ? "(" : "8";
		case Keyboard.KEY_9:
			return shift ? ")" : "9";
		default:
			return shift ? (Keyboard.getKeyName(code).charAt(0) + "").toUpperCase() : Keyboard.getKeyName(code).charAt(0) + "";
		}
	}

	/**
	 * Gets the index of the selected character.
	 * @return An int value.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index of the selected character.
	 * @param index An int value.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintText(g, scaling, this);
	}

}

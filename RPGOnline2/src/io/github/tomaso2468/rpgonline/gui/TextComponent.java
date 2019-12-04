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

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.input.Input;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderException;

/**
 * The root class providing text input support.
 * @author Tomaso2468
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
		this("");
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
	public void update(Game game, float delta, Input input) {
		super.update(game, delta, input);
		
		cooldown -= delta;
		
		if (cooldown > 0) {
			return;
		}
		
		boolean shift = input.isKeyDown(Input.KEY_LSHIFT) || input.isKeyDown(Input.KEY_RSHIFT);
		boolean ctrl = input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL);
		boolean alt = input.isKeyDown(Input.KEY_LMENU) || input.isKeyDown(Input.KEY_RMENU);

		processKeys(shift, ctrl, alt, input);
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
	 * Processes the Input input for this component.
	 * @param shift {@code true} if the SHIFT key is pressed, {@code false} otherwise.
	 * @param ctrl {@code true} if the CTRL key is pressed, {@code false} otherwise.
	 * @param alt {@code true} if the ALT key is pressed, {@code false} otherwise.
	 */
	protected void processKeys(boolean shift, boolean ctrl, boolean alt, Input input) {
		int[] codes = {
				Input.KEY_0,
				Input.KEY_1,
				Input.KEY_2,
				Input.KEY_3,
				Input.KEY_4,
				Input.KEY_5,
				Input.KEY_6,
				Input.KEY_7,
				Input.KEY_8,
				Input.KEY_9,
				Input.KEY_A,
				Input.KEY_B,
				Input.KEY_C,
				Input.KEY_D,
				Input.KEY_E,
				Input.KEY_F,
				Input.KEY_G,
				Input.KEY_H,
				Input.KEY_I,
				Input.KEY_J,
				Input.KEY_K,
				Input.KEY_L,
				Input.KEY_M,
				Input.KEY_N,
				Input.KEY_O,
				Input.KEY_P,
				Input.KEY_Q,
				Input.KEY_R,
				Input.KEY_S,
				Input.KEY_T,
				Input.KEY_U,
				Input.KEY_V,
				Input.KEY_W,
				Input.KEY_X,
				Input.KEY_Y,
				Input.KEY_Z,
				Input.KEY_MINUS,
				Input.KEY_EQUALS,
				Input.KEY_BACK,
				Input.KEY_TAB,
				Input.KEY_LBRACKET,
				Input.KEY_RBRACKET,
				Input.KEY_RETURN,
				Input.KEY_SEMICOLON,
				Input.KEY_APOSTROPHE,
				Input.KEY_GRAVE,
				Input.KEY_BACKSLASH,
				Input.KEY_COMMA,
				Input.KEY_PERIOD,
				Input.KEY_SLASH,
				Input.KEY_MULTIPLY,
				Input.KEY_SPACE,
				Input.KEY_F1,
				Input.KEY_F2,
				Input.KEY_F3,
				Input.KEY_F4,
				Input.KEY_F5,
				Input.KEY_F6,
				Input.KEY_F7,
				Input.KEY_F8,
				Input.KEY_F9,
				Input.KEY_F10,
				Input.KEY_F11,
				Input.KEY_F12,
				Input.KEY_F13,
				Input.KEY_F14,
				Input.KEY_F15,
				Input.KEY_F16,
				Input.KEY_F17,
				Input.KEY_F18,
				Input.KEY_F19,
				Input.KEY_NUMPAD0,
				Input.KEY_NUMPAD1,
				Input.KEY_NUMPAD2,
				Input.KEY_NUMPAD3,
				Input.KEY_NUMPAD4,
				Input.KEY_NUMPAD5,
				Input.KEY_NUMPAD6,
				Input.KEY_NUMPAD7,
				Input.KEY_NUMPAD8,
				Input.KEY_NUMPAD9,
				Input.KEY_SUBTRACT,
				Input.KEY_ADD,
				Input.KEY_DECIMAL,
				Input.KEY_YEN,
				Input.KEY_AT,
				Input.KEY_COLON,
				Input.KEY_NUMPADENTER,
				Input.KEY_DIVIDE,
				Input.KEY_HOME,
				Input.KEY_PRIOR,
				Input.KEY_LEFT,
				Input.KEY_RIGHT,
				Input.KEY_END,
				Input.KEY_CLEAR,
		};
		for (int c : codes) {
			if (input.isKeyDown(c)) {
				processKey(shift, ctrl, alt, c, input);
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
	protected void processKey(boolean shift, boolean ctrl, boolean alt, int code, Input input) {
		switch (code) {
		case Input.KEY_RETURN:
			if (isMultiLine()) {
				insert("\n");
			}
			break;
		case Input.KEY_NUMPADENTER:
			if (isMultiLine()) {
				insert("\n");
			}
			break;
		case Input.KEY_BACK:
			text.deleteCharAt(text.length() - 1);
			index -= 1;
			break;
		case Input.KEY_HOME:
			index = 0;
			break;
		case Input.KEY_END:
			index = text.length();
			break;
		case Input.KEY_PRIOR:
			index = 0;
			break;
		case Input.KEY_NEXT:
			index = text.length();
			break;
		case Input.KEY_LEFT:
			index -= 1;
			if (index < 0) {
				index = 0;
			}
			break;
		case Input.KEY_RIGHT:
			index += 1;
			if (index > text.length()) {
				index = text.length();
			}
			break;
		case Input.KEY_CLEAR:
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
		case Input.KEY_ADD:
			return shift ? "+" : "+";
		case Input.KEY_APOSTROPHE:
			return shift ? "@" : "'";
		case Input.KEY_AT:
			return shift ? "@" : "@";
		case Input.KEY_BACKSLASH:
			return shift ? "|" : "\\";
		case Input.KEY_COLON:
			return shift ? "¦" : ":";
		case Input.KEY_COMMA:
			return shift ? "<" : ",";
		case Input.KEY_DECIMAL:
			return shift ? ">" : ".";
		case Input.KEY_DIVIDE:
			return shift ? "?" : "/";
		case Input.KEY_EQUALS:
			return shift ? "+" : "=";
		case Input.KEY_GRAVE:
			return shift ? "¬" : "`";
		case Input.KEY_LBRACKET:
			return shift ? "{" : "[";
		case Input.KEY_RBRACKET:
			return shift ? "}" : "]";
		case Input.KEY_MINUS:
			return shift ? "_" : "-";
		case Input.KEY_MULTIPLY:
			return shift ? "*" : "*";
		case Input.KEY_NUMPAD0:
			return "0";
		case Input.KEY_NUMPAD1:
			return "1";
		case Input.KEY_NUMPAD2:
			return "2";
		case Input.KEY_NUMPAD3:
			return "3";
		case Input.KEY_NUMPAD4:
			return "4";
		case Input.KEY_NUMPAD5:
			return "5";
		case Input.KEY_NUMPAD6:
			return "6";
		case Input.KEY_NUMPAD7:
			return "7";
		case Input.KEY_NUMPAD8:
			return "8";
		case Input.KEY_NUMPAD9:
			return "9";
		case Input.KEY_NUMPADCOMMA:
			return shift ? "," : ",";
		case Input.KEY_NUMPADEQUALS:
			return shift ? "=" : "=";
		case Input.KEY_PERIOD:
			return shift ? ">" : ".";
		case Input.KEY_SEMICOLON:
			return shift ? ":" : ";";
		case Input.KEY_SLASH:
			return shift ? "?" : "/";
		case Input.KEY_SUBTRACT:
			return shift ? "_" : "-";
		case Input.KEY_TAB:
			return shift ? "\t" : "\t";
		case Input.KEY_YEN:
			return shift ? "\u00A5" : "\u00A5";
		case Input.KEY_0:
			return shift ? "!" : "0";
		case Input.KEY_1:
			return shift ? "\"" : "1";
		case Input.KEY_2:
			return shift ? "£" : "2";
		case Input.KEY_3:
			return shift ? "$" : "3";
		case Input.KEY_4:
			return shift ? "%" : "4";
		case Input.KEY_5:
			return shift ? "^" : "5";
		case Input.KEY_6:
			return shift ? "&" : "6";
		case Input.KEY_7:
			return shift ? "*" : "7";
		case Input.KEY_8:
			return shift ? "(" : "8";
		case Input.KEY_9:
			return shift ? ")" : "9";
		default:
			return shift ? (Input.getKeyName(code).charAt(0) + "").toUpperCase() : Input.getKeyName(code).charAt(0) + "";
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
	public void paint(Game game, Graphics g, float scaling) throws RenderException {
		game.getTheme().paintText(game, g, scaling, this);
	}

}

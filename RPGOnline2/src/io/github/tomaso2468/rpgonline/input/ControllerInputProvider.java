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
package io.github.tomaso2468.rpgonline.input;

import org.lwjgl.input.Controllers;
import org.newdawn.slick.Input;

import io.github.tomaso2468.rpgonline.RPGConfig;

/**
 * A system that provides binding controls for keyboards.
 * @author Tomas
 *
 */
public interface ControllerInputProvider {
	/**
	 * Command for any controller pressed.
	 */
	public static final int CONTROLLER_ANY = 0x0f000000;
	/**
	 * Command for controller 0.
	 */
	public static final int CONTROLLER_0 = 0x00000000;
	/**
	 * Command for controller 1.
	 */
	public static final int CONTROLLER_1 = 0x01000000;
	/**
	 * Command for controller 2.
	 */
	public static final int CONTROLLER_2 = 0x02000000;
	/**
	 * Command for controller 3.
	 */
	public static final int CONTROLLER_3 = 0x03000000;
	/**
	 * Command for controller 4.
	 */
	public static final int CONTROLLER_4 = 0x04000000;
	/**
	 * Command for controller 5.
	 */
	public static final int CONTROLLER_5 = 0x05000000;
	/**
	 * Command for controller 6.
	 */
	public static final int CONTROLLER_6 = 0x06000000;
	/**
	 * Command for controller 7.
	 */
	public static final int CONTROLLER_7 = 0x07000000;
	/**
	 * Command for controller 8.
	 */
	public static final int CONTROLLER_8 = 0x08000000;
	/**
	 * Command for controller 9.
	 */
	public static final int CONTROLLER_9 = 0x09000000;
	/**
	 * Command for controller 10.
	 */
	public static final int CONTROLLER_10 = 0x0A000000;
	/**
	 * Command for controller 11.
	 */
	public static final int CONTROLLER_11 = 0x0B000000;
	/**
	 * Command for controller 12.
	 */
	public static final int CONTROLLER_12 = 0x0C000000;
	/**
	 * Command for controller 13.
	 */
	public static final int CONTROLLER_13 = 0x0D000000;
	/**
	 * Command for controller 14.
	 */
	public static final int CONTROLLER_14 = 0x0E000000;
	/**
	 * Bitwise int for clearing controller information.
	 */
	public static final int CONTROLLER_CLEAR = 0xF0FFFFFF;
	
	/**
	 * XBOX A
	 */
	public static final int A = CONTROLLER_ANY | 0;
	/**
	 * XBOX B
	 */
	public static final int B = CONTROLLER_ANY | 1;
	/**
	 * XBOX X
	 */
	public static final int X = CONTROLLER_ANY | 2;
	/**
	 * XBOX Y
	 */
	public static final int Y = CONTROLLER_ANY | 3;
	/**
	 * XBOX L1
	 */
	public static final int L1 = CONTROLLER_ANY | 4;
	/**
	 * XBOX R1
	 */
	public static final int R1 = CONTROLLER_ANY | 5;
	/**
	 * XBOX BACK
	 */
	public static final int BACK = CONTROLLER_ANY | 6;
	/**
	 * XBOX START
	 */
	public static final int START = CONTROLLER_ANY | 7;
	/**
	 * XBOX L3
	 */
	public static final int L3 = CONTROLLER_ANY | 8;
	/**
	 * XBOX R3
	 */
	public static final int R3 = CONTROLLER_ANY | 9;
	/**
	 * XBOX DPAD UP
	 */
	public static final int DPAD_UP = CONTROLLER_ANY | 10;
	/**
	 * XBOX DPAD DOWN
	 */
	public static final int DPAD_DOWN = CONTROLLER_ANY | 11;
	/**
	 * XBOX DPAD LEFT
	 */
	public static final int DPAD_LEFT = CONTROLLER_ANY | 12;
	/**
	 * XBOX DPAD RIGHT
	 */
	public static final int DPAD_RIGHT = CONTROLLER_ANY | 13;
	/**
	 * XBOX Left Stick UP.
	 */
	public static final int LS_UP = CONTROLLER_ANY | 14;
	/**
	 * XBOX Left Stick DOWN.
	 */
	public static final int LS_DOWN = CONTROLLER_ANY | 15;
	/**
	 * XBOX Left Stick LEFT.
	 */
	public static final int LS_LEFT = CONTROLLER_ANY | 16;
	/**
	 * XBOX Left Stick RIGHT.
	 */
	public static final int LS_RIGHT = CONTROLLER_ANY | 17;
	/**
	 * XBOX Right Stick UP.
	 */
	public static final int RS_UP = CONTROLLER_ANY | 18;
	/**
	 * XBOX Right Stick DOWN.
	 */
	public static final int RS_DOWN = CONTROLLER_ANY | 19;
	/**
	 * XBOX Right Stick LEFT.
	 */
	public static final int RS_LEFT = CONTROLLER_ANY | 20;
	/**
	 * XBOX Right Stick RIGHT.
	 */
	public static final int RS_RIGHT = CONTROLLER_ANY | 21;
	/**
	 * XBOX L2.
	 */
	public static final int L2 = CONTROLLER_ANY | 22;
	/**
	 * XBOX R2.
	 */
	public static final int R2 = CONTROLLER_ANY | 23;
	/**
	 * Invalid key.
	 */
	public static final int INVALID = CONTROLLER_ANY | 0xf0ffffff;
	
	/**
	 * Determines if the controls are left handed.
	 * @return {@code true} if the controls are left handed, {@code false} otherwise.
	 */
	public boolean isLeftHanded();
	
	/**
	 * Sets if the controls are left handed.
	 * @param leftHanded {@code true} if the controls are left handed, {@code false} otherwise.
	 */
	public void setLeftHanded(boolean leftHanded);
	
	/**
	 * Gets the binding for the specified function.
	 * @param func A function ID.
	 * @return A keyboard binding.
	 */
	public int getBinding(String func);
	
	/**
	 * Binds the function to the specified button.
	 * @param func The function to bind.
	 * @param button The button to bind to.
	 */
	public void bind(String func, int button);
	
	/**
	 * Determines if the button is pressed.
	 * @param in The current input.
	 * @param key The button.
	 * @return {@code true} if the button is pressed, {@code false} otherwise.
	 */
	public static boolean isButtonPressed(Input in, int key) {
		if((key & 0xf0000000) == 0xf0000000) {
			return false;
		}
		
		boolean found = false;
		for (int i = 0; i < Controllers.getControllerCount(); i++) {
//			Controller c = Controllers.getController(i);
			//TODO Find compatible controllers.
		}
		if (!found) return false;
		
		int c = key & 0x0f000000 >> 24;
		if(c == 0xf) {
			c = -1;
		}
		int rkey = key & 0x00ffffff;
		switch(rkey) {
		case A:
			return in.isButtonPressed(0, c);
		case B:
			return in.isButtonPressed(1, c);
		case X:
			return in.isButtonPressed(2, c);
		case Y:
			return in.isButtonPressed(3, c);
		case L1:
			return in.isButtonPressed(4, c);
		case R1:
			return in.isButtonPressed(5, c);
		case BACK:
			return in.isButtonPressed(6, c);
		case START:
			return in.isButtonPressed(7, c);
		case L3:
			return in.isButtonPressed(8, c);
		case R3:
			return in.isButtonPressed(9, c);
		case LS_UP:
			return in.getAxisValue(c, 0) < -RPGConfig.getControllerActuation();
		case LS_DOWN:
			return in.getAxisValue(c, 0) > RPGConfig.getControllerActuation();
		case LS_RIGHT:
			return in.getAxisValue(c, 1) > RPGConfig.getControllerActuation();
		case LS_LEFT:
			return in.getAxisValue(c, 1) < -RPGConfig.getControllerActuation();
		case RS_UP:
			return in.getAxisValue(c, 2) < -RPGConfig.getControllerActuation();
		case RS_DOWN:
			return in.getAxisValue(c, 2) > RPGConfig.getControllerActuation();
		case RS_RIGHT:
			return in.getAxisValue(c, 3) > RPGConfig.getControllerActuation();
		case RS_LEFT:
			return in.getAxisValue(c, 3) < -RPGConfig.getControllerActuation();
		case L2:
			return in.getAxisValue(c, 4) > RPGConfig.getControllerActuation();
		case R2:
			return in.getAxisValue(c, 4) < -RPGConfig.getControllerActuation();
		case DPAD_UP:
			return in.isControllerUp(c);
		case DPAD_DOWN:
			return in.isControllerDown(c);
		case DPAD_LEFT:
			return in.isControllerLeft(c);
		case DPAD_RIGHT:
			return in.isControllerRight(c);
		default:
			if (rkey >= Short.MAX_VALUE && rkey < Short.MAX_VALUE * 2) {
				return in.isButtonPressed(rkey - Short.MAX_VALUE, c);
			}
			if (rkey >= Short.MAX_VALUE * 2 && rkey < Short.MAX_VALUE * 3) {
				return in.getAxisValue(c, rkey - Short.MAX_VALUE * 2) > RPGConfig.getControllerActuation();
			}
			if (rkey >= Short.MAX_VALUE * 3 && rkey < Short.MAX_VALUE * 4) {
				return in.getAxisValue(c, rkey - Short.MAX_VALUE * 3) < -RPGConfig.getControllerActuation();
			}
			if (rkey >= Short.MAX_VALUE * 4 && rkey < Short.MAX_VALUE * 5) {
				return in.isControlPressed(rkey = Short.MAX_VALUE * 4, c);
			}
			return false;
		}
	}
}

package rpgonline.input;

import org.newdawn.slick.Input;

import rpgonline.RPGConfig;

public interface ControllerInputProvider {
	public static final int CONTROLLER_ANY = 0x0f000000;
	public static final int CONTROLLER_0 = 0x00000000;
	public static final int CONTROLLER_1 = 0x01000000;
	public static final int CONTROLLER_2 = 0x02000000;
	public static final int CONTROLLER_3 = 0x03000000;
	public static final int CONTROLLER_4 = 0x04000000;
	public static final int CONTROLLER_5 = 0x05000000;
	public static final int CONTROLLER_6 = 0x06000000;
	public static final int CONTROLLER_7 = 0x07000000;
	public static final int CONTROLLER_8 = 0x08000000;
	public static final int CONTROLLER_9 = 0x09000000;
	public static final int CONTROLLER_10 = 0x0A000000;
	public static final int CONTROLLER_11 = 0x0B000000;
	public static final int CONTROLLER_12 = 0x0C000000;
	public static final int CONTROLLER_13 = 0x0D000000;
	public static final int CONTROLLER_14 = 0x0E000000;
	public static final int CONTROLLER_CLEAR = 0xF0FFFFFF;
	
	public static final int A = CONTROLLER_ANY | 0;
	public static final int B = CONTROLLER_ANY | 1;
	public static final int X = CONTROLLER_ANY | 2;
	public static final int Y = CONTROLLER_ANY | 3;
	public static final int L1 = CONTROLLER_ANY | 4;
	public static final int R1 = CONTROLLER_ANY | 5;
	public static final int BACK = CONTROLLER_ANY | 6;
	public static final int START = CONTROLLER_ANY | 7;
	public static final int L3 = CONTROLLER_ANY | 8;
	public static final int R3 = CONTROLLER_ANY | 9;
	public static final int DPAD_UP = CONTROLLER_ANY | 10;
	public static final int DPAD_DOWN = CONTROLLER_ANY | 11;
	public static final int DPAD_LEFT = CONTROLLER_ANY | 12;
	public static final int DPAD_RIGHT = CONTROLLER_ANY | 13;
	public static final int LS_UP = CONTROLLER_ANY | 14;
	public static final int LS_DOWN = CONTROLLER_ANY | 15;
	public static final int LS_LEFT = CONTROLLER_ANY | 16;
	public static final int LS_RIGHT = CONTROLLER_ANY | 17;
	public static final int RS_UP = CONTROLLER_ANY | 18;
	public static final int RS_DOWN = CONTROLLER_ANY | 19;
	public static final int RS_LEFT = CONTROLLER_ANY | 20;
	public static final int RS_RIGHT = CONTROLLER_ANY | 21;
	public static final int L2 = CONTROLLER_ANY | 22;
	public static final int R2 = CONTROLLER_ANY | 23;
	
	public boolean isLeftHanded();
	
	public void setLeftHanded(boolean leftHanded);
	
	public int getBinding(String func);
	
	public void bind(String func, int button);
	
	public static boolean isButtonPressed(Input in, int key) {
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

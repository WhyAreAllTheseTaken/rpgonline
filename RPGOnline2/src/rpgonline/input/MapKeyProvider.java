package rpgonline.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

public class MapKeyProvider implements KeyboardInputProvider {
	private final Map<String, Integer> bindings = new HashMap<String, Integer>();
	
	public MapKeyProvider() {
		bindings.put(InputUtils.WALK_NORTH, Keyboard.KEY_W);
		bindings.put(InputUtils.WALK_EAST, Keyboard.KEY_D);
		bindings.put(InputUtils.WALK_SOUTH, Keyboard.KEY_S);
		bindings.put(InputUtils.WALK_WEST, Keyboard.KEY_A);
		bindings.put(InputUtils.ZOOM_NORMAL, Keyboard.KEY_0);
		bindings.put(InputUtils.ZOOM_IN, Keyboard.KEY_EQUALS);
		bindings.put(InputUtils.ZOOM_OUT, Keyboard.KEY_MINUS);
		bindings.put(InputUtils.EXIT, Keyboard.KEY_ESCAPE);
		bindings.put(InputUtils.GUI_TOGGLE, Keyboard.KEY_F1);
	}
	
	@Override
	public int getKeyCodeForAction(String s) {
		if (bindings.containsKey(s)) {
			return bindings.get(s);
		} else {
			return -1;
		}
	}

	@Override
	public void bind(String s, int code) {
		bindings.put(s, code);
	}

}

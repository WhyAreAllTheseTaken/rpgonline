package io.github.tomaso2468.rpgonline.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

/**
 * An implementation of the KeyboardInputProvider based on a hash map.
 * @author Tomas
 *
 */
public class MapKeyProvider implements KeyboardInputProvider {
	/**
	 * The map to store bindings.
	 */
	private final Map<String, Integer> bindings = new HashMap<String, Integer>();
	
	/**
	 * Constructs a new MapKeyProvider.
	 */
	public MapKeyProvider() {
		bindings.put(InputUtils.WALK_NORTH, Keyboard.KEY_W);
		bindings.put(InputUtils.WALK_EAST, Keyboard.KEY_D);
		bindings.put(InputUtils.WALK_SOUTH, Keyboard.KEY_S);
		bindings.put(InputUtils.WALK_WEST, Keyboard.KEY_A);
		bindings.put(InputUtils.EXIT, Keyboard.KEY_ESCAPE);
		bindings.put(InputUtils.GUI_TOGGLE, Keyboard.KEY_F1);
		bindings.put(InputUtils.SPRINT, Keyboard.KEY_LSHIFT);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getKeyCodeForAction(String s) {
		if (bindings.containsKey(s)) {
			return bindings.get(s);
		} else {
			return -1;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(String s, int code) {
		bindings.put(s, code);
	}

}

package io.github.tomaso2468.rpgonline.input;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of ControllerInputProvider that uses a HashMap.
 * @author Tomas
 *
 */
public class MapControllerProvider implements ControllerInputProvider {
	/**
	 * If the controller is left handed.
	 */
	private boolean leftHanded;
	/**
	 * The button bindings.
	 */
	private final Map<String, Integer> bindings = new HashMap<String, Integer>();
	
	/**
	 * Constructs a new MapControllerProvider with default settings.
	 */
	public MapControllerProvider() {
		bindings.put(InputUtils.EXIT, BACK);
		bindings.put(InputUtils.GUI_TOGGLE, R3);
		bindings.put(InputUtils.SPRINT, Y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLeftHanded() {
		return leftHanded;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBinding(String func) {
		Integer i = bindings.get(func);
		
		if(i == null) {
			return ControllerInputProvider.INVALID;
		}
		
		return i;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLeftHanded(boolean leftHanded) {
		this.leftHanded = leftHanded;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(String func, int button) {
		bindings.put(func, button);
	}

}

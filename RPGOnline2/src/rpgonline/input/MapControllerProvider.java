package rpgonline.input;

import java.util.HashMap;
import java.util.Map;

public class MapControllerProvider implements ControllerInputProvider {
	private boolean leftHanded;
	private final Map<String, Integer> bindings = new HashMap<String, Integer>();
	
	public MapControllerProvider() {
		bindings.put(InputUtils.EXIT, BACK);
		bindings.put(InputUtils.ZOOM_IN, R1);
		bindings.put(InputUtils.ZOOM_OUT, L1);
		bindings.put(InputUtils.ZOOM_NORMAL, L3);
		bindings.put(InputUtils.GUI_TOGGLE, R3);
		bindings.put(InputUtils.SPRINT, Y);
	}
	
	@Override
	public boolean isLeftHanded() {
		return leftHanded;
	}

	@Override
	public int getBinding(String func) {
		return bindings.get(func);
	}

	@Override
	public void setLeftHanded(boolean leftHanded) {
		this.leftHanded = leftHanded;
	}

	@Override
	public void bind(String func, int button) {
		bindings.put(func, button);
	}

}

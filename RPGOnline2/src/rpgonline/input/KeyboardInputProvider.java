package rpgonline.input;

public interface KeyboardInputProvider {
	public int getKeyCodeForAction(String s);
	
	public void bind(String s, int code);
}

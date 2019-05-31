package rpgonline.abt;

import java.io.IOException;

public class InvalidHeaderException extends IOException {
	private static final long serialVersionUID = -6093121438455090244L;
	
	public InvalidHeaderException(String head, String format) {
		super("The header \"" + head + "\" is invalid for the format \"" + format + "\".");
	}
	
	public InvalidHeaderException(String head) {
		super("Invalid file header: " + head);
	}
	
	public InvalidHeaderException() {
		super("Invalid file header.");
	}
}

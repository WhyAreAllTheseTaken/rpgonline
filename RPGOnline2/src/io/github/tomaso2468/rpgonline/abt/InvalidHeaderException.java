package io.github.tomaso2468.rpgonline.abt;

import java.io.IOException;

/**
 * An exception thrown if an invalid file header is found.
 * @author Tomas
 *
 */
public class InvalidHeaderException extends IOException {
	/**
	 * UID for serialisation.
	 */
	private static final long serialVersionUID = -6093121438455090244L;
	
	/**
	 * Constructs a new {@code InvalidHeaderException}.
	 * @param head The data in the header that was invalid. This is usually a magic number.
	 * @param format The format that was attempted to be read from the file.
	 */
	public InvalidHeaderException(String head, String format) {
		super("The header \"" + head + "\" is invalid for the format \"" + format + "\".");
	}
	
	/**
	 * Constructs a new {@code InvalidHeaderException}.
	 * @param head The data in the header that was invalid. This is usually a magic number.
	 */
	public InvalidHeaderException(String head) {
		super("Invalid file header: " + head);
	}
	
	/**
	 * Constructs a new {@code InvalidHeaderException}.
	 */
	public InvalidHeaderException() {
		super("Invalid file header.");
	}
}

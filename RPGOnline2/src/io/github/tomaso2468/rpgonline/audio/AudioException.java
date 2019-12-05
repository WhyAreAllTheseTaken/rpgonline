package io.github.tomaso2468.rpgonline.audio;

public class AudioException extends Exception {
	private static final long serialVersionUID = 5262526420852950035L;

	public AudioException() {
	}

	public AudioException(String message) {
		super(message);
	}

	public AudioException(Throwable cause) {
		super(cause);
	}

	public AudioException(String message, Throwable cause) {
		super(message, cause);
	}

	public AudioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

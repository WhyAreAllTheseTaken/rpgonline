package io.github.tomaso2468.rpgonline.render;

public class RenderException extends Exception {
	private static final long serialVersionUID = 6117014228574493017L;

	public RenderException() {
	}

	public RenderException(String message) {
		super(message);
	}

	public RenderException(Throwable cause) {
		super(cause);
	}

	public RenderException(String message, Throwable cause) {
		super(message, cause);
	}

	public RenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

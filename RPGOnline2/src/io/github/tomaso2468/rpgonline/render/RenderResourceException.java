package io.github.tomaso2468.rpgonline.render;

public class RenderResourceException extends RenderException {
	private static final long serialVersionUID = 727783994232138493L;

	public RenderResourceException() {
	}

	public RenderResourceException(String message) {
		super(message);
	}

	public RenderResourceException(Throwable cause) {
		super(cause);
	}

	public RenderResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RenderResourceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

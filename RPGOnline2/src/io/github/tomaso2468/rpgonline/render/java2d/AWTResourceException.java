package io.github.tomaso2468.rpgonline.render.java2d;

import io.github.tomaso2468.rpgonline.render.RenderResourceException;

public class AWTResourceException extends RenderResourceException {
	private static final long serialVersionUID = 3109759948574840938L;

	public AWTResourceException() {
	}

	public AWTResourceException(String message) {
		super(message);
	}

	public AWTResourceException(Throwable cause) {
		super(cause);
	}

	public AWTResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public AWTResourceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

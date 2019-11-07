package io.github.tomaso2468.rpgonline.render.opengl;

import io.github.tomaso2468.rpgonline.render.RenderResourceException;

public class SlickResourceException extends RenderResourceException {
	private static final long serialVersionUID = -8268289187328138061L;

	public SlickResourceException() {
	}

	public SlickResourceException(String message) {
		super(message);
	}

	public SlickResourceException(Throwable cause) {
		super(cause);
	}

	public SlickResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public SlickResourceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

package io.github.tomaso2468.rpgonline.render;

public class TextureCreateException extends RenderException {
	private static final long serialVersionUID = -5032217754859562060L;

	public TextureCreateException() {
	}

	public TextureCreateException(String message) {
		super(message);
	}

	public TextureCreateException(Throwable cause) {
		super(cause);
	}

	public TextureCreateException(String message, Throwable cause) {
		super(message, cause);
	}

	public TextureCreateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

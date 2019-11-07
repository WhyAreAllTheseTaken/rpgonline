package io.github.tomaso2468.rpgonline.render;

public class ShaderException extends RenderException {
	private static final long serialVersionUID = -7385306851007233545L;

	public ShaderException() {
	}

	public ShaderException(String message) {
		super(message);
	}

	public ShaderException(Throwable cause) {
		super(cause);
	}

	public ShaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

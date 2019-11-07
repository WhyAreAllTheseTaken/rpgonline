package io.github.tomaso2468.rpgonline.render;

public class FeatureException extends RenderException {
	private static final long serialVersionUID = 2436196003761719848L;

	public FeatureException() {
	}

	public FeatureException(String message) {
		super(message);
	}

	public FeatureException(Throwable cause) {
		super(cause);
	}

	public FeatureException(String message, Throwable cause) {
		super(message, cause);
	}

	public FeatureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

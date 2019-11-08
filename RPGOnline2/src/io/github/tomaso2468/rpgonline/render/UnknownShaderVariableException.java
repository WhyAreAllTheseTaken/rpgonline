package io.github.tomaso2468.rpgonline.render;

public class UnknownShaderVariableException extends IllegalArgumentException {
	private static final long serialVersionUID = -4763766955646346073L;

	public UnknownShaderVariableException() {
	}

	public UnknownShaderVariableException(String s) {
		super(s);
	}

	public UnknownShaderVariableException(Throwable cause) {
		super(cause);
	}

	public UnknownShaderVariableException(String message, Throwable cause) {
		super(message, cause);
	}

}

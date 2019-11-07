package io.github.tomaso2468.rpgonline.render.opengl;

import org.lwjgl.opengl.GL30;

import io.github.tomaso2468.rpgonline.render.RenderResourceException;

public class FBOException extends RenderResourceException {
	private static final long serialVersionUID = -8993356929751894838L;

	public FBOException() {
	}

	public FBOException(String message) {
		super(message);
	}

	public FBOException(Throwable cause) {
		super(cause);
	}

	public FBOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FBOException(int code) {
		super(codeToString(code));
	}
	
	protected static String codeToString(int code) {
		switch(code) {
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
			return "GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT";
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
			return "GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT";
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
			return "GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER";
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
			return "GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER";
		case GL30.GL_FRAMEBUFFER_UNDEFINED:
			return "GL_FRAMEBUFFER_UNDEFINED";
		case GL30.GL_FRAMEBUFFER_UNSUPPORTED:
			return "GL_FRAMEBUFFER_UNSUPPORTED";
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE:
			return "GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE";
		default:
			return "Unknown FBO error: " + code;
		}
	}
}

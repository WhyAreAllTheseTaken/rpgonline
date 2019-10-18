package io.github.tomaso2468.rpgonline.render.opengl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import io.github.tomaso2468.rpgonline.input.Input;
import io.github.tomaso2468.rpgonline.render.Renderer;

public class LWJGL2Input implements Input {
	@SuppressWarnings("unused")
	private final Renderer renderer;
	
	public LWJGL2Input(Renderer renderer) {
		super();
		this.renderer = renderer;
	}

	@Override
	public boolean isKeyDown(int keyCodeForAction) {
		return Keyboard.isKeyDown(keyCodeForAction);
	}

	@Override
	public float getMouseX() {
		return Mouse.getX();
	}

	@Override
	public float getMouseY() {
		return Display.getHeight() - Mouse.getY();
	}

	@Override
	public float getMouseDX() {
		return Mouse.getDX();
	}

	@Override
	public float getMouseDY() {
		return Mouse.getDY();
	}

	@Override
	public int getButtonCount() {
		return Mouse.getButtonCount();
	}

	@Override
	public boolean isButtonDown(int i) {
		return Mouse.isButtonDown(i);
	}

	@Override
	public boolean hasWheel() {
		return Mouse.hasWheel();
	}

	@Override
	public float getDWheel() {
		return Mouse.getDWheel();
	}

}

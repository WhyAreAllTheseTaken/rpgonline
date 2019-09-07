package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * An effect that can be toggled on and off.
 * @author Tomas
 */
public class ToggledEffect extends MultiEffect {
	/**
	 * The toggled state of this effect.
	 */
	private boolean state;
	/**
	 * The effect to toggle.
	 */
	private PostEffect e;

	/**
	 * Constructs a new ToggledEffect with an initial state.
	 * @param e The effect to toggle.
	 * @param state The initial state of this effect.
	 */
	public ToggledEffect(PostEffect e, boolean state) {
		super(e);
		this.state = state;
		this.e = e;
	}
	
	/**
	 * Constructs a new ToggledEffect.
	 * @param e The effect to toggle.
	 */
	public ToggledEffect(PostEffect e) {
		this(e, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		if (state) {
			super.doPostProcess(container, game, buffer, g);
		} else {
			NullPostProcessEffect.INSTANCE.doPostProcess(container, game, buffer, g);
		}
	}

	/**
	 * Gets the state of this effect.
	 * @return {@code true} if this effect is active, {@code false} otherwise.
	 */
	public boolean isState() {
		return state;
	}

	/**
	 * Sets the state of this effect.
	 * @param state {@code true} if this effect is active, {@code false} otherwise.
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	
	/**
	 * Gets the effect that is toggled.
	 * @return A post effect instance.
	 */
	public PostEffect getEffect() {
		return e;
	}
	
	/**
	 * Sets the effect to toggle.
	 * @param e A non-null post effect.
	 */
	public void setEffect(PostEffect e) {
		this.e = e;
	}

}

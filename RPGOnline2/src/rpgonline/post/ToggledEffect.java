package rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ToggledEffect extends MultiEffect {
	private boolean state;
	private PostEffect e;

	public ToggledEffect(PostEffect e, boolean state) {
		super(e);
		this.state = state;
		this.e = e;
	}
	
	public ToggledEffect(PostEffect e) {
		this(e, false);
	}

	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		if (state) {
			super.doPostProcess(container, game, buffer, g);
		} else {
			NullPostProcessEffect.INSTANCE.doPostProcess(container, game, buffer, g);
		}
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public PostEffect getE() {
		return e;
	}

	public void setE(PostEffect e) {
		this.e = e;
	}

}

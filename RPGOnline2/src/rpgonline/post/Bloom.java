package rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Bloom extends MultiEffect {
	private Image buffer2;
	
	public Bloom(float value, int blur, float sigma) {
		super(new HighPass(value), new Blur(blur, sigma));
	}
	public Bloom(float value) {
		this(value, 5, 2);
	}
	public Bloom() {
		this(0.95f, 5, 2);
	}
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		if (buffer2 == null) {
			buffer2 = new Image(container.getWidth(), container.getHeight());
		} else if (container.getWidth() != buffer2.getWidth() || container.getHeight() != buffer2.getHeight()) {
			buffer2.destroy();
			buffer2 = new Image(container.getWidth(), container.getHeight());
		}
		
		g.copyArea(buffer2, 0, 0);
		
		super.doPostProcess(container, game, buffer, g);
		
		g.setDrawMode(Graphics.MODE_ADD);
		g.drawImage(buffer2, 0, 0);
		g.setDrawMode(Graphics.MODE_NORMAL);
	}
}

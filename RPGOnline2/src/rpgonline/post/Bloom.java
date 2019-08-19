package rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * An effect that causes bright areas of the screen to bleed.
 * @author Tomas
 * 
 * @see rpgonline.post.Blur
 * @see rpgonline.post.HighPass
 */
public class Bloom extends MultiEffect {
	/**
	 * A second buffer used for storing data.
	 */
	private Image buffer2;
	
	/**
	 * Constructs a new bloom effect.
	 * @param value The threshold for the bloom effect.
	 * @param blur The amount to blur by.
	 * @param sigma used by the blur effect.
	 */
	public Bloom(float value, int blur, float sigma) {
		super(new HighPass(value), new Blur(blur, sigma));
	}
	/**
	 * Constructs a new bloom effect.
	 * @param value The threshold for the bloom effect.
	 */
	public Bloom(float value) {
		this(value, 5, 2);
	}
	
	/**
	 * Constructs a new bloom effect with a threshold of 0.95f.
	 */
	public Bloom() {
		this(0.95f, 5, 2);
	}
	
	/**
	 * {@inheritDoc}
	 */
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

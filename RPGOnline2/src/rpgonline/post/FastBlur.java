package rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A fast (but very bad) blur algorithm.
 * @author Tomas
 */
public class FastBlur implements PostEffect {
	/**
	 * The buffer used for this effect.
	 */
	private Image buffer2;
	/**
	 * The size of the blur.
	 */
	private int size;
	
	/**
	 * Constructs a new FastBlur instance
	 * @param size The radius of the blur.
	 */
	public FastBlur(int size) {
		this.size = size * 2 + 1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		if (buffer2 == null) {
			buffer2 = new Image((int) container.getWidth() / (int) (size), container.getHeight() / (int) (size));
		} else if (container.getWidth() / (int) (size) != buffer2.getWidth() || container.getHeight() / (int) (size) != buffer2.getHeight()) {
			buffer2.destroy();
			buffer2 = new Image((int) container.getWidth() / (int) (size), container.getHeight() / (int) (size));
		}
		
		buffer.setFilter(Image.FILTER_LINEAR);
		buffer2.setFilter(Image.FILTER_LINEAR);
		
		g.drawImage(buffer.getScaledCopy(buffer2.getWidth(), buffer2.getHeight()), 0, 0);
		g.copyArea(buffer2, 0, 0);
		g.drawImage(buffer2.getScaledCopy(buffer.getWidth(), buffer.getHeight()), 0, 0);
	}

}

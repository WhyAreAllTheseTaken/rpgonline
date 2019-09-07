package io.github.tomaso2468.rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import io.github.tomaso2468.rpgonline.world.World;

/**
 * Multiple sky layers combined into one.
 * @author Tomas
 *
 */
public class MultiSkyLayer implements SkyLayer {
	/**
	 * The layers of this layer.
	 */
	private final SkyLayer[] skys;
	
	/**
	 * Constructs a new MultiSkyLayer.
	 * @param skys The layers to use.
	 */
	public MultiSkyLayer(SkyLayer... skys) {
		super();
		this.skys = skys;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		for (SkyLayer skyLayer : skys) {
			skyLayer.render(g, c, x, y, z, world, light);
		}
	}

}

package rpgonline.texture;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import rpgonline.RPGConfig;
import rpgonline.tile.Tile;
import rpgonline.world.World;

public class FilterTileTexture extends BasicTileTexture {
	public FilterTileTexture(String s, float x, float y) {
		super(s, x, y);
	}
	
	public FilterTileTexture(String s) {
		super(s, 0, 0);
	}
	
	@Override
	public boolean isCustom() {
		return true;
	}
	
	@Override
	public void render(Graphics g, long x, long y, long z, World w, String state, Tile t, float sx, float sy,
			float wind) {
		Graphics.setCurrent(g);
		
		TextureMap.getTexture(getTexture(x, y, z, w, state, t)).draw(sx * RPGConfig.getTileSize(), sy * RPGConfig.getTileSize(), new Color(Integer.parseUnsignedInt(state, 16)));
	}
}

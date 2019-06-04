package rpgonline;

import org.newdawn.slick.Color;

public class TextColor {
	public static ColorList DARK = new ColorList(new Color(255, 32, 32), Color.yellow, Color.orange, Color.green,
			Color.cyan, new Color(16, 128, 255), new Color(128, 32, 255), Color.magenta, Color.pink, Color.white,
			Color.lightGray);
	public static ColorList LIGHT = new ColorList(Color.red.darker(), Color.yellow.darker(), Color.orange.darker(),
			Color.green.darker(), Color.cyan.darker(), Color.blue, new Color(128, 32, 255).darker(),
			Color.magenta.darker(), Color.pink.darker(), Color.black, Color.darkGray);

	public static class ColorList {
		public final Color RED;
		public final Color YELLOW;
		public final Color ORANGE;
		public final Color GREEN;
		public final Color AQUA;
		public final Color BLUE;
		public final Color PURPLE;
		public final Color MAGENTA;
		public final Color PINK;
		public final Color BLACK_WHITE;
		public final Color GRAY = Color.gray;
		public final Color GRAY2;

		private ColorList(Color rED, Color yELLOW, Color oRANGE, Color gREEN, Color aQUA, Color bLUE, Color pURPLE,
				Color mAGENTA, Color pINK, Color bLACK_WHITE, Color gRAY) {
			super();
			RED = rED;
			YELLOW = yELLOW;
			ORANGE = oRANGE;
			GREEN = gREEN;
			AQUA = aQUA;
			BLUE = bLUE;
			PURPLE = pURPLE;
			MAGENTA = mAGENTA;
			PINK = pINK;
			BLACK_WHITE = bLACK_WHITE;
			GRAY2 = gRAY;
		}
	}
}

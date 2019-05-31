package rpgonline.post;

/**
 * A heat shader effect that changes over time that has had the edges fixed.
 * 
 * @author Tomas
 */
public class DynamicHeatShader2 extends MultiEffect {

	/**
	 * Creates the effect.
	 * 
	 * @param cmd The command to run to get the strength of the effect.
	 */
	public DynamicHeatShader2() {
		super(new DynamicHeatShader(), new ScaleEffect(1.01f));
	}

}
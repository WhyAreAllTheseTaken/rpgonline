package rpgonline.post;

/**
 * A shader creating a heat shader that has had the edges fixed.
 * 
 * @author Tomas
 */
public class HeatShader2 extends MultiEffect {

	/**
	 * Create the shader.
	 */
	public HeatShader2() {
		super(new HeatShader(), new ScaleEffect(1.01f));
	}

}

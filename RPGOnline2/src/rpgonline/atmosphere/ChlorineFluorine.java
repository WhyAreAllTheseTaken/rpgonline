package rpgonline.atmosphere;

public class ChlorineFluorine extends Atmosphere {
	public ChlorineFluorine(float chlorine, float fluorine, float density) {
		setDensity(density);
		
		addGas(AtmosphereGas.CHLORINE, chlorine);
		addGas(AtmosphereGas.FLUROINE, fluorine);
	}
}

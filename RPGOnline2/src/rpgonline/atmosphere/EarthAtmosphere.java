package rpgonline.atmosphere;

@Deprecated
public class EarthAtmosphere extends Atmosphere {
	public EarthAtmosphere() {
		setDensity(1);
		
		addGas(AtmosphereGas.OXYGEN, 20.95 * 0.01);
		addGas(AtmosphereGas.METHANE, 1700 * 0.0000001);
		addGas(AtmosphereGas.OZONE, 0.000004 * 0.01);
		addGas(AtmosphereGas.NITROGEN_OXIDE, 0.00003 * 0.01);
		addGas(AtmosphereGas.SULPHUR, 0.00001 * 0.01);
		addGas(AtmosphereGas.WATER_VAPOUR, 2 * 0.01);
	}
}

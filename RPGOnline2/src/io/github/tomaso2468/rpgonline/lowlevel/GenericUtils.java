package io.github.tomaso2468.rpgonline.lowlevel;

final class GenericUtils implements LowLevelUtils {
	@Override
	public String getCPUModel() {
		return "Unknown " + Runtime.getRuntime().availableProcessors() + " Core CPU";
	}

}

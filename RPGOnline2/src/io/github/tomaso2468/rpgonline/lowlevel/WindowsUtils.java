package io.github.tomaso2468.rpgonline.lowlevel;

import com.sun.jna.platform.win32.Advapi32Util;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

final class WindowsUtils implements LowLevelUtils {
	private String CPU = null;
	
	@Override
	public String getCPUModel() {
		if (CPU == null) {
			CPU = Advapi32Util.registryGetStringValue(HKEY_LOCAL_MACHINE,
					"HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\\", "ProcessorNameString");
		}
		return CPU;
	}
}

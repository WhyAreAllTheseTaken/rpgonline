package rpgonline.lowlevel;

import org.apache.commons.lang3.SystemUtils;

public interface LowLevelUtils {
	static LowLevelUtils getUtils() {
		if (SystemUtils.IS_OS_WINDOWS) {
			return new WindowsUtils();
		}
		if (SystemUtils.IS_OS_LINUX) {
			return new LinuxUtils();
		}
		if (SystemUtils.IS_OS_MAC) {
			return new MacUtils();
		}
		return new GenericUtils();
	}
	static final LowLevelUtils LLU = getUtils();
	
	public String getCPUModel();
}

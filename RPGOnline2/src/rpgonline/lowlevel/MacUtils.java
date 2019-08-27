package rpgonline.lowlevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.newdawn.slick.util.Log;

final class MacUtils implements LowLevelUtils {
	private String CPU = null;

	@Override
	public String getCPUModel() {
		if (CPU == null) {
			try {
				Process p = Runtime.getRuntime().exec("sysctl -n machdep.cpu.brand_string");

				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String s;
				while ((s = br.readLine()) != null) {
					sb.append(s + "\n");
				}

				p.waitFor();
				p.destroy();

				CPU = sb.toString();
			} catch (IOException | InterruptedException e) {
				Log.warn("Could not access processor information.", e);

				CPU = "Unknown " + Runtime.getRuntime().availableProcessors() + " Core CPU";
			}
		}
		return CPU;
	}

}

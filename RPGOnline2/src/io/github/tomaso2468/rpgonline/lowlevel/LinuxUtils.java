package io.github.tomaso2468.rpgonline.lowlevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.newdawn.slick.util.Log;

final class LinuxUtils implements LowLevelUtils {
	private String CPU = null;

	@Override
	public String getCPUModel() {
		if (CPU == null) {
			try {
				Process p = Runtime.getRuntime().exec("cat /proc/cpuinfo");

				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String s;
				while ((s = br.readLine()) != null) {
					sb.append(s + "\n");
				}

				p.waitFor();
				p.destroy();

				String[] lines = sb.toString().split("\n");
				for (String l : lines) {
					l = l.replace("\t", " ");
					l = l.trim();
					while (l.contains("  ")) { // 2 spaces
						l = l.replace("  ", " "); // (2 spaces, 1 space)
					}
					
					if (l.startsWith("model name :")) {
						CPU = l.split(":")[1].trim();
						break;
					}
				}
			} catch (IOException | InterruptedException e) {
				Log.warn("Could not access processor information.", e);

				CPU = "Unknown " + Runtime.getRuntime().availableProcessors() + " Core CPU";
			}
		}
		return CPU;
	}

}

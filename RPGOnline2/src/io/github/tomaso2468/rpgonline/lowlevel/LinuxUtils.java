/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.lowlevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.newdawn.slick.util.Log;

/**
 * Utils for Linux.
 * @author Tomaso2468
 *
 */
final class LinuxUtils implements LowLevelUtils {
	/**
	 * The CPU name.
	 */
	private String CPU = null;

	/**
	 * {@inheritDoc}
	 */
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

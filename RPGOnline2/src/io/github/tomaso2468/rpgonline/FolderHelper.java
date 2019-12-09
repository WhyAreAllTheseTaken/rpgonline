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
package io.github.tomaso2468.rpgonline;

import java.io.File;

/**
 * A class to assist with storing save files.
 * 
 * @author Tomaso2468
 */
public final class FolderHelper {
	/**
	 * Prevent instantiation
	 */
	private FolderHelper() {

	}

	/**
	 * <p>
	 * Gets a file representing a folder suitable for storing program save files /
	 * config files.
	 * </p>
	 * <p>
	 * The currently supported platforms are listed below:
	 * <table>
	 * <tr>
	 * <td>Windows</td>
	 * <td>%appdata%</td>
	 * </tr>
	 * <tr>
	 * <td>MacOS</td>
	 * <td>~/Library/Application Support/</td>
	 * </tr>
	 * <tr>
	 * <td>Linux/Other systems</td>
	 * <td>~/.rpgonline</td>
	 * </tr>
	 * </table>
	 * 
	 * @return A file representing a directory (that may not exist).
	 */
	public static File getAppDataFolder() {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("windows")) {
			return new File(System.getenv("APPDATA"));
		}

		if (os.contains("mac")) {
			return new File(System.getProperty("user.home"), "Library/Application Support");
		}

		return new File(System.getProperty("user.home"), ".rpgonline");
	}

	/**
	 * <p>
	 * Gets a file representing a specific folder with a given name suitable for
	 * storing program save files / config files.
	 * </p>
	 * <p>
	 * The currently supported platforms are listed below:
	 * <table>
	 * <tr>
	 * <td>Windows</td>
	 * <td>%appdata%\name</td>
	 * </tr>
	 * <tr>
	 * <td>MacOS</td>
	 * <td>~/Library/Application Support/name</td>
	 * </tr>
	 * <tr>
	 * <td>Linux/Other systems</td>
	 * <td>~/.rpgonline/name</td>
	 * </tr>
	 * </table>
	 * 
	 * @return A file representing a directory that exists.
	 */
	public static File createAppDataFolder(String... name) {
		File folder = getAppDataFolder();

		File f = folder;

		for (String s : name) {
			f = new File(f, s);
		}

		f.mkdirs();

		return f;
	}
}

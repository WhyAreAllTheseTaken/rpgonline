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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

import org.newdawn.slick.util.LogSystem;

/**
 * An implementation of a log system.
 * @author Tomaso2468
 *
 */
public class RPGLog implements LogSystem {
	/**
	 * The print writer to use for writing.
	 */
	private final PrintWriter pw;
	/**
	 * Whether detailed debug information should be printed (method information).
	 */
	private boolean detailed_debug = true;
	/**
	 * Whether debug information should be printed (debug).
	 */
	private boolean debug = true;

	/**
	 * Constructs a new RPGLog that writes to a file.
	 * @param f The file to write to.
	 * @param debug Whether debug information should be printed (INFO and debug).
	 * @param detailed_debug Whether detailed debug information should be printed (method information).
	 * @throws FileNotFoundException If the file specified does not exist.
	 */
	public RPGLog(File f, boolean debug, boolean detailed_debug) throws FileNotFoundException {
		super();
		this.debug = debug;
		this.detailed_debug = detailed_debug;
		this.pw = new PrintWriter(new FileOutputStream(f), true);
		info("RPGLog created with settings (debug=" + debug + ", detailed_debug=" + detailed_debug + ") in file "
				+ f.getName());
	}
	
	/**
	 * Constructs a new RPGLog.
	 * @param debug Whether debug information should be printed (INFO and debug).
	 * @param detailed_debug Whether detailed debug information should be printed (method information).
	 */
	public RPGLog(boolean debug, boolean detailed_debug) {
		super();
		this.debug = debug;
		this.pw = null;
		this.detailed_debug = detailed_debug;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void error(String message, Throwable e) {
		print("ERROR", message);
		e.printStackTrace(System.out);
		e.printStackTrace(pw);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void error(Throwable e) {
		print("ERROR", e.getMessage());
		e.printStackTrace(System.out);
		e.printStackTrace(pw);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void error(String message) {
		print("ERROR", message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void warn(String message) {
		print("WARN", message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void warn(String message, Throwable e) {
		print("WARN", message);
		e.printStackTrace(System.out);
		e.printStackTrace(pw);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void info(String message) {
		print("INFO", message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void debug(String message) {
		if (!debug) {
			return;
		}
		print("debug", message);
	}

	/**
	 * Helper method for printing.
	 * @param type The type of message.
	 * @param message The message.
	 */
	public void print(String type, String message) {
		String str;
		if (!detailed_debug) {
			str = String.format("%-30s %-7s %-15s %s", "[" + new Date() + "]", "[" + type + "]",
					"[" + Thread.currentThread().getName() + "]", message);
		} else {
			String caller = Thread.currentThread().getStackTrace()[4].getClassName();
			String method = Thread.currentThread().getStackTrace()[4].getMethodName();
			
			//Paulscode logger.
			if (caller.equals("rpgonline.audio.AudioManager$1")) {
				caller = Thread.currentThread().getStackTrace()[5].getClassName();
				method = Thread.currentThread().getStackTrace()[5].getMethodName();
			}
			
			str = String.format("%-30s %-7s %-15s %-60s %-25s %s", "[" + new Date() + "]", "[" + type + "]",
					"[" + Thread.currentThread().getName() + "]",
					"[" + caller + "]",
					"[" + method + "]", message);
		}

		System.out.println(str);

		if (pw == null) {
			pw.println(str);
		}
	}
}
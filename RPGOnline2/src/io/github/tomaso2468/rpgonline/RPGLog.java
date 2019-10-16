package io.github.tomaso2468.rpgonline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

import org.newdawn.slick.util.LogSystem;

public class RPGLog implements LogSystem {
	private final PrintWriter pw;
	private boolean detailed_debug = true;
	private boolean debug = true;

	public RPGLog(File f, boolean debug, boolean detailed_debug) throws FileNotFoundException {
		super();
		this.debug = debug;
		this.detailed_debug = detailed_debug;
		this.pw = new PrintWriter(new FileOutputStream(f), true);
		info("RPGLog created with settings (debug=" + debug + ", detailed_debug=" + detailed_debug + ") in file "
				+ f.getName());
	}

	@Override
	public void error(String message, Throwable e) {
		print("ERROR", message);
		e.printStackTrace(System.out);
		e.printStackTrace(pw);
	}

	@Override
	public void error(Throwable e) {
		print("ERROR", e.getMessage());
		e.printStackTrace(System.out);
		e.printStackTrace(pw);
	}

	@Override
	public void error(String message) {
		print("ERROR", message);
	}

	@Override
	public void warn(String message) {
		print("WARN", message);
	}

	@Override
	public void warn(String message, Throwable e) {
		print("WARN", message);
		e.printStackTrace(System.out);
		e.printStackTrace(pw);
	}

	@Override
	public void info(String message) {
		print("INFO", message);
	}

	@Override
	public void debug(String message) {
		if (!debug) {
			return;
		}
		print("debug", message);
	}

	public void print(String type, String message) {
		StringBuilder sb = new StringBuilder();
		if (detailed_debug) {
			sb.append("]\t[");
			sb.append(Thread.currentThread().getStackTrace()[4].getClassName());
			sb.append("]\t[");
			sb.append(Thread.currentThread().getStackTrace()[4].getMethodName());
		}

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
			
			str = String.format("%-30s %-7s %-15s %-50s %-24s %s", "[" + new Date() + "]", "[" + type + "]",
					"[" + Thread.currentThread().getName() + "]",
					"[" + caller + "]",
					"[" + method + "]", message);
		}

		System.out.println(str);

		pw.println(str);
	}
}
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

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Nonnull;

import org.newdawn.slick.util.Log;

/**
 * <p>
 * A class for managing version information.
 * </p>
 * <p>
 * Version numbers can be defined as follows (Values indicated in {@code [} and
 * {@code ]} and separated by {@code /} are said to be possible values: <br>
 * {@code M.m.p},&nbsp; {@code M.m.p-[rc/s/d]v},&nbsp; {@code M.m.p-[rc/s/d]v+t}
 * <br>
 * Each part is defined below.#
 * </p>
 * <ul>
 * <li>M - The major version number. Should be incremented for significant
 * updates or new editions.</li>
 * <li>m - The minor version number. Should be incremented for new
 * features.</li>
 * <li>p - The patch number. Should be incremented for bug fixes.</li>
 * <li>rc - Indicated that this version is a release candidate (almost ready to
 * be released). Release candidates should only feature bug fixes.</li>
 * <li>s - Indicated that this version is a snapshot. A version in which
 * features are still being added but the version is available to the
 * public.</li>
 * <li>d - Indicated that this version is a development version. This should be
 * used for non-public unfinished versions.</li>
 * <li>v - The release/snapshot/development version number.</li>
 * <li>t - A Unix time stamp (in seconds) (GMT) of the release date of this
 * version. This should not be in the future.</li>
 * </ul>
 * <p>
 * If the major version number is equal to {@code 0} then the version is said to
 * be an unfinished (beta) build and is unstable.
 * </p>
 * <p>
 * Version numbers can be compared using {@code compareTo()} method. The major
 * version number is compared first. Then, the minor version number is compared.
 * After that, the patch version is compared. Release candidates are deemed to
 * be higher than snapshots but snapshots and development versions are treated
 * the same.
 * </p>
 * 
 * @author Tomaso2468
 */
public class Version implements Comparable<Version> {
	/**
	 * Represents a version number in the future. This should be greater than all other values.
	 */
	public static final Version FUTURE = new Version(Integer.MAX_VALUE + "." + Integer.MAX_VALUE + "." + Integer.MAX_VALUE + "-p" + Integer.MAX_VALUE + "+" + Long.MAX_VALUE);
	
	/**
	 * An identifier for patch versions.
	 */
	public static final int TYPE_NORMAL_PATCH = 4;
	/**
	 * An identifier for normal versions.
	 */
	public static final int TYPE_NORMAL = 3;

	/**
	 * An identifier for unstable patch versions.
	 */
	public static final int TYPE_UNSTABLE_PATCH = 4;
	/**
	 * An identifier for unstable versions.
	 */
	public static final int TYPE_UNSTABLE = 1;
	/**
	 * An identifier for release candidate versions.
	 */
	public static final int TYPE_RC = 0;
	/**
	 * An identifier for snapshot versions.
	 */
	public static final int TYPE_SNAPSHOT = -1;
	/**
	 * An identifier for internal versions.
	 */
	public static final int TYPE_INTERNAL = -2;

	/**
	 * The major version number.
	 */
	private int major;
	/**
	 * The minor version number.
	 */
	private int minor;
	/**
	 * The patch version number.
	 */
	private int patch;
	/**
	 * The release candidate number.
	 */
	private int rc;
	/**
	 * The version type.
	 */
	private int type;
	/**
	 * The snapshot/development number.
	 */
	private int snapshot;
	/**
	 * This version release date as a Unix time stamp in milliseconds.
	 */
	private long date;
	/**
	 * An auto-generated name.
	 */
	private String name;
	/**
	 * An auto-generated name featuring a time stamp. If this value is parse again
	 * it should return the same values.
	 */
	private String name2;
	/**
	 * An auto-generated name with a human readable date.
	 */
	private String dated_string;

	/**
	 * <p>
	 * Parses a version number as a string.
	 * </p>
	 * <p>
	 * If a java version number is inputed (e.g. {@code 1.8.0_191} it is converted
	 * to the form {@code M.m.p} (e.g. {@code 1.8.191})
	 * </p>
	 * 
	 * @param verstr The string to parse.
	 */
	public Version(@Nonnull String verstr) {
		date = Long.MIN_VALUE;
		major = 0;
		minor = 0;
		patch = 0;
		rc = Integer.MAX_VALUE;
		type = TYPE_NORMAL;
		snapshot = Integer.MAX_VALUE;

		if (verstr.contains("NVIDIA")) {
			// Special case for NVIDIA driver version numbers.
			verstr = verstr.replace(" ", "").substring(0, verstr.indexOf("NVIDIA") - 1);
		}
		if (verstr.contains("_")) {
			// Special case for java version numbers.
			major = Integer.parseInt(verstr.split("_|\\.")[0]);
			minor = Integer.parseInt(verstr.split("_|\\.")[1]);
			patch = Integer.parseInt(verstr.split("_|\\.")[2]);
			snapshot = Integer.parseInt(verstr.split("_|\\.")[3]);
			type = TYPE_NORMAL_PATCH;
		} else {
			String[] parts = verstr.split("-|\\+")[0].split("\\.");

			int offset = 0;

			if (parts.length == 1) {
				major = Integer.parseInt(parts[0]);
			} else if (parts.length == 2) {
				major = Integer.parseInt(parts[0]);
				minor = Integer.parseInt(parts[1]);
			} else if (parts.length == 3) {
				major = Integer.parseInt(parts[0]);
				minor = Integer.parseInt(parts[1]);
				patch = Integer.parseInt(parts[2]);
			} else if (parts.length == 4) {
				major = Integer.parseInt(parts[0]);
				minor = Integer.parseInt(parts[1]);
				patch = Integer.parseInt(parts[2]);
				snapshot = Integer.parseInt(parts[3]);
				warn(major + "." + minor + "." + patch + ".",
						"Unexpected \".\". Assuming format x.y.z.w is used (where w is the dev_build).");
			} else {
				major = Integer.parseInt(parts[0]);
				minor = Integer.parseInt(parts[1]);
				patch = Integer.parseInt(parts[2]);
				snapshot = Integer.parseInt(parts[3]);
				warn(major + "." + minor + "." + patch + ".",
						"Unexpected \".\". Assuming format x.y.z.w is used (where w is the dev_build).");
				error(major + "." + minor + "." + patch + "." + snapshot + ".", "Unexpected \".\".");
			}
			if (major == 0) {
				type = TYPE_UNSTABLE;
			}
			if (snapshot != 0 && snapshot != Integer.MAX_VALUE) {
				type = type + 1;
			}

			offset += verstr.split("-|\\+")[0].length();

			while (offset < verstr.length() - 1) {
				if (verstr.charAt(offset) == '-') {
					offset += 1;
					if (verstr.charAt(offset) == 'p') {
						offset += 1;
						String num = verstr.substring(offset).split("-|\\+")[0];

						type = type + 1;
						rc = 0;
						snapshot = Integer.parseInt(num);

						offset += num.length();
					} else if (verstr.charAt(offset) == 'r' & verstr.charAt(offset + 1) == 'c') {
						offset += 2;
						String num = verstr.substring(offset).split("-|\\+")[0];

						type = TYPE_RC;
						rc = Integer.parseInt(num);
						snapshot = Integer.MAX_VALUE;

						offset += num.length();
					} else if (verstr.charAt(offset) == 's') {
						offset += 1;
						String num = verstr.substring(offset).split("-|\\+")[0];

						type = TYPE_SNAPSHOT;
						rc = 0;
						snapshot = Integer.parseInt(num);

						offset += num.length();
					} else if (verstr.charAt(offset) == 'd') {
						offset += 1;
						String num = verstr.substring(offset).split("-|\\+")[0];

						type = TYPE_INTERNAL;
						snapshot = Integer.parseInt(num);

						offset += num.length();
					} else {
						error(verstr.substring(0, offset), "Unknown subversion.");
					}
					continue;
				}
				if (verstr.charAt(offset) == '+') {
					offset += 1;

					String num = verstr.substring(offset).split("-|\\+")[0];

					date = Long.parseLong(num);

					offset += num.length();
					continue;
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append(major);
		sb.append(".");
		sb.append(minor);
		sb.append(".");
		sb.append(patch);

		if (type == TYPE_INTERNAL) {
			sb.append("-d" + snapshot);
		}

		if (type == TYPE_SNAPSHOT) {
			sb.append("-s" + snapshot);
		}

		if (type == TYPE_RC) {
			sb.append("-rc" + rc);
		}

		if (type == TYPE_NORMAL_PATCH || type == TYPE_UNSTABLE_PATCH) {
			sb.append("-p" + snapshot);
		}

		name = sb.toString();

		if (date != Long.MIN_VALUE) {
			sb.append("+" + date);
		}

		name2 = sb.toString();

		StringBuilder sb2 = new StringBuilder();
		sb2.append(name);
		if (date != Long.MIN_VALUE) {
			sb2.append(" ");
			sb2.append(new Date(date * 1000));
		}

		dated_string = sb2.toString();
	}

	/**
	 * Generates a warning for parsing errors.
	 * 
	 * @param mistake A substring ending where the mistake happened.
	 * @param type    The description of the mistake.
	 */
	private void warn(String mistake, String type) {
		Log.warn("Possible error at: " + mistake + " <- here. " + type);
	}

	/**
	 * Generates an exception for parsing errors.
	 * 
	 * @param mistake A substring ending where the mistake happened.
	 * @param type    The description of the mistake.
	 */
	private void error(String mistake, String type) {
		throw new RuntimeException(
				new ParseException("Error at: " + mistake + " <- here. Unexpected \".\".", mistake.length() - 1));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Version) {
			return compareTo((Version) obj) == 0;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Version o) {
		if (o == this) {
			return 0;
		}
		if (o.major == major) {
			if (o.minor == minor) {
				if (o.patch == patch) {
					if (o.type == type || (o.type < 0 && type < 0)) {
						if (type == TYPE_NORMAL || type == TYPE_UNSTABLE) {
							return (int) Math.signum((double) date - o.date);
						} else {
							if (type == TYPE_NORMAL_PATCH || type == TYPE_UNSTABLE_PATCH) {
								if (snapshot > o.snapshot) {
									return 1;
								}
								if (snapshot < o.snapshot) {
									return -1;
								}
							}
							if (type == TYPE_RC) {
								if (rc > o.rc) {
									return 1;
								}
								if (rc < o.rc) {
									return -1;
								}
							}
							if (type == TYPE_SNAPSHOT || type == TYPE_INTERNAL) {
								if (snapshot > o.snapshot) {
									return 1;
								}
								if (snapshot < o.snapshot) {
									return -1;
								}
							}
						}
					} else {
						if (type > o.type) {
							return 1;
						}
						if (type < o.type) {
							return -1;
						}
					}
				} else {
					if (patch > o.patch) {
						return 1;
					}
					if (patch < o.patch) {
						return -1;
					}
				}
			} else {
				if (minor > o.minor) {
					return 1;
				}
				if (minor < o.minor) {
					return -1;
				}
			}
		} else {
			if (major > o.major) {
				return 1;
			}
			if (major < o.major) {
				return -1;
			}
		}
		return (int) Math.signum((double) date - o.date);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name2;
	}

	/**
	 * Returns this version number in a simplified form.
	 * 
	 * @return A version number without the time stamp.
	 */
	@Nonnull
	public String toSimpleString() {
		return name;
	}

	/**
	 * Returns a version number with a human readable date.
	 * 
	 * @return A version number with a human readable date component.
	 */
	@Nonnull
	public String toDatedString() {
		return dated_string;
	}

	/**
	 * A method for testing.
	 * 
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		Version[] versions = { new Version("3.0.5"), new Version("3.0.6"), new Version("3.0.5-d5"),
				new Version("3.0.5-s7"), new Version("3.0.5-rc1"), new Version("3.0.5-rc2"),
				new Version("3.0.5-rc2+1000"), new Version("3.0.5-rc2+1500"), new Version("1.0"), new Version("2.0"),
				new Version("0.0"), };
		Arrays.parallelSort(versions);
		System.out.println(Arrays.toString(versions));

		System.out.println("Java Version: " + new Version(System.getProperty("java.version")));
		System.out.println("OS Version (String): " + System.getProperty("os.version"));
		System.out.println("OS Version: " + new Version(System.getProperty("os.version")));
	}
}

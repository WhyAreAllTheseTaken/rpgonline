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
package io.github.tomaso2468.rpgonline.audio;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.util.Log;

import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.SoundSystemLogger;
import paulscode.sound.codecs.CodecIBXM;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

/**
 * <p>
 * A class for managing audio. This class acts as a wrapper around the paulscode
 * sound system. system. The following formats are supported: {@code .ogg},
 * {@code .wav}, {@code .xm}, {@code .mod}, {@code .s3m}.
 * </p>
 * <p>
 * Sounds are mapped as strings to urls locating audio files. They are only
 * loaded once they are played. Music will be streamed. Volumes canm be set for
 * different categories of sounds.
 * </p>
 * <p>
 * For most audio settings (pitch & volume) the default value is 1 representing normal pitch and full volume. When sound positions are set they are assumed to be in real world coordinates.
 * </p> 
 * @author Tomaso2468
 *
 */
public final class AudioManager {
	/**
	 * Prevent instantiation
	 */
	private AudioManager() {

	}

	/**
	 * The current sound system.
	 */
	private static SoundSystem system;
	/**
	 * The roll-off factor for audio.
	 */
	private static float rf;

	/**
	 * A map of sound file locations.
	 */
	private static final Map<String, URL> sounds = new HashMap<>();

	/**
	 * The currently playing piece of music.
	 */
	private static AmbientMusic music = null;

	/**
	 * A thread used to fade out music.
	 */
	private static Thread fadeThread;

	/**
	 * A map of audio groups to volumes
	 */
	private static final Map<String, Float> volumes = new HashMap<>();

	/**
	 * A list of currently playing ambient sounds.
	 */
	private static final List<String> ambient = new ArrayList<>();

	/**
	 * A map of IDs to music.
	 */
	private static final Map<String, AmbientMusic> ambientMusic = new HashMap<>();

	static {
		SoundSystemConfig.setLogger(new SoundSystemLogger() {
			@Override
			public boolean errorCheck(boolean error, String classname, String message, int indent) {
				if (error)
					Log.error("Error in paulscode sound engine in class " + classname + ": " + message);
				return error;
			}

			@Override
			public void errorMessage(String classname, String message, int indent) {
				Log.error("Error in paulscode sound engine in class " + classname + ": " + message);
			}

			@Override
			public void importantMessage(String message, int indent) {
				Log.info(message);
			}

			@Override
			public void message(String message, int indent) {
				Log.debug(message);
			}

			@Override
			public void printExceptionMessage(Exception e, int indent) {
				Log.error("Exception in paulscode class.", e);
			}

			@Override
			public void printStackTrace(Exception e, int indent) {
				Log.error("Exception in paulscode class.", e);
			}
		});
		
		// Get compatible libraries
		boolean aLCompatible = SoundSystem.libraryCompatible(LibraryLWJGLOpenAL.class);
		boolean jSCompatible = SoundSystem.libraryCompatible(LibraryJavaSound.class);

		Class<?> libraryType;
		if (aLCompatible) {
			libraryType = LibraryLWJGLOpenAL.class; // OpenAL
		} else if (jSCompatible) {
			libraryType = LibraryJavaSound.class; // Java Sound
		} else {
			libraryType = Library.class; // "No Sound, Silent Mode"
		}
		try {
			Log.info("Attempting to use " + libraryType);
			system = new SoundSystem(libraryType);
		} catch (SoundSystemException sse) {
			Log.error("Error loading audio library: " + libraryType);
		}

		// Load codecs
		try {
			SoundSystemConfig.setCodec("wav", CodecWav.class);
		} catch (SoundSystemException e) {
			Log.error("Error linking with the CodecWav plug-in.", e);
		}
		try {
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
		} catch (SoundSystemException e) {
			Log.error("Error linking with the CodecJOrbis plug-in.", e);
		}
		try {
			SoundSystemConfig.setCodec("xm", CodecIBXM.class);
		} catch (SoundSystemException e) {
			Log.error("Error linking with the CodecIBXM plug-in.", e);
		}

		// Setup system
		rf = SoundSystemConfig.getDefaultRolloff();
		Log.info("Roll-off distance set to: " + rf);

		// Default volumes
		volumes.put("music", 1f);
		volumes.put("sound", 1f);
		volumes.put("ambient", 1f);

		Thread t = new Thread("Sound Daemon") {
			@Override
			public void run() {
				while (true) {
					system.checkFadeVolumes();

					for (int i = 0; i < ambient.size(); i++) {
						String s = ambient.get(i);

						if (!system.playing(s)) {
							ambient.remove(s);
						}
					}

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						Log.error(e);
					}
				}
			}
		};
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Adjusts the pitch by a random amount with a range either side of base.
	 * @param base The base pitch (usually 1)
	 * @param range The range (on either side) of the pitch that will be possible.
	 * @return A float value.
	 */
	public static float pitchAdjust(float base, float range) {
		return (float) ((Math.random() * 2 - 1) * range + base);
	}

	/**
	 * Creates a pitch with around 1 with a range in either direction.
	 * @param range The range (on either side) of the pitch that will be possible.
	 * @return A float value.
	 */
	public static float pitchAdjust(float range) {
		return pitchAdjust(1, range);
	}

	/**
	 * Deletes the sound system.
	 */
	public static void dispose() {
		system.cleanup();
	}

	/**
	 * Gets the current class of the sound library.
	 * @return
	 */
	public static Class<?> getSoundLibraryClass() {
		return SoundSystem.currentLibrary();
	}

	/**
	 * Sets the currently used sound library.
	 * @param c The library to use
	 * @throws SoundSystemException If an error occurred changing the library.
	 */
	public static void setSoundLibrary(Class<?> c) throws SoundSystemException {
		system.switchLibrary(c);
	}

	/**
	 * Plays a piece of background music.
	 * @param url The music location.
	 * @param loop Determines if the music should be looped.
	 * @param volume The volume to play the music at.
	 * @return The sound system ID of the music.
	 */
	private static String playBackgroundMusic(URL url, boolean loop, float volume) {
		String name = Long.toHexString(System.nanoTime()).toUpperCase() + ":BM:"
				+ Long.toHexString(System.currentTimeMillis()).toUpperCase() + ":" + url.toString();

		system.backgroundMusic(name, url, url.getPath(), loop);
		system.setVolume(name, volume);

		return name;
	}

	/**
	 * Gets the currently playing piece of music.
	 * @return A ambient music object or null if no music is playing.
	 */
	public static AmbientMusic getMusic() {
		return music;
	}

	/**
	 * Sets the currently playing piece of music.
	 * @param m A music object or null to stop all music.
	 */
	@SuppressWarnings("deprecation")
	public static void setMusic(AmbientMusic m) {
		if (m == music) {
			return;
		}
		if (fadeThread != null) {
			fadeThread.stop();
		}

		fadeThread = new Thread() {
			@Override
			public void run() {
				if (music != null) {
					for (int i = 0; i < 1000 / 50; i++) {
						for (String s : music.refs) {
							system.setVolume(s, system.getVolume(s) * 0.8f);
						}
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							Log.error(e);
						}
					}
					for (String s : music.refs) {
						system.stop(s);
					}
				}
				music = m;

				fadeThread = null;

				if (music != null) {
					String[] sounds = music.getSounds();

					for (int i = 0; i < sounds.length; i++) {
						String s = sounds[i];
						String g = music.getGroups()[i];
						float v = music.getVolumes()[i];

						music.refs[i] = playBackgroundMusic(AudioManager.sounds.get(s), true, volumes.get(g) * v);
					}
				}
			}
		};
		fadeThread.start();
	}

	/**
	 * sets the currently playing music based off of a sound ID.
	 * @param s A sound ID
	 */
	public static void setMusic(String s) {
		if (music != null && music.getSounds().length == 1 && music.getSounds()[0].equals(s)) {
			return;
		}

		setMusic(new AmbientMusic(s));
	}

	/**
	 * Sets the currently playing music based on a music ID.
	 * @param s A music ID.
	 */
	public static void setMusicID(String s) {
		AmbientMusic m = getAmbientMusic(s);

		if (m == null && !s.equals("null")) {
			Log.error("Could not find ambient music with ID " + s);
		}

		setMusic(m);
	}

	/**
	 * Sets the volume of a specified sound group.
	 * @param g A sound group ID.
	 * @param v The desired volume.
	 */
	public static void setGroupVolume(String g, float v) {
		volumes.put(g, v);
	}

	/**
	 * Gets the current volume of a sound group.
	 * @param g A sound group ID.
	 * @return The current volume of a sound group.
	 */
	public static float getGroupVolume(String g) {
		return volumes.get(g);
	}

	/**
	 * Sets the current volume of all audio (this is multiplied with all channels).
	 * @param v The desired volume.
	 */
	public static void setMasterVolume(float v) {
		system.setMasterVolume(v);
	}

	/**
	 * Gets the current volume of all audio.
	 * @return The current master volume.
	 */
	public static float getMasterVolume() {
		return system.getMasterVolume();
	}

	/**
	 * Sets the current volume of music.
	 * @param v The desired volume.
	 */
	public static void setMusicVolume(float v) {
		float old = getMusicVolume();

		setGroupVolume("music", v);

		float factor = v / old;

		if (music != null) {
			for (String s : music.refs) {
				system.setVolume(s, system.getVolume(s) * factor);
			}
		}
	}

	/**
	 * Gets the current volume of music.
	 * @return The current volume of music.
	 */
	public static float getMusicVolume() {
		return getGroupVolume("music");
	}

	/**
	 * Sets the volume of normal (non-ambient) sounds.
	 * @param v The desired volume.
	 */
	public static void setSoundVolume(float v) {
		setGroupVolume("sound", v);
	}

	/**
	 * Gets the current volume of normal (non-ambient) sounds.
	 * @return The current volume.
	 */
	public static float getSoundVolume() {
		return getGroupVolume("sound");
	}

	/**
	 * Sets the current volume of ambient sounds.
	 * @param v The desired volume.
	 */
	public static void setAmbientVolume(float v) {
		float old = getAmbientVolume();

		setGroupVolume("ambient", v);

		float factor = v / old;

		for (String s : ambient) {
			if (system.playing(s)) {
				system.setVolume(s, system.getVolume(s) * factor);
			}
		}
	}

	/**
	 * Gets the current volume of ambient sounds.
	 * @return The current volume.
	 */
	public static float getAmbientVolume() {
		return getGroupVolume("ambient");
	}

	/**
	 * Plays a sound at a specified location with a pitch, volume and velocity.
	 * @param name The sound ID to play.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The position of the sound (horizontal).
	 * @param y The position of the sound (vertical).
	 * @param z The position of the sound (depth).
	 * @param loop Set to true if the sound should be looped.
	 * @param dx The horizontal velocity of the sound.
	 * @param dy The vertical velocity of the sound.
	 * @param dz The depth velocity of the sound.
	 * @return The internal sound ID.
	 */
	public static String playSound(String name, float v, float p, float x, float y, float z, boolean loop, float dx,
			float dy, float dz) {
		String s = system.quickPlay(false, sounds.get(name), sounds.get(name).getPath(), loop, x, y, z,
				SoundSystemConfig.ATTENUATION_ROLLOFF, rf);
		system.setVolume(s, v * getSoundVolume());
		system.setPitch(s, p);
		system.setVelocity(s, dx, dy, dz);

		return s;
	}

	/**
	 * Plays a sound at a specified location with a pitch, volume and velocity without looping.
	 * @param name The sound ID to play.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The position of the sound (horizontal).
	 * @param y The position of the sound (vertical).
	 * @param z The position of the sound (depth).
	 * @param dx The horizontal velocity of the sound.
	 * @param dy The vertical velocity of the sound.
	 * @param dz The depth velocity of the sound.
	 * @return The internal sound ID.
	 */
	public static String playSound(String name, float v, float p, float x, float y, float z, float dx, float dy,
			float dz) {
		return playSound(name, v, p, x, y, z, false, dx, dy, dz);
	}

	/**
	 * Plays a sound at a specified location with a pitch and volume without velocity or looping.
	 * @param name The sound ID to play.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The position of the sound (horizontal).
	 * @param y The position of the sound (vertical).
	 * @param z The position of the sound (depth).
	 * @return The internal sound ID.
	 */
	public static String playSound(String name, float v, float p, float x, float y, float z) {
		return playSound(name, v, p, x, y, z, false, 0, 0, 0);
	}

	/**
	 * Plays a sound at a specified location at the specified volume without velocity or looping and with a normal pitch.
	 * @param name The sound ID to play.
	 * @param v The volume of the sound.
	 * @param x The position of the sound (horizontal).
	 * @param y The position of the sound (vertical).
	 * @param z The position of the sound (depth).
	 * @return The internal sound ID.
	 */
	public static String playSound(String name, float v, float x, float y, float z) {
		return playSound(name, v, 1, x, y, z, false, 0, 0, 0);
	}

	/**
	 * Plays a sound at a specified location without velocity or looping and with a normal pitch and full volume.
	 * @param name The sound ID to play.
	 * @param x The position of the sound (horizontal).
	 * @param y The position of the sound (vertical).
	 * @param z The position of the sound (depth).
	 * @return The internal sound ID.
	 */
	public static String playSound(String name, float x, float y, float z) {
		return playSound(name, 1, 1, x, y, z, false, 0, 0, 0);
	}

	/**
	 * Plays an ambient sound with a specified volume, pitch and position.
	 * @param name The sound ID to play.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The position of the sound (horizontal).
	 * @param y The position of the sound (vertical).
	 * @param z The position of the sound (depth).
	 * @param loop If the sound should be looped.
	 * @return The internal sound ID.
	 */
	public static String playAmbient(String name, float v, float p, float x, float y, float z, boolean loop) {
		String s = system.quickPlay(loop, sounds.get(name), sounds.get(name).getPath(), loop, x, y, z,
				SoundSystemConfig.ATTENUATION_ROLLOFF, rf);
		system.setVolume(s, v);
		system.setPitch(s, p);

		ambient.add(s);
		return s;
	}

	/**
	 * Plays an ambient sound with a specified volume, pitch and position without looping.
	 * @param name The sound ID to play.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The position of the sound (horizontal).
	 * @param y The position of the sound (vertical).
	 * @param z The position of the sound (depth).
	 * @return The internal sound ID.
	 */
	public static String playAmbient(String name, float v, float p, float x, float y, float z) {
		return playAmbient(name, v, p, x, y, z, false);
	}

	/**
	 * Sets the position of the player in 3D coordinates.
	 * @param x The horizontal position of the player.
	 * @param y The vertical position of the player.
	 * @param z The depth position of the player.
	 */
	public static void setPlayerPos(float x, float y, float z) {
		system.setListenerPosition(x, z, y);
	}

	/**
	 * Sets the velocity of the player in 3D coordinates.
	 * @param x The horizontal velocity of the player.
	 * @param y The vertical velocity of the player.
	 * @param z The depth velocity of the player.
	 */
	public static void setPlayerVelocity(float x, float y, float z) {
		system.setListenerVelocity(x, z, y);
	}

	/**
	 * Gets the factor used to determine how the volume of a sound rolls-off with distance.
	 * @return A float value.
	 */
	public static float getDistanceFactor() {
		return rf;
	}

	/**
	 * Sets the factor used to determine how the volume of a sound rolls-off with distance.
	 * @param rf The roll-off factor
	 */
	public static void setDistanceFactor(float rf) {
		AudioManager.rf = rf;
		SoundSystemConfig.setDefaultRolloff(rf);
	}

	/**
	 * Adds a sound.
	 * @param id The sound ID.
	 * @param loc The location of the sound.
	 */
	public static void addSound(String id, URL loc) {
		sounds.put(id, loc);
	}

	/**
	 * Gets the sound system.
	 * @return A non-null sound system object.
	 */
	public static SoundSystem getSystem() {
		return system;
	}

	/**
	 * Gets a piece of ambient music with the specified ID.
	 * @param id The music ID.
	 * @return An ambient music object or null.
	 */
	public static AmbientMusic getAmbientMusic(String id) {
		return ambientMusic.get(id);
	}

	/**
	 * Maps a piece of ambient music to an ID.
	 * @param id The music ID.
	 * @param m An ambient music object
	 */
	public static void setAmbientMusic(String id, AmbientMusic m) {
		ambientMusic.put(id, m);
	}

	/**
	 * Stops all ambient sounds.
	 */
	public static void stopAmbient() {
		for (int i = 0; i < ambient.size(); i++) {
			String s = ambient.get(i);

			if (system.playing(s)) {
				system.stop(s);
			}
		}
	}
}

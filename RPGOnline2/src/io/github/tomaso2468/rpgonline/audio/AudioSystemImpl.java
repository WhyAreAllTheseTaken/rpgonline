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

public class AudioSystemImpl implements AudioSystem {
	/**
	 * The current sound system.
	 */
	private SoundSystem system;
	/**
	 * The roll-off factor for audio.
	 */
	private float rf;

	/**
	 * A map of sound file locations.
	 */
	private final Map<String, URL> sounds = new HashMap<>();

	/**
	 * The currently playing piece of music.
	 */
	private AmbientMusic music = null;

	/**
	 * A thread used to fade out music.
	 */
	private Thread fadeThread;

	/**
	 * A map of audio groups to volumes
	 */
	private final Map<String, Float> volumes = new HashMap<>();

	/**
	 * A list of currently playing ambient sounds.
	 */
	private final List<String> ambient = new ArrayList<>();

	/**
	 * A map of IDs to music.
	 */
	private final Map<String, AmbientMusic> ambientMusic = new HashMap<>();

	/**
	 * The thread for the AudioSystem.
	 */
	private Thread audioThread;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
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

		audioThread = new Thread("Sound Daemon") {
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
		audioThread.setDaemon(true);
		audioThread.start();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void dispose() {
		if (fadeThread != null && fadeThread.isAlive()) {
			fadeThread.stop();
		}
		audioThread.stop();
		system.cleanup();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getSoundLibraryClass() {
		return SoundSystem.currentLibrary();
	}

	/**
	 * Sets the currently used sound library.
	 * 
	 * @param c The library to use
	 * @throws SoundSystemException If an error occurred changing the library.
	 */
	public void setSoundLibrary(Class<?> c) throws SoundSystemException {
		system.switchLibrary(c);
	}

	/**
	 * Plays a piece of background music.
	 * 
	 * @param url    The music location.
	 * @param loop   Determines if the music should be looped.
	 * @param volume The volume to play the music at.
	 * @return The sound system ID of the music.
	 */
	private String playBackgroundMusic(URL url, boolean loop, float volume) {
		String name = Long.toHexString(System.nanoTime()).toUpperCase() + ":BM:"
				+ Long.toHexString(System.currentTimeMillis()).toUpperCase() + ":" + url.toString();

		system.backgroundMusic(name, url, url.getPath(), loop);
		system.setVolume(name, volume);

		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AmbientMusic getMusic() {
		return music;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMusic(AmbientMusic m) {
		if (m == music) {
			return;
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

						music.refs[i] = playBackgroundMusic(AudioSystemImpl.this.sounds.get(s), true, volumes.get(g) * v);
					}
				}
			}
		};
		fadeThread.start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMusic(String s) {
		if (music != null && music.getSounds().length == 1 && music.getSounds()[0].equals(s)) {
			return;
		}

		setMusic(new AmbientMusic(s));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMusicID(String s) {
		AmbientMusic m = getAmbientMusic(s);

		if (m == null && !s.equals("null")) {
			Log.error("Could not find ambient music with ID " + s);
		}

		setMusic(m);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGroupVolume(String g, float v) {
		volumes.put(g, v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getGroupVolume(String g) {
		return volumes.get(g);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMasterVolume(float v) {
		system.setMasterVolume(v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getMasterVolume() {
		return system.getMasterVolume();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMusicVolume(float v) {
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
	 * {@inheritDoc}
	 */
	@Override
	public float getMusicVolume() {
		return getGroupVolume("music");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSoundVolume(float v) {
		setGroupVolume("sound", v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getSoundVolume() {
		return getGroupVolume("sound");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAmbientVolume(float v) {
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
	 * {@inheritDoc}
	 */
	@Override
	public float getAmbientVolume() {
		return getGroupVolume("ambient");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String playSound(String name, float v, float p, float x, float y, float z, boolean loop, float dx, float dy,
			float dz) {
		String s = system.quickPlay(false, sounds.get(name), sounds.get(name).getPath(), loop, x, y, z,
				SoundSystemConfig.ATTENUATION_ROLLOFF, rf);
		system.setVolume(s, v * getSoundVolume());
		system.setPitch(s, p);
		system.setVelocity(s, dx, dy, dz);

		return s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String playSound(String name, float v, float p, float x, float y, float z, float dx, float dy, float dz) {
		return playSound(name, v, p, x, y, z, false, dx, dy, dz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String playSound(String name, float v, float p, float x, float y, float z) {
		return playSound(name, v, p, x, y, z, false, 0, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String playSound(String name, float v, float x, float y, float z) {
		return playSound(name, v, 1, x, y, z, false, 0, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String playSound(String name, float x, float y, float z) {
		return playSound(name, 1, 1, x, y, z, false, 0, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String playAmbient(String name, float v, float p, float x, float y, float z, boolean loop) {
		String s = system.quickPlay(loop, sounds.get(name), sounds.get(name).getPath(), loop, x, y, z,
				SoundSystemConfig.ATTENUATION_ROLLOFF, rf);
		system.setVolume(s, v);
		system.setPitch(s, p);

		ambient.add(s);
		return s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String playAmbient(String name, float v, float p, float x, float y, float z) {
		return playAmbient(name, v, p, x, y, z, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayerPos(float x, float y, float z) {
		system.setListenerPosition(x, z, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayerVelocity(float x, float y, float z) {
		system.setListenerVelocity(x, z, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getDistanceFactor() {
		return rf;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDistanceFactor(float rf) {
		this.rf = rf;
		SoundSystemConfig.setDefaultRolloff(rf);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSound(String id, URL loc) {
		sounds.put(id, loc);
	}

	/**
	 * Gets the sound system.
	 * 
	 * @return A non-null sound system object.
	 */
	public SoundSystem getSystem() {
		return system;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AmbientMusic getAmbientMusic(String id) {
		return ambientMusic.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAmbientMusic(String id, AmbientMusic m) {
		ambientMusic.put(id, m);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopAmbient() {
		for (int i = 0; i < ambient.size(); i++) {
			String s = ambient.get(i);

			if (system.playing(s)) {
				system.stop(s);
			}
		}
	}
}

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

import javax.annotation.Nonnull;

/**
 * <p>
 * A class for storing Ambient Music.
 * </p>
 * <p>
 * Ambient music will loop until changed (using cross-fading). Through the use
 * of groups individual parts of music can be changed to perform vertical
 * mixing. Horizontal mixing currently has to be done using
 * {@link io.github.tomaso2468.rpgonline.audio.AudioManager#setMusic(AmbientMusic)}
 * </p>
 * 
 * @author Tomaso2468
 */
public class AmbientMusic {
	/**
	 * The set of sounds associated with this piece of music.
	 */
	private final String[] sounds;
	/**
	 * The sound groups associated with the sounds.
	 */
	private final String[] groups;
	/**
	 * The volumes associated with each sound.
	 */
	private final float[] volumes;
	/**
	 * An internally-used list of references to currently-playing audio.
	 * 
	 * @see io.github.tomaso2468.rpgonline.audio.AudioManager#setMusic(AmbientMusic)
	 */
	final String[] refs;

	/**
	 * Constructs a new piece of ambient music using 3 arrays of equal length.
	 * 
	 * @param sounds  The set of sounds associated with this piece of music.
	 * @param groups  The sound groups associated with the each sound.
	 * @param volumes The volumes associated with each sound.
	 */
	public AmbientMusic(@Nonnull String[] sounds, @Nonnull String[] groups, @Nonnull float[] volumes) {
		super();
		this.sounds = sounds;
		refs = new String[sounds.length];
		this.groups = groups;
		this.volumes = volumes;
	}

	/**
	 * Constructs a new piece of ambient music from one audio track on one sound group at the specified volume.
	 * @param sound The sound to loop.
	 * @param group The sound group associated with the sound.
	 * @param volume The volume to play the music at.
	 */
	public AmbientMusic(@Nonnull String sound, @Nonnull String group, @Nonnull float volume) {
		this(new String[] { sound }, new String[] { group }, new float[] { volume });
	}

	/**
	 * Constructs a new piece of ambient music from one audio track at a specified volume.
	 * @param sound The sound to loop.
	 * @param volume The volume to play the music at.
	 */
	public AmbientMusic(@Nonnull String sound, @Nonnull float volume) {
		this(sound, "music", volume);
	}

	/**
	 * Constructs a new piece of ambient music from one audio track at full volume.
	 * @param sound The sound to loop.
	 */
	public AmbientMusic(@Nonnull String sound) {
		this(sound, 1f);
	}

	/**
	 * Gets the list of sounds in this music.
	 * @return The array of sounds used by this music.
	 */
	@Nonnull
	public String[] getSounds() {
		return sounds;
	}

	/**
	 * Gets the list of sound groups. in this music.
	 * @return The array of sound group IDs used by this music.
	 */
	@Nonnull
	public String[] getGroups() {
		return groups;
	}

	/**
	 * Gets the list of volumes in this music.
	 * @return The array of volumes used by this music.
	 */
	@Nonnull
	public float[] getVolumes() {
		return volumes;
	}
}

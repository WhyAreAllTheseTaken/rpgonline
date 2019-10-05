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
package io.github.tomaso2468.rpgonline.cutscene;

/**
 * A cutscene made of a series of static images.
 * @author Tomaso2468
 *
 */
public class MultiTextureCutscene extends MultiCutscene {

	/**
	 * Constructs new MultiTextureCutscene.
	 * @param texture The texture prefix ID to use this is combined with a "." and an index starting at 0.
	 * @param length The length of the textures.
	 * @param time The length of each frame in seconds.
	 * @param music The music to play.
	 */
	public MultiTextureCutscene(String texture, int length, float time, String music) {
		super(generate(texture, length, time, music));
	}
	
	/**
	 * Constructs new MultiTextureCutscene.
	 * @param texture The texture prefix ID to use this is combined with a "." and an index starting at 0.
	 * @param length The length of the textures.
	 * @param time The length for each frame in seconds.
	 * @param music The music to play.
	 */
	public MultiTextureCutscene(String texture, int length, float[] time, String music) {
		super(generate(texture, length, time, music));
	}
	
	/**
	 * Generates a cutscene array for the constructor.
	 * @param texture The texture prefix ID to use this is combined with a "." and an index starting at 0.
	 * @param length The length of the textures.
	 * @param time The length of each frame in seconds.
	 * @param music The music to play.
	 * @return a cutscene array.
	 */
	private static Cutscene[] generate(String texture, int len, float time, String music) {
		TextureCutscene[] textures = new TextureCutscene[len];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new TextureCutscene(time, texture + "." + i, music);
		}
		
		return textures;
	}
	
	/**
	 * Generates a cutscene array for the constructor.
	 * @param texture The texture prefix ID to use this is combined with a "." and an index starting at 0.
	 * @param length The length of the textures.
	 * @param time The length for each frame in seconds.
	 * @param music The music to play.
	 * @return a cutscene array.
	 */
	private static Cutscene[] generate(String texture, int len, float[] time, String music) {
		TextureCutscene[] textures = new TextureCutscene[len];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new TextureCutscene(time[i], texture + "." + i, music);
		}
		
		return textures;
	}

}

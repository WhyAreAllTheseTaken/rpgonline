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
package io.github.tomaso2468.rpgonline.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

/**
 * An implementation of the KeyboardInputProvider based on a hash map.
 * @author Tomas
 *
 */
public class MapKeyProvider implements KeyboardInputProvider {
	/**
	 * The map to store bindings.
	 */
	private final Map<String, Integer> bindings = new HashMap<String, Integer>();
	
	/**
	 * Constructs a new MapKeyProvider.
	 */
	public MapKeyProvider() {
		bindings.put(InputUtils.WALK_NORTH, Keyboard.KEY_W);
		bindings.put(InputUtils.WALK_EAST, Keyboard.KEY_D);
		bindings.put(InputUtils.WALK_SOUTH, Keyboard.KEY_S);
		bindings.put(InputUtils.WALK_WEST, Keyboard.KEY_A);
		bindings.put(InputUtils.EXIT, Keyboard.KEY_ESCAPE);
		bindings.put(InputUtils.GUI_TOGGLE, Keyboard.KEY_F1);
		bindings.put(InputUtils.SPRINT, Keyboard.KEY_LSHIFT);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getKeyCodeForAction(String s) {
		if (bindings.containsKey(s)) {
			return bindings.get(s);
		} else {
			return -1;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(String s, int code) {
		bindings.put(s, code);
	}

}

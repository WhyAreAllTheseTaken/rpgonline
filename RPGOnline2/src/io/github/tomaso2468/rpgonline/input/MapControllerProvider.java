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

/**
 * An implementation of ControllerInputProvider that uses a HashMap.
 * @author Tomaso2468
 *
 */
public class MapControllerProvider implements ControllerInputProvider {
	/**
	 * If the controller is left handed.
	 */
	private boolean leftHanded;
	/**
	 * The button bindings.
	 */
	private final Map<String, Integer> bindings = new HashMap<String, Integer>();
	
	/**
	 * Constructs a new MapControllerProvider with default settings.
	 */
	public MapControllerProvider() {
		bindings.put(InputUtils.EXIT, BACK);
		bindings.put(InputUtils.GUI_TOGGLE, R3);
		bindings.put(InputUtils.SPRINT, Y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLeftHanded() {
		return leftHanded;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBinding(String func) {
		Integer i = bindings.get(func);
		
		if(i == null) {
			return ControllerInputProvider.INVALID;
		}
		
		return i;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLeftHanded(boolean leftHanded) {
		this.leftHanded = leftHanded;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(String func, int button) {
		bindings.put(func, button);
	}

}

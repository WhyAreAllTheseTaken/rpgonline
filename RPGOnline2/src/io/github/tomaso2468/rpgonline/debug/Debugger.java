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
package io.github.tomaso2468.rpgonline.debug;

import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.RPGConfig;

/**
 * The primary class for the debugging API.
 * @author Tomas
 * @see io.github.tomaso2468.rpgonline.RPGGame
 */
public final class Debugger {
	/**
	 Prevent instantiation
	 */
	private Debugger() {
		
	}
	
	/**
	 * The map of debug frames.
	 */
	private static final Map<Thread, DebugFrame> debug = new HashMap<>();
	/**
	 * The thread used for rendering.
	 */
	private static Thread renderThread;
	
	/**
	 * Starts the current debug frame. This will overright existing debug frames on this thread.
	 */
	public static final void start() {
		if (RPGConfig.isDebug()) {
			debug.put(Thread.currentThread(), new DebugFrame());
			start("total");
		}
	}
	
	/**
	 * Initialises this thread as the thread for render operations.
	 */
	public static final void initRender() {
		if (RPGConfig.isDebug()) renderThread = Thread.currentThread();
	}
	
	/**
	 * Stops the current debug frame.
	 */
	public static final void stop() {
		if (RPGConfig.isDebug()) stop("total");
	}
	
	/**
	 * Starts a task within a debug frame. This can be repeated multiple times per frame for events that occur many times.
	 * @param id The ID of the task.
	 */
	public static final void start(String id) {
		if (RPGConfig.isDebug()) {
			DebugFrame f = debug.get(Thread.currentThread());
			if(f != null) f.start(id);
		}
	}
	
	/**
	 * Stops a task within a debug frame. This can be repeated multiple times per frame for events that occur many times.
	 * @param id The ID of the task.
	 */
	public static final void stop(String id) {
		if (RPGConfig.isDebug()) {
			DebugFrame f = debug.get(Thread.currentThread());
			if(f != null) f.stop(id);
		}
	}
	
	/**
	 * Gets the most recent debug frame from the render thread.
	 * @return A possibly-incomplete debug frame object.
	 */
	public static final DebugFrame getRenderFrame() {
		return debug.get(renderThread);
	}
	
	/**
	 * Gets the most recent debug frame from the specified thread.
	 * @param t A non-null thread object.
	 * @return A possibly-incomplete debug frame object.
	 */
	public static final DebugFrame getFrame(Thread t) {
		return debug.get(t);
	}
	
	/**
	 * Gets the most recent debug frame from this thread.
	 * @return A possibly-incomplete debug frame object.
	 */
	public static final DebugFrame getFrame() {
		return getFrame(Thread.currentThread());
	}
}

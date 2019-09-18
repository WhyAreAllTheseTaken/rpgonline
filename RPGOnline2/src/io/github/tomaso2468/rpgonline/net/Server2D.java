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
package io.github.tomaso2468.rpgonline.net;

import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.net.packet.UpdatePacket;
import io.github.tomaso2468.rpgonline.world.LightSource.LightUpdate;

public interface Server2D extends Server {
	/**
	 * Called to indicate an update to an entity has occurred.
	 * @param up The entity information to update.
	 */ 
	public void updateEntity(UpdatePacket up);

	/**
	 * Adds an entity to the server.
	 * @param e The entity to add.
	 */
	public void addEntity(Entity e);

	/**
	 * Removes an entity from the server.
	 * @param e The entity to remove.
	 */
	public void removeEntity(Entity e);

	/**
	 * Called to indicate an update to a light has occurred.
	 * @param lightUpdate the update packet.
	 */ 
	public void updateLight(LightUpdate lightUpdate);
}

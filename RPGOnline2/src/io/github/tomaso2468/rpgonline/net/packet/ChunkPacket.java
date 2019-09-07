package io.github.tomaso2468.rpgonline.net.packet;

import io.github.tomaso2468.rpgonline.abt.TagGroup;
import io.github.tomaso2468.rpgonline.world.chunk.Chunk;

/**
 * Packet used for chunk data.
 * @author Tomas
 *
 */
public class ChunkPacket implements NetPacket {
	private static final long serialVersionUID = 968047869315854575L;
	private final TagGroup tg;

	public ChunkPacket(Chunk c) {
		tg = c.save();
	}

	public TagGroup getTg() {
		return tg;
	}
}

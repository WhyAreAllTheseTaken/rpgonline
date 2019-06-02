package rpgonline.net.packet;

import rpgonline.abt.TagGroup;
import rpgonline.world.chunk.Chunk;

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

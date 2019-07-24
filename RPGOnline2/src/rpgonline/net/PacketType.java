package rpgonline.net;

import java.io.DataInputStream;
import java.io.IOException;

import rpgonline.net.packet.NetPacket;

public interface PacketType {
	public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException;
}

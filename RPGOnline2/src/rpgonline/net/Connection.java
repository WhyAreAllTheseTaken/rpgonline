package rpgonline.net;

import java.io.Closeable;
import java.io.IOException;

import rpgonline.net.packet.NetPacket;

public interface Connection extends Closeable {
	public void send(NetPacket p) throws IOException;
	public boolean isAvaliable() throws IOException;
	public NetPacket getNext() throws IOException;
	public void encrypt() throws IOException;
}

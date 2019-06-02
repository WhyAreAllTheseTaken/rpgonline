package rpgonline.net;

import java.io.Closeable;

import rpgonline.net.packet.NetPacket;

public interface Connection extends Closeable {
	public void send(NetPacket p);
	public boolean isAvaliable();
	public NetPacket getNext();
	public void encrypt();
}

package rpgonline.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.util.Log;

import rpgonline.net.packet.NetPacket;

public class LocalConnectionGenerator {
	private boolean closed;
	private List<NetPacket> client = Collections.synchronizedList(new ArrayList<NetPacket>());
	private List<NetPacket> server = Collections.synchronizedList(new ArrayList<NetPacket>());

	public Connection getServer() {
		return new Connection() {
			@Override
			public void close() throws IOException {
				if (closed) {
					throw new IOException("Connection is already closed.");
				}
				closed = true;
			}

			@Override
			public void send(NetPacket p) throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				client.add(p);
			}

			@Override
			public boolean isAvaliable() throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				return server.size() > 0;
			}

			@Override
			public NetPacket getNext() throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				return server.get(0);
			}

			@Override
			public void encrypt() throws IOException {
				Log.warn("Server attempted encryption on local connection: Ignoring");
			}
		};
	}
	
	public Connection getClient() {
		return new Connection() {
			@Override
			public void close() throws IOException {
				if (closed) {
					throw new IOException("Connection is already closed.");
				}
				closed = true;
			}

			@Override
			public void send(NetPacket p) throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				server.add(p);
			}

			@Override
			public boolean isAvaliable() throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				return client.size() > 0;
			}

			@Override
			public NetPacket getNext() throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				return client.get(0);
			}

			@Override
			public void encrypt() throws IOException {
				Log.warn("Server attempted encryption on local connection: Ignoring");
			}
		};
	}
}

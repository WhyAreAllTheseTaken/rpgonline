package rpgonline.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

import org.newdawn.slick.util.Log;

import rpgonline.net.packet.AmbientPacket;
import rpgonline.net.packet.ChunkRequestPacket;
import rpgonline.net.packet.EntityRemovePacket;
import rpgonline.net.packet.KeyPacket;
import rpgonline.net.packet.LightsPacket;
import rpgonline.net.packet.LoginPacket;
import rpgonline.net.packet.ModePacket;
import rpgonline.net.packet.MovePacket;
import rpgonline.net.packet.MusicPacket;
import rpgonline.net.packet.NetPacket;
import rpgonline.net.packet.ServerInfoPacket;
import rpgonline.net.packet.SoundPacket;
import rpgonline.net.packet.StopAmbientPacket;
import rpgonline.net.packet.TextPacket;
import rpgonline.net.packet.WindPacket;
import rpgonline.world.LightSource;

/**
 * A connection that uses TCP over IP to send data. The data is sent as one or
 * more packets and are read in the order they are sent. First, the ID of the
 * packet is sent as an 8bit integer. Next, the data of the packet is sent.
 * 
 * @author Tomas
 *
 */
public class BasicPacketConnection extends AESSecurityCap implements Connection {
	/**
	 * The list of packets to send.
	 */
	private List<NetPacket> toSend = Collections.synchronizedList(new ArrayList<NetPacket>());
	/**
	 * The list of received packets/
	 */
	private List<NetPacket> recieved = Collections.synchronizedList(new ArrayList<NetPacket>());
	/**
	 * Determines if the system should be stopped.
	 */
	private boolean stopped = false;
	/**
	 * The cipher to encrypt with.
	 */
	private Cipher encryptCipher;
	/**
	 * The cipher to decrypt with.
	 */
	private Cipher decryptCipher;
	/**
	 * Determines if packets should be logged. This is set to true for encryption setup.
	 */
	private boolean logPackets = false;
	/**
	 * Determines if encyption is complete.
	 */
	private boolean encyptComplete = false;
	/**
	 * Constructs a new BasicPacketConnection.
	 * @param s The socket to connect to.
	 */
	public BasicPacketConnection(Socket s) {
		this(s, basicTypes());
	}

	/**
	 * A method that constructs an array of packet types for the default supported types an maps them to their IDs.
	 * @return A 256-length array of packet types.
	 */
	public static PacketType[] basicTypes() {
		PacketType[] types = new PacketType[0x100];

		types[NetPacket.PACKET_OBJECT & 0xFF] = new NetPacket.Type();
		types[AmbientPacket.PACKET_AMBIENT & 0xFF] = new AmbientPacket.Type();
		types[ChunkRequestPacket.PACKET_ID & 0xFF] = new ChunkRequestPacket.Type();
		types[EntityRemovePacket.PACKET_ID & 0xFF] = new EntityRemovePacket.Type();
		types[KeyPacket.PACKET_ID & 0xFF] = new KeyPacket.Type();
		types[LoginPacket.PACKET_ID & 0xFF] = new LoginPacket.Type();
		types[MovePacket.PACKET_ID & 0xFF] = new MovePacket.Type();
		types[MusicPacket.PACKET_ID & 0xFF] = new MusicPacket.Type();
		types[ServerInfoPacket.PACKET_ID & 0xFF] = new ServerInfoPacket.Type();
		types[SoundPacket.PACKET_ID & 0xFF] = new SoundPacket.Type();
		types[StopAmbientPacket.PACKET_ID & 0xFF] = new StopAmbientPacket.Type();
		types[WindPacket.PACKET_ID & 0xFF] = new WindPacket.Type();
		types[TextPacket.PACKET_ID & 0xFF] = new TextPacket.Type();
		types[LightsPacket.PACKET_ID & 0xFF] = new LightsPacket.Type();
		types[LightSource.LightUpdate.PACKET_ID & 0xFF] = new LightSource.LightUpdate.Type();
		types[ModePacket.PACKET_ID & 0xFF] = new ModePacket.Type();

		return types;
	}

	/**
	 * Constructs a new BasicPacketConnection.
	 * @param s The socket to connect to.
	 * @param types The array of packet types.
	 */
	public BasicPacketConnection(Socket s, PacketType[] types) {
		if (types.length != 0x100) {
			throw new IllegalArgumentException("Type array must be of length 256");
		}
		new Thread(toString()) {
			public void run() {
				try {
					OutputStream buffer = s.getOutputStream();
					DataOutputStream out = new DataOutputStream(buffer);
					DataInputStream in = new DataInputStream(s.getInputStream());

					while (!stopped) {
						if (encryptCipher != null && !encyptComplete) {
							Log.debug("Finalising encyption");
							out = new DataOutputStream(new CipherOutputStream(buffer, encryptCipher));
							in = new DataInputStream(new CipherInputStream(s.getInputStream(), decryptCipher));
							Log.info("Encryption complete.");
							encyptComplete = true;
						}
						
						while (toSend.size() > 0) {
							try {
								if(logPackets) {
									Log.debug("Writing: " + toSend.get(0));
								}
								toSend.get(0).write(out);
								toSend.remove(0);
							} catch (IOException e) {
								Log.error("Error writing packet.", e);
							}
							if(logPackets) Log.debug("Write complete.");
						}
						out.flush();

						while (in.available() > 0) {
							try {
								byte id = (byte) in.read();
								PacketType type = types[id & 0xFF];
								if (type == null) {
									throw new IllegalArgumentException("Unknown packet type: " + id);
								} else {
									recieved.add(type.readPacket(in));
								}
							} catch (ClassNotFoundException e) {
								Log.error("Error reading packet.", e);
							}
							if(logPackets) Log.debug("Reading.");
						}

						if (s.isClosed()) {
							stopped = true;
						}	

						Thread.yield();
					}

					s.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}.start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		stopped = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(NetPacket p) throws IOException {
		if (logPackets) {
			Log.debug("Sending " + p);
		}
		toSend.add(p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAvaliable() throws IOException {
		return recieved.size() > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NetPacket getNext() throws IOException {
		NetPacket p = recieved.get(0);
		recieved.remove(0);
		return p;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void encrypt() throws IOException {
		Log.info("Encrypting");
		
		logPackets = true;
		// Send public key.
		Log.debug("Sending key");
		send(new KeyPacket(getPublickey()));
		Log.debug("Sent key");
		
		// Wait for other public key.
		Log.debug("Waiting");
		while (true) {
			while (!isAvaliable()) {
				Thread.yield();
			}
			NetPacket next = getNext();
			if (next instanceof KeyPacket) {
				Log.debug("Packet recieved");
				PublicKey k;
				try {
					k = KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(((KeyPacket) next).key));
					setReceiverPublicKey(k);
					
					decryptCipher = Cipher.getInstance("AES");
					decryptCipher.init(Cipher.DECRYPT_MODE, generateKey());
					
					encryptCipher = Cipher.getInstance("AES");
					encryptCipher.init(Cipher.ENCRYPT_MODE, generateKey());
					Log.debug("Cipher generated");
				} catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
					Log.error("Error encypting connection", e);
					close();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logPackets = true;
				Log.debug("Exiting");
				return;
			} else {
				Log.warn("Discarding unencypted packet: " + next);
			}
		}
	}

}

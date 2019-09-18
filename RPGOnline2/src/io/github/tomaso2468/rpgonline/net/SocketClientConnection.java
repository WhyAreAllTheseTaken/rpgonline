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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.net.packet.KeyPacket;
import io.github.tomaso2468.rpgonline.net.packet.NetPacket;

/**
 * A client connection that connects to a server and uses java serialisation for IO.
 * @author Tomas
 */
public class SocketClientConnection extends AESSecurityCap implements Connection {
	/**
	 * Mode indicating to perform normal buffered serialisation.
	 */
	public static final int MODE_NORMAL = 0b0;
	/**
	 * Mode indicating to compress before sending data.
	 */
	public static final int MODE_COMPRESS = 0b1;
	/**
	 * Mode indicating to encrypt data.
	 */
	public static final int MODE_ENCRYPT = 0b10;
	/**
	 * Mode indicating to encrypt and compress data.
	 */
	public static final int MODE_ENCRYPT_COMPRESS = 0b11;

	/**
	 * The list of packets to send.
	 */
	private List<NetPacket> toSend = Collections.synchronizedList(new ArrayList<NetPacket>());
	/**
	 * The list of received packets.
	 */
	private List<NetPacket> recieved = Collections.synchronizedList(new ArrayList<NetPacket>());
	/**
	 * Determines if the connection is encrypted.
	 */
	private boolean encrypted = false;

	/**
	 * Determines if the connection has been closed.
	 */
	private boolean stopped;
	/**
	 * The encryption cipher.
	 */
	private Cipher encryptCipher;
	/**
	 * The decryption cipher.
	 */
	private Cipher decryptCipher;

	/**
	 * Constructs a new SocketClientConnection.
	 * @param address The address to connect to.
	 * @param port The port to connect to.
	 */
	public SocketClientConnection(String address, int port) {
		new Thread(toString()) {
			public void run() {
				boolean lastEncryptState = false;

				try {
					Log.info("Connecting to " + address + ":" + port);
					Socket s = new Socket(address, port);

					Log.debug("Mode: " + MODE_COMPRESS);
					s.getOutputStream().write(MODE_COMPRESS);

					Log.debug("Compressing data.");
					ObjectInputStream in = new ObjectInputStream(
							new BufferedInputStream(new GZIPInputStream(s.getInputStream())));
					ObjectOutputStream out = new ObjectOutputStream(
							new BufferedOutputStream(new GZIPOutputStream(s.getOutputStream())));

					while (!stopped) {
						if (lastEncryptState != encrypted) {
							if (encrypted) {
								Log.info("Switching to encrypted mode.");
								s.close();

								s = new Socket(address, port);

								s.getOutputStream().write(MODE_ENCRYPT_COMPRESS);

								in = new ObjectInputStream(new BufferedInputStream(
										new CipherInputStream(new GZIPInputStream(s.getInputStream()), decryptCipher)));
								out = new ObjectOutputStream(new BufferedOutputStream(new CipherOutputStream(
										new GZIPOutputStream(s.getOutputStream()), encryptCipher)));
							} else {
								s.close();

								s = new Socket(address, port);

								s.getOutputStream().write(MODE_COMPRESS);

								in = new ObjectInputStream(
										new BufferedInputStream(new GZIPInputStream(s.getInputStream())));
								out = new ObjectOutputStream(
										new BufferedOutputStream(new GZIPOutputStream(s.getOutputStream())));
							}
							lastEncryptState = encrypted;
						}

						while (toSend.size() > 0) {
							try {
								out.writeObject(toSend.get(0));
								toSend.remove(0);
							} catch (IOException e) {
								Log.error("Error writing packet.", e);
							}
						}

						while (in.available() > 0) {
							try {
								Object o = in.readObject();

								NetPacket p = (NetPacket) o;

								recieved.add(p);
							} catch (ClassNotFoundException e) {
								Log.error("Error reading packet.", e);
							}
						}

						if (s.isClosed()) {
							stopped = true;
						}

						Thread.yield();
					}

					s.close();
				} catch (IOException e) {
					Log.error("Error opening connection", e);
				}
			}
		}.start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(NetPacket p) {
		toSend.add(p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAvaliable() {
		return recieved.size() > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NetPacket getNext() {
		return recieved.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void encrypt() {
		Log.info("Attempting encryption.");
		
		Log.debug("Sending key");
		send(new KeyPacket(getPublickey()));

		Log.debug("Waiting for server key.");
		while (!isAvaliable()) {
			Thread.yield();
		}

		KeyPacket p = (KeyPacket) getNext();

		Log.debug("Encrypting");
		try {
			PublicKey k = KeyFactory.getInstance("AES").generatePublic(new X509EncodedKeySpec(p.key));
			setReceiverPublicKey(k);

			decryptCipher = Cipher.getInstance("AES");
			decryptCipher.init(Cipher.DECRYPT_MODE, generateKey());
			
			encryptCipher = Cipher.getInstance("AES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, generateKey());
			
			encrypted = true;
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			Log.error("Error reading/generating encryption key.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		stopped = true;
	}

}

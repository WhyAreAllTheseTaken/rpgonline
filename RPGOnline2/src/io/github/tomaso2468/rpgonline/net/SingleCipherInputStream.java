package io.github.tomaso2468.rpgonline.net;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

@Deprecated
public class SingleCipherInputStream extends FilterInputStream {
	private Cipher cipher;
	public SingleCipherInputStream(InputStream in, Cipher cipher) {
		super(in);
		this.cipher = cipher;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read() throws IOException {
		try {
			int size = in.read();
			byte[] data = new byte[size];
			int length = in.read(data);
			if (length != data.length) {
				throw new IOException("Invalid block length.");
			}
			return cipher.doFinal(data)[0];
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new IOException(e);
		}
	}

}

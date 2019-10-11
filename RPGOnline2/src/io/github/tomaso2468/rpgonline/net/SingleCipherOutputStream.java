package io.github.tomaso2468.rpgonline.net;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

@Deprecated
public class SingleCipherOutputStream extends FilterOutputStream {
	private Cipher cipher;
	public SingleCipherOutputStream(OutputStream out, Cipher cipher) {
		super(out);
		this.cipher = cipher;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(int b) throws IOException {
		try {
			byte[] data = cipher.doFinal(new byte[] {(byte) b});
			out.write(data.length);
			out.write(data);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new IOException(e);
		}
	}
}

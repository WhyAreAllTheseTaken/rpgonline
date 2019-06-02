package rpgonline.abt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An extension of DataInputStream to provide functions and formats needed by
 * ABT. This format is compatible with {@code ABTDataOutputStream}
 * 
 * @author Tomas
 * 
 * @see rpgonline.abt.ABTDataOutputStream
 */
public class ABTDataInputStream extends DataInputStream {
	/**
	 * Constructs a new ABTDataInputStream.
	 * 
	 * @param in The input stream to read from.
	 */
	public ABTDataInputStream(InputStream in) {
		super(in);
	}

	/**
	 * Reads the length of a string as a byte then reads that many bytes and treats
	 * them as UTF-8 data.
	 * 
	 * @return A string.
	 * @throws IOException If an error occurs reading data.
	 */
	public String readUTFByte() throws IOException {
		byte len = readByte();

		byte[] data = new byte[len];
		readFully(data);

		return new String(data);
	}

	/**
	 * Reads the length of a string as an int then reads that many bytes and treats
	 * them as UTF-8 data.
	 * 
	 * @return A string.
	 * @throws IOException If an error occurs reading data.
	 */
	public String readUTFInt() throws IOException {
		int len = readInt();

		byte[] data = new byte[len];
		readFully(data);

		return new String(data);
	}

	/**
	 * Reads the length of a string as a long then reads that many bytes and treats
	 * them as UTF-8 data.
	 * 
	 * @return A string.
	 * @throws IOException If an error occurs reading data.
	 */
	public String readUTFLong() throws IOException {
		long len = readLong();

		if (len > Integer.MAX_VALUE - 8) {
			throw new OutOfMemoryError("Requested array size exceeds VM limit");
		}

		byte[] data = new byte[(int) len];
		readFully(data);

		return new String(data);
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(short[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readShort();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(int[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readInt();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(long[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readLong();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(float[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readFloat();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(double[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readDouble();
		}
	}

	/**
	 * Reads all of the data required to fill an array. The strings are treated in
	 * the same way as {@code readUTFInt()}
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(String[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readUTFInt();
		}
	}

	/**
	 * Reads all of the data required to fill an array. The strings are treated in
	 * the same way as {@code readUTF()}
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFullyShort(String[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readUTF();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(char[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readChar();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(boolean[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readBoolean();
		}
	}
}

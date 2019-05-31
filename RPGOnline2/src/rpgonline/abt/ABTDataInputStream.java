package rpgonline.abt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ABTDataInputStream extends DataInputStream {
	public ABTDataInputStream(InputStream in) {
		super(in);
	}
	
	public String readUTFByte() throws IOException {
		byte len = readByte();
		
		byte[] data = new byte[len];
		readFully(data);
		
		return new String(data);
	}
	
	public String readUTFInt() throws IOException {
		int len = readInt();
		
		byte[] data = new byte[len];
		readFully(data);
		
		return new String(data);
	}
	
	public String readUTFLong() throws IOException {
		long len = readLong();
		
		if (len > Integer.MAX_VALUE - 8) {
			throw new OutOfMemoryError("Requested array size exceeds VM limit");
		}
		
		byte[] data = new byte[(int) len];
		readFully(data);
		
		return new String(data);
	}
	
	public void readFully(short[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readShort();
		}
	}
	
	public void readFully(int[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readInt();
		}
	}
	
	public void readFully(long[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readLong();
		}
	}
	
	public void readFully(float[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readFloat();
		}
	}
	
	public void readFully(double[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readDouble();
		}
	}
	
	public void readFully(String[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readUTFInt();
		}
	}
	
	public void readFullyShort(String[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readUTF();
		}
	}
	
	public void readFully(char[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readChar();
		}
	}
	
	public void readFully(boolean[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readBoolean();
		}
	}
}

package rpgonline.abt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

public class ABTDataOutputStream extends DataOutputStream {
	public ABTDataOutputStream(OutputStream out) {
		super(out);
	}
	
	public void writeUTFByte(String s) throws IOException {
		if(s.getBytes().length > 255) {
			throw new IOException("String \"" + s + "\" is too big to be written using byte length.");
		}
		writeByte(s.getBytes().length);
		write(s.getBytes());
	}
	
	public void writeUTFInt(String s) throws IOException {
		if(s.getBytes().length > 4294967295L) {
			throw new IOException("String \"" + s + "\" is too big to be written using int length.");
		}
		writeInt(s.getBytes().length);
		write(s.getBytes());
	}
	
	public void writeUTFLong(String s) throws IOException {
		if(new BigInteger(s.getBytes().length + "").compareTo(new BigInteger("ffffffffffffffff", 16)) > 0) {
			throw new IOException("String \"" + s + "\" is too big to be written using long length.");
		}
		writeLong(s.getBytes().length);
		write(s.getBytes());
	}
	
	public void write(short[] data) throws IOException {
		for (short s : data) {
			writeShort(s);
		}
	}
	
	public void write(int[] data) throws IOException {
		for (int s : data) {
			writeInt(s);
		}
	}
	
	public void write(long[] data) throws IOException {
		for (long s : data) {
			writeLong(s);
		}
	}
	
	public void write(float[] data) throws IOException {
		for (float s : data) {
			writeFloat(s);
		}
	}
	
	public void write(double[] data) throws IOException {
		for (double s : data) {
			writeDouble(s);
		}
	}
	
	public void write(String[] data) throws IOException {
		for (String s : data) {
			writeUTFInt(s);
		}
	}
	
	public void writeShort(String[] data) throws IOException {
		for (String s : data) {
			writeUTF(s);
		}
	}
	
	public void write(char[] data) throws IOException {
		for (char s : data) {
			writeChar(s);
		}
	}
	
	public void write(boolean[] data) throws IOException {
		for (boolean s : data) {
			writeBoolean(s);
		}
	}

}

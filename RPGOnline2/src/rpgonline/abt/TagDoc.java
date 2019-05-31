package rpgonline.abt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.util.Log;

public class TagDoc {
	public TagDoc(short version, String app, TagGroup tags) {
		super();
		this.version = version;
		this.app = app;
		this.tags = tags;
	}
	
	private short version;
	private String app;
	private TagGroup tags;
	
	public short getVersion() {
		return version;
	}
	public void setVersion(short version) {
		this.version = version;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public TagGroup getTags() {
		return tags;
	}
	public void setTags(TagGroup tags) {
		this.tags = tags;
	}
	
	public static TagDoc read(InputStream in, String app) throws IOException {
		ABTDataInputStream dis = new ABTDataInputStream(new BufferedInputStream(in));
		
		char c1 = (char) dis.readByte();
		char c2 = (char) dis.readByte();
		char c3 = (char) dis.readByte();
		char c4 = (char) dis.readByte();
		
		if (!(c1 == 'A' && c2 == 'B' && c3 == 'T' && c4 == '1')) {
			dis.close();
			
			throw new InvalidHeaderException("" + c1 + c2 + c3 + c4, "abt");
		}
		
		short version = dis.readShort();
		
		if (version == 0) {
			String app2 = dis.readUTFByte();
			
			if(app != null) {
				if (!app2.equals(app)) {
					dis.close();
					throw new IOException("Cannot read file from another game.");
				}
			}
			
			byte tagID = dis.readByte();
			
			if (tagID == 0x01) {
				TagGroup g = readTagGroup(dis);
				
				dis.close();
				
				return new TagDoc(version, app, g);
			} else {
				dis.close();
				throw new IOException("Malformed intial tag group with ID: " + tagID);
			}
		} else {
			dis.close();
			throw new IOException("Unknown or unsupported ABT version: " + version);
		}
	}
	
	public static TagGroup readTagGroup(ABTDataInputStream in) throws IOException {
		String name = in.readUTF();
		
		// Binary length. This is ignored when parsing the whole tree.
		in.readLong();
		
		// Item count. Not needed by this parser.
		in.readLong();
		
		TagGroup g = new TagGroup(name);
		
		while (true) {
			byte id = in.readByte();
			
			if(id == 0x0) {
				break;
			}
			
			g.add(readTag(id, in));
		}
		
		return g;
	}
	public static Tag readTag(byte id, ABTDataInputStream in) throws IOException {
		byte[] buffer;
		
		switch(id) {
		case 0x00:
			Log.warn("Unhandled case of end tag.");
			return null;
		case 0x01:
			return readTagGroup(in);
		case 0x02:
			return new TagByte(in.readUTF(), in.readByte());
		case 0x03:
			return new TagShort(in.readUTF(), in.readShort());
		case 0x04:
			return new TagInt(in.readUTF(), in.readInt());
		case 0x05:
			return new TagLong(in.readUTF(), in.readLong());
		case 0x06:
			buffer = new byte[16];
			in.readFully(buffer);
			return new Tag128(in.readUTF(), buffer);
		case 0x07:
			buffer = new byte[in.readInt()];
			in.readFully(buffer);
			return new TagBigInt(in.readUTF(), buffer);
		case 0x08:
			return new TagFloat(in.readUTF(), in.readFloat());
		case 0x09:
			return new TagDouble(in.readUTF(), in.readDouble());
		case 0x0A:
			return new TagBigFloat(in.readUTF(), in.readUTFInt());
		case 0x0B:
			return new TagStringShort(in.readUTF(), in.readUTF());
		case 0x0C:
			return new TagString(in.readUTF(), in.readUTFLong());
		case 0x0D:
			buffer = new byte[in.readInt()];
			in.readFully(buffer);
			return new TagByteArray(in.readUTF(), buffer);
		case 0x0E:
			short[] buffer_s = new short[in.readInt()];
			in.readFully(buffer_s);
			return new TagShortArray(in.readUTF(), buffer_s);
		case 0x0F:
			int[] buffer_i = new int[in.readInt()];
			in.readFully(buffer_i);
			return new TagIntArray(in.readUTF(), buffer_i);
		case 0x10:
			long[] buffer_l = new long[in.readInt()];
			in.readFully(buffer_l);
			return new TagLongArray(in.readUTF(), buffer_l);
		case 0x11:
			byte[][] buffer128 = new byte[in.readInt()][16];
			
			for (int x = 0; x < buffer128.length; x++) {
				for (int y = 0; y < buffer128[x].length; y++) {
					buffer128[x][y] = in.readByte();
				}
			}
			
			return new Tag128Array(in.readUTF(), buffer128);
		case 0x12:
			byte[][] buffer_big = new byte[in.readInt()][];
			
			for (int x = 0; x < buffer_big.length; x++) {
				buffer_big[x] = new byte[in.readInt()];
				for (int y = 0; y < buffer_big[x].length; y++) {
					buffer_big[x][y] = in.readByte();
				}
			}
			
			return new TagBigIntArray(in.readUTF(), buffer_big);
		case 0x13:
			float[] buffer_f = new float[in.readInt()];
			in.readFully(buffer_f);
			return new TagFloatArray(in.readUTF(), buffer_f);
		case 0x14:
			double[] buffer_d = new double[in.readInt()];
			in.readFully(buffer_d);
			return new TagDoubleArray(in.readUTF(), buffer_d);
		case 0x15:
			String[] buffer_bf = new String[in.readInt()];
			in.readFully(buffer_bf);
			return new TagBigFloatArray(in.readUTF(), buffer_bf);
		case 0x16:
			String[] buffer_str = new String[in.readInt()];
			in.readFullyShort(buffer_str);
			return new TagStringShortArray(in.readUTF(), buffer_str);
		case 0x17:
			String[] buffer_str2 = new String[in.readInt()];
			in.readFully(buffer_str2);
			return new TagStringArray(in.readUTF(), buffer_str2);
		case 0x18:
			return new TagChar(in.readUTF(), in.readChar());
		case 0x19:
			char[] buffer_char = new char[in.readInt()];
			in.readFully(buffer_char);
			return new TagCharArray(in.readUTF(), buffer_char);
		case 0x20:
			return new TagBoolean(in.readUTF(), in.readBoolean());
		case 0x21:
			boolean[] buffer_bool = new boolean[in.readInt()];
			in.readFully(buffer_bool);
			return new TagBooleanArray(in.readUTF(), buffer_bool);
		default:
			throw new IOException("Invalid tag ID: " + id);
		}
	}
}

package io.github.tomaso2468.rpgonline.abt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.debug.Debugger;

/**
 * A representation of an ABT file.
 * @author Tomas
 */
public class TagDoc {
	/**
	 * The most recent ABT version.
	 */
	public static final short LATEST_VERSION = 1;
	/**
	 * Construct a new tag doc.
	 * @param version The version format of the tag doc.
	 * @param app The app name of the tag doc.
	 * @param tags The root tag group used by this tag doc.
	 */
	public TagDoc(short version, String app, TagGroup tags) {
		super();
		this.version = version;
		this.app = app;
		this.tags = tags;
	}
	
	/**
	 * Construct a new tag doc.
	 * @param app The app name of the tag doc.
	 * @param tags The root tag group used by this tag doc.
	 */
	public TagDoc(String app, TagGroup tags) {
		this(LATEST_VERSION, app, tags);
	}
	
	/**
	 * The ABT version.
	 */
	private short version;
	/**
	 * The app format.
	 */
	private String app;
	/**
	 * The root tag group.
	 */
	private TagGroup tags;
	
	/**
	 * Gets the ABT version of this document.
	 * @return A short value.
	 */
	public short getVersion() {
		return version;
	}
	/**
	 * Sets the ABT version of this document.
	 * @param version A supported version value.
	 */
	public void setVersion(short version) {
		if (!(version >= 0 && version <= TagDoc.LATEST_VERSION)) {
			throw new IllegalArgumentException("Unsupported tag version: " + version);
		}
		this.version = version;
	}
	/**
	 * Gets the app ID for this document.
	 * @return A string.
	 */
	public String getApp() {
		return app;
	}
	/**
	 * Sets the app ID for this document.
	 * @return A string of 255 characters or less.
	 */
	public void setApp(String app) {
		if (app.length() > 255) {
			throw new IllegalArgumentException("app ID must be less than or equal to 255 characters.");
		}
		this.app = app;
	}
	/**
	 * Gets the root tag of this document.
	 * @return A tag group.
	 */
	public TagGroup getTags() {
		return tags;
	}
	/**
	 * Sets the root tag of this document.
	 * @param tags A tag group.
	 */
	public void setTags(TagGroup tags) {
		this.tags = tags;
	}
	
	/**
	 * Reads a tag document from an ABT file.
	 * @param in An input stream.
	 * @param app The app ID to check against or {@code null} to disable checking.
	 * @return A tag doc object.
	 * @throws IOException If an error occurs loading data.
	 */
	public static TagDoc read(InputStream in, String app) throws IOException {
		Debugger.start("abt-decode");
		try {
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
			
			if (version >= 0 || version <= 1) {
				String app2 = dis.readUTFByte();
				
				if(app != null) {
					if (!app2.equals(app)) {
						dis.close();
						throw new IOException("Cannot read file from another game.");
					}
				}
				
				byte tagID = dis.readByte();
				
				if (tagID == 0x01) {
					String name = dis.readUTF();
					
					TagGroup g = readTagGroup(name, dis);
					
					dis.close();
					
					Debugger.stop("abt-decode");
					return new TagDoc(version, app, g);
				} else {
					dis.close();
					throw new IOException("Malformed intial tag group with ID: " + tagID);
				}
			} else {
				dis.close();
				throw new IOException("Unknown or unsupported ABT version: " + version);
			}
		} catch (IOException e) {
			Debugger.stop("abt-decode");
			throw e;
		}
	}
	
	/**
	 * Writes this document to a stream.
	 * @param out The stream to write to.
	 * @throws IOException If an error occurs writing data.
	 */
	public void write(OutputStream out) throws IOException {
		Debugger.start("abt-encode");
		try {
			ABTDataOutputStream dos = new ABTDataOutputStream(new BufferedOutputStream(out));
			
			dos.write(new byte[] {'A', 'B', 'T', '1'});
			
			dos.writeShort(LATEST_VERSION);
			
			dos.writeUTFByte(app);
			
			writeTag(dos, tags);
			
			dos.flush();
			dos.close();
		} catch (IOException e) {
			Debugger.stop("abt-encode");
			throw e;
		}
		Debugger.stop("abt-encode");
	}
	
	/**
	 * Writes a tag.
	 * @param out The output stream to write to.
	 * @param t The tag to write.
	 * @throws IOException If an error occurs writing data.
	 */
	@SuppressWarnings("deprecation")
	public void writeTag(ABTDataOutputStream out, Tag t) throws IOException {
		out.writeByte(t.getType());
		
		if(t.getType() != 0x0) {
			out.writeUTF(t.getName());
		}
		
		switch(t.getType()) {
		case 0x0:
			break;
		case 0x1:
			out.writeLong(((TagGroup) t).size());
			
			for(Tag t2 : ((TagGroup) t).getTags()) {
				writeTag(out, t2);
			}
			
			writeTag(out, new Tag("", 0x0) {

				/**
				 * 
				 */
				private static final long serialVersionUID = -7308059970539366716L;}); // End tag
			break;
		case 0x2:
			out.writeByte(((TagByte) t).getData());
			break;
		case 0x3:
			out.writeShort(((TagShort) t).getData());
			break;
		case 0x4:
			out.writeInt(((TagInt) t).getData());
			break;
		case 0x5:
			out.writeLong(((TagLong) t).getData());
			break;
		case 0x6:
			out.write(((Tag128) t).getData());
			break;
		case 0x7:
			out.writeInt(((TagBigInt) t).getData().length);
			out.write(((TagBigInt) t).getData());
			break;
		case 0x8:
			out.writeFloat(((TagFloat) t).getData());
			break;
		case 0x9:
			out.writeDouble(((TagDouble) t).getData());
			break;
		case 0xA:
			out.writeUTFInt(((TagBigFloat) t).getData());
			break;
		case 0xB:
			out.writeUTF(((TagStringShort) t).getData());
			break;
		case 0xC:
			out.writeUTFLong(((TagString) t).getData());
			break;
		case 0xD:
			out.writeInt(((TagByteArray) t).getData().length);
			out.write(((TagByteArray) t).getData());
			break;
		case 0xE:
			out.writeInt(((TagShortArray) t).getData().length);
			out.write(((TagShortArray) t).getData());
			break;
		case 0xF:
			out.writeInt(((TagIntArray) t).getData().length);
			out.write(((TagIntArray) t).getData());
			break;
		case 0x10:
			out.writeInt(((TagLongArray) t).getData().length);
			out.write(((TagLongArray) t).getData());
			break;
		case 0x11:
			out.writeInt(((Tag128Array) t).getData().length);
			
			byte[][] buffer128 = (((Tag128Array) t).getData());
			
			for (int x = 0; x < buffer128.length; x++) {
				for (int y = 0; y < buffer128[x].length; y++) {
					out.writeByte(buffer128[x][y]);
				}
			}
			
			break;
		case 0x12:
			out.writeInt(((TagBigIntArray) t).getData().length);
			
			byte[][] bufferBigInt = (((TagBigIntArray) t).getData());
			
			for (int x = 0; x < bufferBigInt.length; x++) {
				out.writeInt(bufferBigInt[x].length);
				for (int y = 0; y < bufferBigInt[x].length; y++) {
					out.writeByte(bufferBigInt[x][y]);
				}
			}
			
			break;
		case 0x13:
			out.writeInt(((TagFloatArray) t).getData().length);
			out.write(((TagFloatArray) t).getData());
			break;
		case 0x14:
			out.writeInt(((TagDoubleArray) t).getData().length);
			out.write(((TagDoubleArray) t).getData());
			break;
		case 0x15:
			out.writeInt(((TagBigFloatArray) t).getData().length);
			out.write(((TagBigFloatArray) t).getData());
			break;
		case 0x16:
			out.writeInt(((TagStringShortArray) t).getData().length);
			out.writeShort(((TagStringShortArray) t).getData());
			break;
		case 0x17:
			out.writeInt(((TagStringArray) t).getData().length);
			out.write(((TagStringArray) t).getData());
			break;
		case 0x18:
			out.writeChar(((TagChar) t).getData());
			break;
		case 0x19:
			out.writeInt(((TagCharArray) t).getData().length);
			out.write(((TagCharArray) t).getData());
			break;
		case 0x1A:
			out.writeDouble(((TagVector2) t).get(0));
			out.writeDouble(((TagVector2) t).get(1));
			break;
		case 0x1B:
			out.writeDouble(((TagVector3) t).get(0));
			out.writeDouble(((TagVector3) t).get(1));
			out.writeDouble(((TagVector3) t).get(2));
			break;
		case 0x1C:
			out.writeDouble(((TagVector4) t).get(0));
			out.writeDouble(((TagVector4) t).get(1));
			out.writeDouble(((TagVector4) t).get(2));
			out.writeDouble(((TagVector4) t).get(3));
			break;
		case 0x1D:
			double[][] buffer_m2 = ((TagMatrix2) t).getData();
			
			for (int x = 0; x < buffer_m2.length; x++) {
				for (int y = 0; y < buffer_m2[x].length; y++) {
					out.writeDouble(buffer_m2[x][y]);
				}
			}
			break;
		case 0x1E:
			double[][] buffer_m3 = ((TagMatrix3) t).getData();
			
			for (int x = 0; x < buffer_m3.length; x++) {
				for (int y = 0; y < buffer_m3[x].length; y++) {
					out.writeDouble(buffer_m3[x][y]);
				}
			}
			break;
		case 0x1F:
			double[][] buffer_m4 = ((TagMatrix3) t).getData();
			
			for (int x = 0; x < buffer_m4.length; x++) {
				for (int y = 0; y < buffer_m4[x].length; y++) {
					out.writeDouble(buffer_m4[x][y]);
				}
			}
			break;
		case 0x20:
			out.writeBoolean(((TagBoolean) t).getData());
			break;
		case 0x21:
			out.write(((TagBooleanArray) t).getData());
			break;
		default:
			throw new IOException("Unknown tag type: " + t.getType());
		}
	}
	
	/**
	 * Reads a tag group from a stream.
	 * @param name The name of the tag group.
	 * @param in The input stream to read from.
	 * @return A tag group.
	 * @throws IOException If an error occurs reading data.
	 */
	public static TagGroup readTagGroup(String name, ABTDataInputStream in) throws IOException {
		// Item count. Not needed by this parser.
		in.readLong();
		
		TagGroup g = new TagGroup(name);
		
		while (true) {
			byte id = in.readByte();
			
			if (id == 0x0) {
				break;
			}
			
			g.add(readTag(id, in));
		}
		
		return g;
	}
	/**
	 * Reads a tag from a stream.
	 * @param id The id of the tag.
	 * @param in The input stream to read from.
	 * @return A tag/
	 * @throws IOException If an error occurs reading data.
	 */
	@SuppressWarnings("deprecation")
	public static Tag readTag(byte id, ABTDataInputStream in) throws IOException {
		byte[] buffer;
		
		String name = "";
		
		if(id != 0x0) {
			name = in.readUTF();
		}
		
		switch(id) {
		case 0x00:
			Log.warn("Unhandled case of end tag.");
			return null;
		case 0x01:
			return readTagGroup(name, in);
		case 0x02:
			return new TagByte(name, in.readByte());
		case 0x03:
			return new TagShort(name, in.readShort());
		case 0x04:
			return new TagInt(name, in.readInt());
		case 0x05:
			return new TagLong(name, in.readLong());
		case 0x06:
			buffer = new byte[16];
			in.readFully(buffer);
			return new Tag128(name, buffer);
		case 0x07:
			buffer = new byte[in.readInt()];
			in.readFully(buffer);
			return new TagBigInt(name, buffer);
		case 0x08:
			return new TagFloat(name, in.readFloat());
		case 0x09:
			return new TagDouble(name, in.readDouble());
		case 0x0A:
			return new TagBigFloat(name, in.readUTFInt());
		case 0x0B:
			return new TagStringShort(name, in.readUTF());
		case 0x0C:
			return new TagString(name, in.readUTFLong());
		case 0x0D:
			buffer = new byte[in.readInt()];
			in.readFully(buffer);
			return new TagByteArray(name, buffer);
		case 0x0E:
			short[] buffer_s = new short[in.readInt()];
			in.readFully(buffer_s);
			return new TagShortArray(name, buffer_s);
		case 0x0F:
			int[] buffer_i = new int[in.readInt()];
			in.readFully(buffer_i);
			return new TagIntArray(name, buffer_i);
		case 0x10:
			long[] buffer_l = new long[in.readInt()];
			in.readFully(buffer_l);
			return new TagLongArray(name, buffer_l);
		case 0x11:
			byte[][] buffer128 = new byte[in.readInt()][16];
			
			for (int x = 0; x < buffer128.length; x++) {
				for (int y = 0; y < buffer128[x].length; y++) {
					buffer128[x][y] = in.readByte();
				}
			}
			
			return new Tag128Array(name, buffer128);
		case 0x12:
			byte[][] buffer_big = new byte[in.readInt()][];
			
			for (int x = 0; x < buffer_big.length; x++) {
				buffer_big[x] = new byte[in.readInt()];
				for (int y = 0; y < buffer_big[x].length; y++) {
					buffer_big[x][y] = in.readByte();
				}
			}
			
			return new TagBigIntArray(name, buffer_big);
		case 0x13:
			float[] buffer_f = new float[in.readInt()];
			in.readFully(buffer_f);
			return new TagFloatArray(name, buffer_f);
		case 0x14:
			double[] buffer_d = new double[in.readInt()];
			in.readFully(buffer_d);
			return new TagDoubleArray(name, buffer_d);
		case 0x15:
			String[] buffer_bf = new String[in.readInt()];
			in.readFully(buffer_bf);
			return new TagBigFloatArray(name, buffer_bf);
		case 0x16:
			String[] buffer_str = new String[in.readInt()];
			in.readFullyShort(buffer_str);
			return new TagStringShortArray(name, buffer_str);
		case 0x17:
			String[] buffer_str2 = new String[in.readInt()];
			in.readFully(buffer_str2);
			return new TagStringArray(name, buffer_str2);
		case 0x18:
			return new TagChar(name, in.readChar());
		case 0x19:
			char[] buffer_char = new char[in.readInt()];
			in.readFully(buffer_char);
			return new TagCharArray(name, buffer_char);
		case 0x20:
			return new TagBoolean(name, in.readBoolean());
		case 0x21:
			boolean[] buffer_bool = new boolean[in.readInt()];
			in.readFully(buffer_bool);
			return new TagBooleanArray(name, buffer_bool);
		case 0x1A:
			return new TagVector2(name, in.readDouble(), in.readDouble());
		case 0x1B:
			return new TagVector3(name, in.readDouble(), in.readDouble(), in.readDouble());
		case 0x1C:
			return new TagVector4(name, in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble());
		case 0x1D:
			double[][] buffer_m2 = new double[4][4];
			
			for (int x = 0; x < buffer_m2.length; x++) {
				for (int y = 0; y < buffer_m2[x].length; y++) {
					buffer_m2[x][y] = in.readDouble();
				}
			}
			
			return new TagMatrix2(name, buffer_m2);
		case 0x1E:
			double[][] buffer_m3 = new double[3][3];
			
			for (int x = 0; x < buffer_m3.length; x++) {
				for (int y = 0; y < buffer_m3[x].length; y++) {
					buffer_m3[x][y] = in.readDouble();
				}
			}
			
			return new TagMatrix3(name, buffer_m3);
		case 0x1F:
			double[][] buffer_m4 = new double[3][3];
			
			for (int x = 0; x < buffer_m4.length; x++) {
				for (int y = 0; y < buffer_m4[x].length; y++) {
					buffer_m4[x][y] = in.readDouble();
				}
			}
			
			return new TagMatrix4(name, buffer_m4);
		default:
			throw new IOException("Invalid tag ID: " + id);
		}
	}
}

package rpgonline.net.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.newdawn.slick.util.Log;

import rpgonline.FolderHelper;

public class LocalDiskUserServer implements UserServer {
	public static final int TOKEN_LENGTH = 512;
	public static final String INVALID_TOKEN = generateInvalidToken(TOKEN_LENGTH);
	
	private final String appID;

	public LocalDiskUserServer(String appID) {
		super();
		this.appID = appID;
	}

	@Override
	public String getLoginToken(String login, String password) {
		if (isValidLogin(login, password)) {
			Database d = getDatabase();
			
			String token = generateToken(TOKEN_LENGTH);
			
			d.tokens.put(token, getUserID(login));
			
			try {
				saveDatabase(d);
			} catch (IOException e) {
				Log.error("Error saving login data", e);
			}
			
			return token;
		}
		
		return INVALID_TOKEN;
	}
	
	private boolean isValidLogin(String login, String password) {
		Database d = getDatabase();
		
		for (User u : d.users) {
			if (u.getLogin().equalsIgnoreCase(login)) {
				return u.verify(login, password);
			}
		}
		
		return false;
	}

	@Override
	public boolean isValidToken(String token) {
		Database d = getDatabase();
		
		for (Entry<String, Long> t : d.tokens.entrySet()) {
			if(t.getKey().equals(token)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getConnectToken(String token) {
		if (isValidToken(token)) {
			Database d = getDatabase();
			
			String token2 = generateToken(TOKEN_LENGTH);
			
			d.tokens2.put(token, getUserIDToken(token));
			
			try {
				saveDatabase(d);
			} catch (IOException e) {
				Log.error("Error saving login data", e);
			}
			
			return token2;
		}
		return INVALID_TOKEN;
	}

	@Override
	public boolean isValidConnectToken(String token) {
		Database d = getDatabase();
		
		for (Entry<String, Long> t : d.tokens2.entrySet()) {
			if(t.getKey().equals(token)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void clearConnectToken(String token) {
		Database d = getDatabase();
		
		d.tokens2.remove(token);
		
		try {
			saveDatabase(d);
		} catch (IOException e) {
			Log.error("Error saving login data", e);
		}
	}

	@Override
	public long getUserID(String login) {
		Database d = getDatabase();
		
		for (User u : d.users) {
			if (u.getLogin().equalsIgnoreCase(login)) {
				return u.id;
			}
		}
		
		return -1;
	}

	@Override
	public String getUsername(long id) {
		Database d = getDatabase();
		
		for (User u : d.users) {
			if (u.id == id) {
				return u.getUsername();
			}
		}
		
		return Long.toHexString(id);
	}
	
	@Override
	public long getUserIDToken(String token) {
		Database d = getDatabase();
		
		Long l = d.tokens.get(token);
		
		if (l == null) {
			return -1;
		}
		
		return l;
	}
	
	@Override
	public long getUserIDToken2(String token) {
		Database d = getDatabase();
		
		Long l = d.tokens2.get(token);
		
		if (l == null) {
			return -1;
		}
		
		return l;
	}
	
	@Override
	public long getUserIDUsername(String username) {
		Database d = getDatabase();
		
		for (User u : d.users) {
			if (u.getUsername().equals(username)) {
				return u.id;
			}
		}
		
		return -1;
	}
	
	private Database getDatabase() {
		try {
			return new Database(FolderHelper.createAppDataFolder("rpgonline", "userverify", appID, "database"));
		} catch (IOException e) {
			Log.warn("No database could be found creating a new database.", e);
			return new Database();
		}
	}
	
	public void saveDatabase(Database d) throws IOException {
		d.save(FolderHelper.createAppDataFolder("rpgonline", "userverify", appID, "database"));
	}
	
	private static String generateToken(int length) {
		SecureRandom r = new SecureRandom();
		
		byte[] data = r.generateSeed(length);
		
		StringBuilder sb = new StringBuilder();
		
		for (byte b : data) {
			sb.append(Integer.toString(b, 16));
		}
		
		String token = sb.toString();
		
		if (token.equals(INVALID_TOKEN)) {
			return generateToken(length);
		}
		
		return token;
	}
	
	private static String generateInvalidToken(int length) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			sb.append("00");
		}
		
		return sb.toString();
	}

	private class Database {
		private Map<String, Long> tokens = new HashMap<>();
		private Map<String, Long> tokens2 = new HashMap<>();
		private List<User> users = new ArrayList<>();
		
		Database(File folder) throws IOException {
			readTokens(new DataInputStream(new FileInputStream(new File(folder, "tokens.dat"))));
			readConnectTokens(new DataInputStream(new FileInputStream(new File(folder, "nettokens.dat"))));
			readUsers(new DataInputStream(new FileInputStream(new File(folder, "users.dat"))));
		}
		
		Database() {
			
		}
		
		protected void readTokens(DataInputStream in) throws IOException {
			while (in.available() > 0) {
				tokens.put(in.readUTF(), in.readLong());
			}
			in.close();
		}
		
		protected void readConnectTokens(DataInputStream in) throws IOException {
			while (in.available() > 0) {
				tokens2.put(in.readUTF(), in.readLong());
			}
			in.close();
		}
		
		protected void readUsers(DataInputStream in) throws IOException {
			while (in.available() > 0) {
				String login = in.readUTF();
				
				String hash = in.readUTF();
				
				String username = in.readUTF();
				
				byte[] salt = new byte[in.readInt()];
				in.readFully(salt);
				
				long id = in.readLong();
				
				users.add(new User(login, hash, username, salt, id));
			}
			in.close();
		}
		
		protected void writeTokens(DataOutputStream out) throws IOException {
			for (Entry<String, Long> t : tokens.entrySet()) {
				out.writeUTF(t.getKey());
				out.writeLong(t.getValue());
			}
			out.flush();
			out.close();
		}
		
		protected void writeConnectTokens(DataOutputStream out) throws IOException {
			for (Entry<String, Long> t : tokens2.entrySet()) {
				out.writeUTF(t.getKey());
				out.writeLong(t.getValue());
			}
			out.flush();
			out.close();
		}
		
		protected void writeUsers(DataOutputStream out) throws IOException {
			for (User u : users) {
				out.writeUTF(u.login);
				
				out.writeUTF(u.hash);
				
				out.writeUTF(u.username);
				
				out.writeInt(u.salt.length);
				out.write(u.salt);
				
				out.writeLong(u.id);
			}
			out.flush();
			out.close();
		}
		
		void save(File folder) throws IOException {
			Log.info("Reading from " + folder.getAbsolutePath());
			File tokens = new File(folder, "tokens.dat");
			tokens.createNewFile();
			File nettokens = new File(folder, "nettokens.dat");
			nettokens.createNewFile();
			File users = new File(folder, "users.dat");
			users.createNewFile();
			
			writeTokens(new DataOutputStream(new FileOutputStream(tokens)));
			writeConnectTokens(new DataOutputStream(new FileOutputStream(nettokens)));
			writeUsers(new DataOutputStream(new FileOutputStream(users)));
		}
	}

	public static class User {
		private String login;
		private String hash;
		private String username;
		private byte[] salt;
		private final long id;

		public User(String login, String password, long id, String username) {
			super();
			this.login = login;
			setPassword(password);
			this.id = id;
			this.username = username;
		}

		public User(String login, String hash, String username, byte[] salt, long id) {
			super();
			this.login = login;
			this.hash = hash;
			this.username = username;
			this.salt = salt;
			this.id = id;
		}

		private static byte[] getSalt() throws NoSuchAlgorithmException {
			// Always use a SecureRandom generator
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			// Create array for salt
			byte[] salt = new byte[16];
			// Get a random salt
			sr.nextBytes(salt);
			// return salt
			return salt;
		}

		private static String get_SHA_512_SecurePassword(String passwordToHash, byte[] salt) {
			String generatedPassword = null;
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-512");
				md.update(salt);
				byte[] bytes = md.digest(passwordToHash.getBytes());
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < bytes.length; i++) {
					sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}
				generatedPassword = sb.toString();
			} catch (NoSuchAlgorithmException e) {
				Log.error(e);
			}
			return generatedPassword;
		}
		
		public void setPassword(String password) {
			try {
				this.salt = getSalt();
			} catch (NoSuchAlgorithmException e) {
				Log.error(e);
				Log.error("Force exiting for security reasons.");
				System.exit(-1);
				this.salt = new byte[0];
			}
			this.hash = get_SHA_512_SecurePassword(password, salt);
		}
		
		public boolean verify(String login, String password) {
			if (!login.equalsIgnoreCase(login)) {
				return false;
			}
			return get_SHA_512_SecurePassword(password, salt).equals(hash);
		}
		
		public long getID() {
			return id;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.print("App ID: ");

		String id = s.nextLine();
		
		LocalDiskUserServer server = new LocalDiskUserServer(id);
		
		while(true) {
			System.out.print("> ");
			
			String cmd = s.nextLine();
			
			switch(cmd.toLowerCase()) {
			case "stop":
				System.out.println("Exiting...");
				return;
			case "add":
				System.out.println();
				System.out.println(" --- Add User --- ");
				add(s, server);
				break;
			case "edit":
				System.out.println();
				System.out.println(" --- Edit User --- ");
				change(s, server);
				break;
			case "remove":
				System.out.println();
				System.out.println(" --- Remove User --- ");
				remove(s, server);
				break;
			case "help":
				System.out.println(" --- Help --- ");
				System.out.println("add\t-\tAdds a new user.");
				System.out.println("edit\t-\tChanges a user's information.");
				System.out.println("help\t-\tDisplays help information.");
				System.out.println("remove\t-\tRemoves a user..");
				System.out.println("stop\t-\tCloses the program.");
				break;
			case "list":
				System.out.println(" --- List --- ");
				Database d = server.getDatabase();
				
				for(User u : d.users) {
					System.out.println(String.format("%30s\t%30s", u.getUsername(), u.getLogin()));
				}
				break;
			case "listhash":
				System.out.println(" --- List --- ");
				Database d2 = server.getDatabase();
				
				for(User u : d2.users) {
					System.out.println(String.format("%30s\t%30s", u.getUsername(), u.getLogin()));
				}
				break;
			default:
				System.out.println("Unknown Command: " + cmd);
				break;
			}
			System.out.println();
		}
	}
	
	private static void add(Scanner s, LocalDiskUserServer server) {
		System.out.print("Login: ");
		String login = s.nextLine();
		
		System.out.print("Username: ");
		String username = s.nextLine();
		
		System.out.print("Password: ");
		String password = s.nextLine();
		
		long id = getNextID(server);
		
		Database d = server.getDatabase();
		
		d.users.add(new User(login, password, id, username));
		
		try {
			server.saveDatabase(d);
		} catch (IOException e) {
			Log.error("Error saving database", e);
		}
	}
	
	private static void change(Scanner s, LocalDiskUserServer server) {
		System.out.print("Username: ");
		String username = s.nextLine();
		
		System.out.print("Login: ");
		String login = s.nextLine();
		
		System.out.print("Password: ");
		String password = s.nextLine();
		
		Database d = server.getDatabase();
		
		for(User u : d.users) {
			if(u.getUsername().equals(username)) {
				u.setLogin(login);
				u.setPassword(password);
			}
		}
		
		try {
			server.saveDatabase(d);
		} catch (IOException e) {
			Log.error("Error saving database", e);
		}
	}
	
	private static void remove(Scanner s, LocalDiskUserServer server) {
		System.out.print("Username: ");
		String username = s.nextLine();
		
		Database d = server.getDatabase();
		
		for(User u : d.users) {
			if(u.getUsername().equals(username)) {
				d.users.remove(u);
			}
		}
		
		try {
			server.saveDatabase(d);
		} catch (IOException e) {
			Log.error("Error saving database", e);
		}
	}
	
	private static long getNextID(LocalDiskUserServer server) {
		Database d = server.getDatabase();
		
		SecureRandom r = new SecureRandom();
		
		long id = r.nextLong();
		
		if (id == -1) {
			return getNextID(server);
		}
		
		for (User u : d.users) {
			if(u.id == id) {
				return getNextID(server);
			}
		}
		
		return id;
	}

	@Override
	public boolean isUp() {
		return true;
	}
}

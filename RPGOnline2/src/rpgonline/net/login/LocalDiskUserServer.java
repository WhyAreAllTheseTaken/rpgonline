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

/**
 * A user server stored in appdata on the local disk.
 * @author Tomas
 */
public class LocalDiskUserServer implements UserServer {
	/**
	 * The length of a token in bytes.
	 */
	public static final int TOKEN_LENGTH = 64 / 8;
	/**
	 * A token what is invalid.
	 * 
	 * @deprecated use rpgonline.net.login.UserServer.INVALID_TOKEN
	 * 
	 * @see rpgonline.net.login.UserServer#INVALID_TOKEN
	 */
	@Deprecated
	public static final String INVALID_TOKEN = UserServer.INVALID_TOKEN;
	
	/**
	 * ID of the game.
	 */
	private final String appID;

	/**
	 * Constructs a new {@code LocalDiskUserServer}.
	 * @param appID The ID name of the app. (a package name can be used).
	 */
	public LocalDiskUserServer(String appID) {
		super();
		this.appID = appID;
	}

	/**
	 * {@inheritDoc}
	 */
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
		
		return UserServer.INVALID_TOKEN;
	}
	
	/**
	 * {@inheritDoc}
	 */
	private boolean isValidLogin(String login, String password) {
		Database d = getDatabase();
		
		for (User u : d.users) {
			if (u.getLogin().equalsIgnoreCase(login)) {
				return u.verify(login, password);
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getConnectToken(String token) {
		if (isValidToken(token)) {
			Database d = getDatabase();
			
			String token2 = generateToken(TOKEN_LENGTH);
			
			d.tokens2.put(token2, getUserIDToken(token));
			
			try {
				saveDatabase(d);
			} catch (IOException e) {
				Log.error("Error saving login data", e);
			}
			
			return token2;
		}
		return UserServer.INVALID_TOKEN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidConnectToken(String token) {
		Database d = getDatabase();
		
		for (Entry<String, Long> t : d.tokens2.entrySet()) {
			if(t.getKey().equals(token)) {
				return true;
			}
		}
		
		System.err.println(token + " is invalid");
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserIDToken(String token) {
		Database d = getDatabase();
		
		Long l = d.tokens.get(token);
		
		if (l == null) {
			return -1;
		}
		
		return l;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserIDToken2(String token) {
		Database d = getDatabase();
		
		Long l = d.tokens2.get(token);
		
		if (l == null) {
			return -1;
		}
		
		return l;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * Gets the database stored in a file for this.
	 * @return A non-null database object.
	 */
	private Database getDatabase() {
		try {
			return new Database(FolderHelper.createAppDataFolder("rpgonline", "userverify", appID, "database"));
		} catch (IOException e) {
			Log.warn("No database could be found creating a new database.", e);
			return new Database();
		}
	}
	
	/**
	 * Saves the database to a file.
	 * @param d The database to save.
	 * @throws IOException If an error occurs saving data.
	 */
	private void saveDatabase(Database d) throws IOException {
		d.save(FolderHelper.createAppDataFolder("rpgonline", "userverify", appID, "database"));
	}
	
	/**
	 * Generates a token.
	 * @param length The length of the token in bytes (in multiples of 4).
	 * @return A hex string of the token.
	 */
	private static String generateToken(int length) {
		SecureRandom r = new SecureRandom();
		
		int[] data = new int[(int) Math.floor(TOKEN_LENGTH / (float) Integer.BYTES)];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = r.nextInt();
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i : data) {
			sb.append(Integer.toHexString(i));
		}
		
		String token = sb.toString();
		
		if (token.equals(INVALID_TOKEN)) {
			return generateToken(length);
		}
		
		return token;
	}
	
	/**
	 * Generates a token with a value of -1 in 2s compliment.
	 * @param length The length of the token in bytes (in multiples of 4).
	 * @return A hex string of the token.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static String generateInvalidToken(int length) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < (int) Math.floor(TOKEN_LENGTH / (float) Integer.BYTES); i++) {
			sb.append(Integer.toHexString(-1));
		}
		
		return sb.toString();
	}

	/**
	 * A class storing local user data.
	 * @author Tomas
	 */
	private static class Database {
		/**
		 * A map of tokens.
		 */
		private Map<String, Long> tokens = new HashMap<>();
		/**
		 * A map of connect tokens.
		 */
		private Map<String, Long> tokens2 = new HashMap<>();
		/**
		 * A map of users.
		 */
		private List<User> users = new ArrayList<>();
		
		/**
		 * Loads a database from a folder.
		 * @param folder The folder containing the database.
		 * @throws IOException If an error occurs loading the database/
		 */
		private Database(File folder) throws IOException {
			readTokens(new DataInputStream(new FileInputStream(new File(folder, "tokens.dat"))));
			readConnectTokens(new DataInputStream(new FileInputStream(new File(folder, "nettokens.dat"))));
			readUsers(new DataInputStream(new FileInputStream(new File(folder, "users.dat"))));
		}
		
		/**
		 * Constructs an empty database.
		 */
		private Database() {
			
		}
		
		/**
		 * Reads all tokens from binary data.
		 * @param in The source of the data.
		 * @throws IOException If an error occurs reading data.
		 * @see #writeTokens(DataOutputStream)
		 */
		protected void readTokens(DataInputStream in) throws IOException {
			while (in.available() > 0) {
				tokens.put(in.readUTF(), in.readLong());
			}
			in.close();
		}
		
		/**
		 * Reads all connect tokens from binary data.
		 * @param in The source of the data.
		 * @throws IOException If an error occurs reading data.
		 * @see #writeConnectTokens(DataOutputStream)
		 */
		protected void readConnectTokens(DataInputStream in) throws IOException {
			while (in.available() > 0) {
				String token = in.readUTF();
				long id = in.readLong();
				
				tokens2.put(token, id);
			}
			in.close();
		}
		
		/**
		 * Reads all users from binary data.
		 * @param in The source of the data.
		 * @throws IOException If an error occurs reading data.
		 * @see #writeUsers(DataOutputStream)
		 */
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
		
		/**
		 * Writes all tokens to a stream in a binary format.
		 * @param out The stream to write to.
		 * @throws IOException If an error occurs writing data.
		 * @see #readTokens(DataInputStream)
		 */
		protected void writeTokens(DataOutputStream out) throws IOException {
			for (Entry<String, Long> t : tokens.entrySet()) {
				out.writeUTF(t.getKey());
				out.writeLong(t.getValue());
			}
			out.flush();
			out.close();
		}
		
		/**
		 * Writes all connect tokens to a stream in a binary format.
		 * @param out The stream to write to.
		 * @throws IOException If an error occurs writing data.
		 * @see #readConnectTokens(DataInputStream)
		 */
		protected void writeConnectTokens(DataOutputStream out) throws IOException {
			for (Entry<String, Long> t : tokens2.entrySet()) {
				out.writeUTF(t.getKey());
				out.writeLong(t.getValue());
			}
			out.flush();
			out.close();
		}
		
		/**
		 * Writes all users to a stream in a binary format.
		 * @param out The stream to write to.
		 * @throws IOException If an error occurs writing data.
		 * @see #readUsers(DataInputStream)
		 */
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
		
		/**
		 * Saves the database to a file.
		 * @param folder The folder to write to.
		 * @throws IOException If an error occurs writing data.
		 */
		private void save(File folder) throws IOException {
			Log.info("Writing to " + folder.getAbsolutePath());
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

	/**
	 * A representation of a user.
	 * @author Tomas
	 */
	public static class User {
		/**
		 * The user's login.
		 */
		private String login;
		/**
		 * The user's password hash.
		 */
		private String hash;
		/**
		 * The user's username.
		 */
		private String username;
		/**
		 * The password salt.
		 */
		private byte[] salt;
		/**
		 * The user ID.
		 */
		private final long id;

		/**
		 * Creates a new user.
		 * @param login The user's login.
		 * @param password The user's password.
		 * @param id The user ID.
		 * @param username The user's username.
		 */
		public User(String login, String password, long id, String username) {
			super();
			this.login = login;
			setPassword(password);
			this.id = id;
			this.username = username;
		}

		/**
		 * Creates a user object with existing data.
		 * @param login The user's login.
		 * @param hash The user's password hash.
		 * @param id The user ID.
		 * @param salt The password salt.
		 * @param username The user's username.
		 */
		public User(String login, String hash, String username, byte[] salt, long id) {
			super();
			this.login = login;
			this.hash = hash;
			this.username = username;
			this.salt = salt;
			this.id = id;
		}

		/**
		 * Generates a salt using {@code SHA1PRNG}
		 * @return A random 16-item byte array.
		 * @throws NoSuchAlgorithmException If {@code SHA1PRNG} is not available.
		 */
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

		/**
		 * Hashes a password.
		 * @param passwordToHash The password to hash.
		 * @param salt The password salt.
		 * @return A hash string.
		 */
		private static String getSHA512SecurePassword(String passwordToHash, byte[] salt) {
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
		
		/**
		 * Sets the user's password.
		 * @param password The password to set.
		 */
		public void setPassword(String password) {
			try {
				this.salt = getSalt();
			} catch (NoSuchAlgorithmException e) {
				Log.error(e);
				Log.error("Force exiting for security reasons.");
				System.exit(-1);
				this.salt = new byte[0];
			}
			this.hash = getSHA512SecurePassword(password, salt);
		}
		
		/**
		 * Verifies if a login and password match this user.
		 * @param login The login to test.
		 * @param password The password to test.
		 * @return {@code true} if the login and password matches this user, {@code false} otherwise.
		 */
		public boolean verify(String login, String password) {
			if (!login.equalsIgnoreCase(login)) {
				return false;
			}
			return getSHA512SecurePassword(password, salt).equals(hash);
		}
		
		/**
		 * Gets this user's ID.
		 * @return A long value.
		 */
		public long getID() {
			return id;
		}
		
		/**
		 * Gets this user's username.
		 * @return A username string.
		 */
		public String getUsername() {
			return username;
		}
		
		/**
		 * Gets this user's login.
		 * @return A login string.
		 */
		public String getLogin() {
			return login;
		}

		/**
		 * Sets this user's login.
		 * @param login A login string.
		 */
		public void setLogin(String login) {
			this.login = login;
		}

		/**
		 * Sets this user's username.
		 * @param username A username string.
		 */
		public void setUsername(String username) {
			this.username = username;
		}
	}
	
	/**
	 * The main method for the CLI for the user server.
	 * @param args Command line arguments.
	 */
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
					System.out.println(String.format("%10s\t%20s", u.getUsername(), u.getLogin()));
				}
				break;
			case "listhash":
				System.out.println(" --- List Hashes --- ");
				Database d2 = server.getDatabase();
				
				for(User u : d2.users) {
					System.out.println(String.format("%10s\t%20s\t%30s", u.getUsername(), u.getLogin(), u.hash));
				}
				break;
			case "listtoken":
				System.out.println(" --- List Tokens --- ");
				Database d3 = server.getDatabase();
				
				for(Entry<String, Long> t : d3.tokens.entrySet()) {
					System.out.println(String.format("%70s\t%20s", t.getKey(), Long.toHexString(t.getValue())));
				}
				break;
			case "listtoken2":
				System.out.println(" --- List Connect Tokens --- ");
				Database d4 = server.getDatabase();
				
				for(Entry<String, Long> t : d4.tokens2.entrySet()) {
					System.out.println(String.format("%70s\t%20s", t.getKey(), Long.toHexString(t.getValue())));
				}
				break;
			default:
				System.out.println("Unknown Command: " + cmd);
				break;
			}
			System.out.println();
		}
	}
	
	/**
	 * Adds a user to the disk.
	 * @param s The input from the console.
	 * @param server The user server.
	 */
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
	
	/**
	 * Changes the details of a user on disk.
	 * @param s The input from the console.
	 * @param server The user server.
	 */
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
	
	/**
	 * Removes a user from the disk.
	 * @param s The input from the console.
	 * @param server The user server.
	 */
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
	
	/**
	 * Gets the next user ID.
	 * @param server The current user server.
	 * @return A long value that is not -1 and does not match previous entries in the server.
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUp() {
		return true;
	}
}

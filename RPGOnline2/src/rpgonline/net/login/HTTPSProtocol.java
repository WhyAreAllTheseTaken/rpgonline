package rpgonline.net.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.newdawn.slick.util.Log;

public class HTTPSProtocol implements ServerProtocol {
	private String url;
	
	public HTTPSProtocol(String url) {
		if (url.startsWith("https://")) {
			this.url = url;
		} else {
			throw new IllegalArgumentException("\"" + url + "\" - URL must use protocol \"https\"."
					+ "If your URL uses \"http\" used HTTPProtocol instead.");
		}
	}
	
	@Override
	public String request(Map<String, String> args) {
		try {
			String query = "";
			Object[] entries = args.entrySet().toArray();
			
			for (int i = 0; i < entries.length; i++) {
				if(i != 0) {
					query += "&";
				}
				
				@SuppressWarnings("unchecked")
				Entry<String, String> e = (Entry<String, String>) entries[i];
				
				query += URLEncoder.encode(e.getKey(), "UTF-8") + "=" + URLEncoder.encode(e.getValue(), "UTF-8");
			}
			
			URL myurl = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
			con.setRequestMethod("POST");
			
			con.setRequestProperty("Content-length", String.valueOf(query.length()));
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencode");
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
			con.setDoOutput(true);
			con.setDoInput(true);
			
			DataOutputStream output = new DataOutputStream(con.getOutputStream());

			output.writeBytes(query);

			output.close();
			
			DataInputStream input = new DataInputStream(con.getInputStream());

			StringBuilder response = new StringBuilder();

			for (int c = input.read(); c != -1; c = input.read()) {
				response.append(c);
			}
			input.close();
			
			Log.debug("Response: " + con.getResponseCode() + ": " + con.getResponseMessage());
			
			return response.toString();
		} catch (IOException e1) {
			Log.error("Error using HTTP", e1);
			return "";
		}
	}
	@Override
	public boolean isUp() {
		try {
			String domain = getDomainName(url);

			try (Socket socket = new Socket()) {
				socket.connect(new InetSocketAddress(domain, 80), 5000);
				return true;
			} catch (IOException e) {
				Log.error("Error checking server status: most likely a timeout.", e);
				return false; // Either timeout or unreachable or failed DNS lookup.
			}
		} catch (MalformedURLException e) {
			Log.error("Error checking server status", e);
			return false;
		}
	}
	
	/**
	 * Gets a domain name from a URL
	 * 
	 * @param url a URL.
	 * @return A domain name.
	 * @throws MalformedURLException If the URL is not valid.
	 */
	public static String getDomainName(String url) throws MalformedURLException {
		if (!url.startsWith("http") && !url.startsWith("https")) {
			url = "http://" + url;
		}
		URL netUrl = new URL(url);
		String host = netUrl.getHost();
		if (host.startsWith("www")) {
			host = host.substring("www".length() + 1);
		}
		return host;
	}
	
}

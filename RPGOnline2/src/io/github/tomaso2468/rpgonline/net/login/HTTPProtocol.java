/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.net.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.util.Log;

/**
 * <p>
 * A web connection using the HTTP-POST method. It is usually better to use
 * {@code WebUserServer} instead as it will automatically choose the correct
 * protocol.
 * </p>
 * <p>
 * Any data request is sent with the POST method. The server is also pinged on
 * port 80 to determine if the server is up. A new connection is opened and
 * closed for each request for data.
 * </p>
 * 
 * @author Tomaso2468
 * 
 * @see io.github.tomaso2468.rpgonline.net.login.HTTPSProtocol
 * @see io.github.tomaso2468.rpgonline.net.login.WebUserServer
 */
public class HTTPProtocol implements ServerProtocol {
	/**
	 * The site URL.
	 */
	private final String url;

	/**
	 * Constructs a new interface for a HTTP connection.
	 * 
	 * @param url The URL to connect to. <b>https:// should use
	 *            {@code HTTPSProtocol} instead.</b>
	 * 
	 * @see io.github.tomaso2468.rpgonline.net.login.HTTPSProtocol
	 */
	public HTTPProtocol(String url) {
		if (url.startsWith("http://")) {
			this.url = url;
		} else {
			throw new IllegalArgumentException("\"" + url + "\" - URL must use protocol \"http\"."
					+ "If your URL uses \"https\" used HTTPSProtocol instead.");
		}
		Log.warn("!!!!! - DO NOT USE HTTP FOR LOGONS - THIS SHOULD BE USED ONLY FOR A TEST SERVER - !!!!!");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String request(Map<String, String> args) {
		try {
			String query = "";
			Object[] entries = args.entrySet().toArray();

			for (int i = 0; i < entries.length; i++) {
				if (i != 0) {
					query += "&";
				}

				@SuppressWarnings("unchecked")
				Entry<String, String> e = (Entry<String, String>) entries[i];

				query += URLEncoder.encode(e.getKey(), "UTF-8") + "=" + URLEncoder.encode(e.getValue(), "UTF-8");
			}

			URL myurl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
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

	/**
	 * {@inheritDoc}
	 */
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

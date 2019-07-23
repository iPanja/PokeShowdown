package com.panjaco.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Player {
	private String _displayName;
	private String _IP;
	public Player(String displayName) {
		this(displayName, Player.getIP());
	}
	public Player(String displayName, String IP) {
		this._displayName = displayName;
		this._IP = IP;
	}
	public static String getIP() {
		try {
			URL service = new URL("http://checkip.amazonaws.com");
			BufferedReader output = new BufferedReader(new InputStreamReader(service.openStream()));
			String response = output.readLine();
			return response;
		}catch(Exception e) {
			return null;
		}
	}
	public String getDisplayName() {
		return this._displayName;
	}
	public String getIPAddress() {
		return this._IP;
	}
	public void setDisplayName(String displayName) {
		this._displayName = displayName;
	}
	public void setIPAddress(String IP) {
		this._IP = IP;
	}
}

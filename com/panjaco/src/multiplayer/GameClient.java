package com.panjaco.src.multiplayer;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.panjaco.src.Player;
import com.panjaco.src.UI;

// 7/21/2019
// Multiplayer Game Update
// This file will receive messages from the Server/Host Player

public class GameClient{
	public Player player;
	private Client instance;
	
	public GameClient() {
		this.player = null;
		this.instance = new Client();
		registerClasses();
		setupListeners();
	}
	
	private void registerClasses() {
		Kryo kryo = instance.getKryo();
		kryo.register(GameRequest.class);
	}
	private void setupListeners() {
		this.instance.addListener(new Listener() {
			@Override
			public void received(Connection connection, Object object) {
				if(object instanceof GameRequest) { //Game Invite Received BACK
					GameRequest gr = (GameRequest) object;
					if(gr.getResponse() == 0) { //Denied
						UI._UIController.showAlert("Game Invite Denied", "Your previous game invite was declined");
					}else if(gr.getResponse() == 1) { //Accepted
						UI.setupServer();
						UI.serverInstance.setupGame(gr.sender, gr.target);
						//If the other computer accepted it, they should now connect as well
					}else {
						System.out.println("Unexpected response from Game Request, outdated game version?");
					}
				}
			}
		});
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void sendRequest(GameRequest gr, String ip) {
		try {
			//Establish connection
			this.instance.connect(GameServer.connectionTimeout, ip, GameServer.tcpPort, GameServer.udpPort);
			this.instance.sendTCP(gr);
			//Send GameRequest object
		}catch(IOException e) {
			System.out.println("Failed to send game request");
			e.printStackTrace();
		}
	}

	public void sendRequest(String displayName, String ip) {
		UI.player = new Player(displayName);
		GameRequest gr = new GameRequest(player);
		sendRequest(gr, ip);
	}
}

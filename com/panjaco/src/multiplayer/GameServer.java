package com.panjaco.src.multiplayer;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.panjaco.src.*;

// 7/21/2019
// Multiplayer Game Update
// This file will send/receive messages to/from the Client

public class GameServer{
	//Multiplayer Config
	public static int tcpPort = 40506;
	public static int udpPort = 40508;
	public static int requestTimeout = 15000;
	public static int connectionTimeout = 5000;
	
	public static Server instance = null;
	private static Player[] players = new Player[2];
	public static boolean inGame = false;
	private static Connection gameConnection = null;
	
	public GameServer() {
		instance = new Server();
		instance.start();
		try {
			instance.bind(tcpPort, udpPort);
		}catch(IOException e) {
			System.out.println("Failed to bind TCP/UDP port");
			e.printStackTrace();
		}
		registerClasses();
		setupListeners();
	}
	
	private void registerClasses() {
		Kryo kryo = instance.getKryo();
		kryo.register(GameRequest.class);
	}
	
	private void setupListeners() {
		instance.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				//Check what type of object is sent to the server and respond accordingly
				//Send (object) response back via `connection.sendTCP(response)`
				if(object instanceof GameRequest) {
					GameRequest gr = (GameRequest) object;
					boolean accept = UI._UIController.confirmAlert("Game Invite", gr.sender.getDisplayName() + " has sent you a game invite. Would you like to play?");
					if(!accept) {
						gr.deny();
						connection.sendTCP(gr);
						return;
					}
					String username = UI._UIController.getInputAlert("1v1 Match", "Username: ");
					if(username == null)
						username = "Bruh Moment";
					UI.player = new Player(username);
					gr.accept(UI.player);
					connection.sendTCP(gr);
					
					GameServer.gameConnection = connection;
					GameServer.inGame = true;
					setupGame(UI.player, gr.sender);
				}
			}
		});
	}
	
	public void setupGame(Player pOne, Player pTwo) {
		players = new Player[2];
		joinGame(pOne);
		joinGame(pTwo);
	}
	
	public boolean joinGame(Player player) {
		for(int i = 0; i < this.players.length; i++) {
			if(this.players[i] == null) {
				this.players[i] = player;
				return true;
			}
		}
		return false;
	}
}

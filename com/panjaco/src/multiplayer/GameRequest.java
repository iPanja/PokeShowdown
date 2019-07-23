package com.panjaco.src.multiplayer;

import com.panjaco.src.Player;

public class GameRequest {
	public Player sender = null;
	public Player target = null;
	private int response = -1; //[-1 request sent] [0 denied] [1 accepted]
	public GameRequest(Player sender) {
		this.sender = sender;
	}
	public void deny() {
		this.response = 0;
	}
	public void accept(Player player) {
		this.target = player;
		this.response = 1;
	}
	public int getResponse() {
		return this.response;
	}
}

package com.tut3c.model;

public final class Move {
	
	private final int coordinate;
	private final Game game;
	private final Player player;
	
	public Move(int coordinate, Game game, Player player) {
		this.coordinate = coordinate;
		this.game = game;
		this.player = player;
	}
	
	public int getCoordinate() {
		return coordinate;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getPlayer() {
		return player;
	}

}

package com.tut3c.model;

import java.util.concurrent.atomic.AtomicInteger;

public final class Move {
	
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	
	private final int id = ID_GENERATOR.incrementAndGet();
	
	private final int coordinate;
	private final Game game;
	private final Player player;
	
	public Move(int coordinate, Game game, Player player) {
		this.coordinate = coordinate;
		this.game = game;
		this.player = player;
	}
	
	public int getId() {
		return id;
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

package com.tut3c.model;

import java.util.concurrent.atomic.AtomicInteger;

public final class Move {
	
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	
	private final int id = ID_GENERATOR.incrementAndGet();
	
	private final int field;
	private final Game game;
	private final Player player;
	
	public Move(int field, Game game, Player player) {
		this.field = field;
		this.game = game;
		this.player = player;
	}
	
	public int getId() {
		return id;
	}
	
	public int getField() {
		return field;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getPlayer() {
		return player;
	}

}

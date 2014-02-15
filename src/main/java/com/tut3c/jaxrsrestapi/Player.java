package com.tut3c.jaxrsrestapi;

import java.util.ArrayList;
import java.util.List;

public final class Player {
	
	private final String name;
	private final List<Game> games = new ArrayList<>();
	
	public Player(String name) {
		this.name = name;
	}

	public void addGame(Game game) {
		games.add(game);
	}
	
	public String getName() {
		return name;
	}
	
	public List<Game> getGames() {
		return games;
	}
	
}

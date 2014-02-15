package com.tut3c.model;

import java.util.ArrayList;
import java.util.List;

public final class Player {

	private String name;

	private int id;

	private final List<Game> games = new ArrayList<>();

	public Player() {
	}

	public Player(String name) {
		this.name = name;
	}

	public void addGame(Game game) {
		games.add(game);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Game> getGames() {
		return games;
	}

	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + " " + name + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

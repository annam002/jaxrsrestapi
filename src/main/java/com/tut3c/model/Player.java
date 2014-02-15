package com.tut3c.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class Player {

	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	
	private final int id = ID_GENERATOR.incrementAndGet();
	private final List<Game> games = new ArrayList<>();
	
	private String name;

	public Player() {
	}

	public Player(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + " " + name + "]";
	}

}

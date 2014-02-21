package com.tut3c.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public final class Player {

	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	
	private final int playerid = ID_GENERATOR.incrementAndGet();
	private final List<Game> games = new ArrayList<>();
	
	private String name;

	public Player() {
		System.out.println("New Player here!");
	}

	public Player(String name) {
		this.name = name;
	}
	
	public int getPlayerid() {
		return playerid;
	}

	public void addGame(Game game) {
		games.add(game);
	}

	@XmlTransient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public List<Game> getGames() {
		return games;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + playerid;
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
		if (playerid != other.playerid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + " " + name + "]";
	}

}

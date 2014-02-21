package com.tut3c.model;

import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public final class Move {
	
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	
	private final int moveid = ID_GENERATOR.incrementAndGet();
	
	private final Coordinate coordinate;
	private final Game game;
	private final Player player;
	
	public Move(String field, Game game, Player player) {
		this.coordinate = new Coordinate(field);
		this.game = game;
		this.player = player;
	}
	
	public int getMoveid() {
		return moveid;
	}
	
	@XmlTransient
	public Game getGame() {
		return game;
	}
	
	@XmlTransient
	public Player getPlayer() {
		return player;
	}

	@XmlTransient
	public Coordinate getCoordinate() {
		return coordinate;
	}
	
}

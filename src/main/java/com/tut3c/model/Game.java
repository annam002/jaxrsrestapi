package com.tut3c.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
	
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	
	private final int id = ID_GENERATOR.incrementAndGet();
	
	private final Board board = new Board();
	private final List<Move> moves = new ArrayList<>();

	private Player player1;
	private Player player2;
	
	public int getId() {
		return id;
	}
	
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public boolean addMove(Player player, int coordinate) {
		if (!validPlayer(player)) {
			return false;
		}
		Move move = new Move(coordinate, this, player);
		if (board.setMove(move, player)) {
			moves.add(move);
			return true;
		}
		return false;
	}
	
	private boolean validPlayer(Player player) {
		return player.equals(player1) || player.equals(player2);
	}

	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public List<Move> getMoves() {
		return moves;
	}
	
	public Board getBoard() {
		return board;
	}
	
}

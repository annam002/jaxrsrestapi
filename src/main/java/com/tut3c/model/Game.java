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
	private Player currentPlayer;
	
	public int getId() {
		return id;
	}
	
	public void setPlayer1(Player player1) {
		this.player1 = player1;
		this.currentPlayer = player1;
	}
	
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Move addMove(Player player, int field) {
		if (!validPlayer(player)) {
			return null;
		}
		
		Move move = new Move(field, this, player);
		if (board.setMove(move, player)) {
			moves.add(move);
			updatePlayer(player);
			return move;
		}
		return null;
	}
	
	private void updatePlayer(Player player) {
		if (player.equals(player1)) {
			currentPlayer = player2;
		}
		else {
			currentPlayer = player1;
		}
		
	}

	private boolean validPlayer(Player player) {
		return player != null && player.equals(currentPlayer);
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

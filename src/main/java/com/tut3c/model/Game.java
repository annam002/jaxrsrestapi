package com.tut3c.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private final Player player1;
	private final Player player2;
	private final Board board = new Board();
	private final List<Move> moves = new ArrayList<>();
	
	public Game(Player player1, Player player2) {
		this.player1 = player1;
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

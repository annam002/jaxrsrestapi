package com.tut3c.jaxrsrestapi;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private final Player player1;
	private final Player player2;
	private final Board board = new Board();
	private final List<Move> moves = new ArrayList<>();
	
	private Player currentPlayer;
	
	public Game(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		currentPlayer = player1;
	}

	public boolean addMove(Move move) {
		if (board.setMove(move, currentPlayer)) {
			moves.add(move);
			if (currentPlayer.equals(player1)) {
				currentPlayer = player2;
			}
			else {
				currentPlayer = player1;
			}
			return true;
		}
		return false;
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

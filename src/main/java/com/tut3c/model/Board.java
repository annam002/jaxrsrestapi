package com.tut3c.model;

public class Board {

	public enum BoardState { RUNNING, DRAW, WIN }
	
	private final Player[] fields = new Player[9];
	private BoardState state = BoardState.RUNNING;
	private Player winningPlayer = null;

	public boolean setMove(Move move, Player player) {
		int coordinate = move.getField();
		if (fields[coordinate] != null) {
			return false;
		}
		fields[coordinate] = player;
		updateGameState();
		return true;
	}
	
	public Player[] getFields() {
		return fields;
	}

	public BoardState getState() {
		return state;
	}
	
	public Player getWinningPlayer() {
		return winningPlayer;
	}
	
	private void updateGameState() {
		if (inOneRow(0, 1, 2)
			|| inOneRow(3, 4, 5)	
			|| inOneRow(6, 7, 8)	
			|| inOneRow(0, 3, 6)	
			|| inOneRow(1, 4, 7)	
			|| inOneRow(2, 5, 8)	
			|| inOneRow(0, 4, 8)	
			|| inOneRow(2, 4, 6)	
			|| inOneRow(0, 3, 6)	
			) {
			state = BoardState.WIN;
		}
		else if (allFieldsSet()) {
			state = BoardState.DRAW;
		}
	}
	
	private boolean allFieldsSet() {
		for (int i = 0; i < 9; i++) {
			if (fields[i] == null) {
				return false;
			}
		}
		return true;
	}
	
	private boolean inOneRow(int c1, int c2, int c3) {
		Player player1 = fields[c1];
		Player player2 = fields[c2];
		Player player3 = fields[c3];
		
		if (player1 == null || player2 == null || player3 == null) {
			return false;
		}
		boolean inOneRow = player1.equals(player2) && player1.equals(player3);
		if (inOneRow) {
			winningPlayer = player1;
		}
		return inOneRow;
	}
	

}

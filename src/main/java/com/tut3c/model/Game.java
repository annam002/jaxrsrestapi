package com.tut3c.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Game {
	
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	
	public static final String GAME_STATE_OPEN = "OPEN";
	public static final String GAME_STATE_RUNNING = "RUNNING";
	public static final String GAME_STATE_FINISHED = "FINISHED";
	
	private final int gameid = ID_GENERATOR.incrementAndGet();
	
	private final Board board = new Board();
	private final List<Move> moves = new ArrayList<>();

	private Player player1;
	private Player player2;
	private Player currentPlayer;
	
	public static Game create(Player player, Boolean isFirst) {
		Game game = new Game();
    	if (isFirst != null && !isFirst) {
    		game.player2 = player;
    	}
    	else {
    		game.player1 = player;
    	}
		return game;
	}
	
	public int getGameid() {
		return gameid;
	}
	
	@XmlTransient
	public List<Move> getMoves() {
		return moves;
	}
	
	@XmlTransient
	public Board getBoard() {
		return board;
	}
	
	@XmlTransient
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void addPlayer(Player player) {
		if (player2 == null) {
    		this.player2 = player;
    	}
    	else {
    		this.player1 = player;
    	}
		this.currentPlayer = player;
		
	}
	
	public Move addMove(Player player, String coordinate) {
		if (!validPlayer(player)) {
			return null;
		}
		
		Move move = new Move(coordinate, this, player);
		if (board.setMove(move, player)) {
			moves.add(move);
			updatePlayer(player);
			return move;
		}
		return null;
	}
	
	@XmlTransient
	public String getGameState() {
		if (player2 == null) {
			return GAME_STATE_OPEN;
		}
		if (getBoard().getState() == Board.BoardState.RUNNING) {
			return GAME_STATE_RUNNING;
		}
		return GAME_STATE_FINISHED;
	}
	
	@XmlTransient
	public List<Player> getGamePlayers() {
		List<Player> playerMap = new ArrayList<>();
		if (player1 != null) {
			playerMap.add(player1);
		}
		if (player2 != null) {
			playerMap.add(player2);
		}
		return playerMap;
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

}

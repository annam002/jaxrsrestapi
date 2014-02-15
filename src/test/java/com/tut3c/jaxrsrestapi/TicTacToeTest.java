package com.tut3c.jaxrsrestapi;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tut3c.model.Board;
import com.tut3c.model.Game;
import com.tut3c.model.Player;
import com.tut3c.model.Board.BoardState;

public class TicTacToeTest {

	@Test
	public void test1() {
		Player player1 = new Player("player1");
		Player player2 = new Player("player2");
		Game game = new Game(player1, player2);
		
		game.addMove(player1, 0);
		game.addMove(player2, 1);
		game.addMove(player1, 3);
		game.addMove(player2, 4);
		game.addMove(player1, 6);
		
		System.out.println(debugPrint(game.getBoard()));
		
		assertEquals(BoardState.WIN, game.getBoard().getState());
		assertEquals(player1, game.getBoard().getWinningPlayer());
	}
	
	
	private String debugPrint(Board board) {
		StringBuilder sb = new StringBuilder();
		Player[] fields = board.getFields();
		sb.append(fields[0]).append(" ").append(fields[1]).append(" ").append(fields[2]).append("\n");
		sb.append(fields[3]).append(" ").append(fields[4]).append(" ").append(fields[5]).append("\n");
		sb.append(fields[6]).append(" ").append(fields[7]).append(" ").append(fields[8]).append("\n");
		return sb.toString();
		
	}
}

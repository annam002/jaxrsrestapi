package com.tut3c.jaxrsrestapi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tut3c.model.Board;
import com.tut3c.model.Board.BoardState;
import com.tut3c.model.Game;
import com.tut3c.model.Player;

public class TicTacToeTest {

	@Test
	public void test1() {
		Player player1 = new Player("player1");
		Player player2 = new Player("player2");

		Game game = new Game();
		game.setPlayer1(player1);
		game.setPlayer2(player2);

		game.addMove(player1, "a1");
		game.addMove(player2, "a2");
		game.addMove(player1, "b1");
		game.addMove(player2, "b2");
		game.addMove(player1, "a3");
		game.addMove(player2, "b3");
		game.addMove(player1, "c2");
		game.addMove(player2, "c3");
		game.addMove(player1, "c1");

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

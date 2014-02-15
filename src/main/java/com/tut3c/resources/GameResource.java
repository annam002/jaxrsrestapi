package com.tut3c.resources;

import static com.tut3c.resources.PlayerResource.getPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.tut3c.model.Board;
import com.tut3c.model.Game;
import com.tut3c.model.Move;
import com.tut3c.model.Player;

/**
 * The Ultimate Tic Tac Toe Challenge 2014
 *
 * http://jugda.wordpress.com/termine/tic-tac-toe/
 */
@Path("/game")
@Produces({ "application/json" })
public class GameResource {

	private static final String GAMEID = "gameid";
	private static final String IS_FIRST = "first";
	private static final String FIELD = "field";
	private static final String MOVEID = "moveid";
	private static final String PLAYERS = "players";
	private static final String STATE = "state";
	private static final String NEXT = "next";
	private static final String WINNER = "winner";
	private static final String GAME_STATE_OPEN = "OPEN";
	private static final String GAME_STATE_RUNNING = "RUNNING";
	private static final String GAME_STATE_FINISHED = "FINISHED";

	public static Map<Integer, Game> games = new HashMap<>();

	private static Map<String, Integer> coordinateMap = createCoordinateMap();

    @POST
    @Consumes({ "application/json" })
    public Response create(HashMap<String, Object> parameter) {
    	Player player = getPlayer(parameter);
    	Boolean isFirst = (Boolean) parameter.get(IS_FIRST);

    	if (player == null) {
    		return conflict();
    	}

    	Game game = new Game();
    	if (isFirst != null && !isFirst) {
    		game.setPlayer2(player);
    	}
    	else {
    	game.setPlayer1(player);
    	}
    	games.put(game.getId(), game);

        return Response.accepted(buildMap(GAMEID, game.getId())).build();
    }

    @GET
    public List<Map<String, Object>> getGames(@QueryParam("state") String state) {
    	List<Map<String, Object>> result = new ArrayList<>();
    	for (Game game : games.values()) {
    		if (game.getPlayer2() == null) {
    			result.add(buildMap(GAMEID, game.getId()));
    		}
    	}

    	return result;
    }

    @PUT
    @Path("/{gameid:.+}")
    @Consumes({ "application/json" })
    public Response joinGame(@PathParam("gameid") Integer gameId, HashMap<String, Object> parameter) {
    	Game game = games.get(gameId);
    	Player player = getPlayer(parameter);

    	if (game == null || player == null) {
    		return conflict();
    	}

    	if (game.getPlayer2() == null) {
    	game.setPlayer2(player);
    	}
    	else {
    		game.setPlayer1(player);
    	}

        return Response.noContent().build();
    }

    @GET
    @Path("/{gameid:.+}")
    public Response getGame(@PathParam("gameid") Integer gameId) {
    	Game game = games.get(gameId);

    	if (game == null) {
    		return notFound();
    	}

    	List<Map<String, Object>> result = new ArrayList<>();
    	result.add(buildMap(PLAYERS, getGamePlayers(game)));
    	result.add(buildMap(STATE, getGameState(game)));
    	result.add(buildMap(NEXT, getNextPlayer(game)));
    	result.add(buildMap(WINNER, getWinner(game)));

        return Response.accepted(result).build();
    }

	private List<Map<String, Object>> getGamePlayers(Game game) {
		List<Map<String, Object>> playerMap = new ArrayList<>();
    	playerMap.add(buildMap(PlayerResource.PLAYERID, getNonNullPlayerId(game.getPlayer1())));
    	playerMap.add(buildMap(PlayerResource.PLAYERID, getNonNullPlayerId(game.getPlayer2())));
		return playerMap;
	}

	private String getGameState(Game game) {
		if (game.getPlayer2() == null) {
			return GAME_STATE_OPEN;
		}
		if (game.getBoard().getState() == Board.BoardState.RUNNING) {
			return GAME_STATE_RUNNING;
		}
		return GAME_STATE_FINISHED;
	}

	private Map<String, Object> getNextPlayer(Game game) {
		return buildMap(PlayerResource.PLAYERID, getNonNullPlayerId(game.getCurrentPlayer()));
	}

	private Map<String, Object> getWinner(Game game) {
		return buildMap(PlayerResource.PLAYERID, getNonNullPlayerId(game.getBoard().getWinningPlayer()));
	}

	private Integer getNonNullPlayerId(Player player) {
		return player != null ? player.getId() : null;
	}

	@POST
    @Path("/{gameid:.+}/move")
    @Consumes({ "application/json" })
    public Response move(@PathParam("gameid") Integer gameId, HashMap<String, Object> parameter) {
		Game game = games.get(gameId);
    	Player player = getPlayer(parameter);
    	Integer coordinate = getField(parameter);

    	if (game == null || player == null || coordinate == null) {
    		return conflict();
    	}

		Move move = game.addMove(player, coordinate);
    	return move != null ?  Response.accepted(move).build() : conflict();
    }

    @GET
    @Path("/{gameid:.+}/move")
    public Response getMoves(@PathParam("gameid") Integer gameId) {
    	Game game = games.get(gameId);

    	if (game == null) {
    		return notFound();
    	}

    	List<Map<String, Object>> result = new ArrayList<>();
    	for (Move move : game.getMoves()) {
    		result.add(buildMap(MOVEID, move.getId()));
    	}
    	return Response.accepted(result).build();
    }

    @GET
    @Path("/{gameid:.+}/move/{moveid:.+}")
    public Response getMove(@PathParam("gameid") Integer gameId, @PathParam("moveid") Integer moveId) {
    	Game game = games.get(gameId);
    	if (game == null || moveId == null) {
    		return notFound();
    	}

    	for (Move move : game.getMoves()) {
    		if (move.getId() == moveId) {
    			Map<String, Object> moveMap = new HashMap<>();
    			moveMap.put(PlayerResource.PLAYERID, move.getPlayer().getId());
    			moveMap.put(FIELD, getCoordinate(move.getField()));
    			return Response.accepted(moveMap).build();
    		}
    	}
    	return notFound();
    }

	private Response notFound() {
		return Response.status(Status.NOT_FOUND).build();
	}

	private Response conflict() {
		return Response.status(Status.CONFLICT).build();
	}

    private Map<String, Object> buildMap(String fieldName, Object value) {
    	Map<String, Object> result = new HashMap<>();
    	result.put(fieldName, value);
		return result;
	}

	private Integer getField(Map<String, Object> parameter) {
		return coordinateMap.get(((String) parameter.get(FIELD)).toUpperCase());
	}

	private String getCoordinate(int field) {
		for (Entry<String, Integer> entry : coordinateMap.entrySet()) {
			if (entry.getValue() == field) {
				return entry.getKey();
			}
		}
		return "";
	}

    private static Map<String, Integer> createCoordinateMap() {
    	Map<String, Integer> map = new HashMap<>();
    	map.put("A1", 0);
    	map.put("A2", 1);
    	map.put("A3", 2);
    	map.put("B1", 3);
    	map.put("B2", 4);
    	map.put("B3", 5);
    	map.put("C1", 6);
    	map.put("C2", 7);
    	map.put("C3", 8);
    	return map;
    }

}
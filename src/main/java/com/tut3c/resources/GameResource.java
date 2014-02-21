package com.tut3c.resources;

import static com.tut3c.resources.PlayerResource.getPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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

	private static final String IS_FIRST = "first";
	private static final String FIELD = "field";
	private static final String PLAYERS = "players";
	private static final String STATE = "state";
	private static final String NEXT = "next";
	private static final String WINNER = "winner";

	public static Map<Integer, Game> games = new HashMap<>();

    @POST
    @Consumes({ "application/json" })
    public Response create(HashMap<String, Object> parameter) {
    	Player player = getPlayer(parameter);

    	if (player == null) {
    		throw new WebApplicationException(Status.CONFLICT);
    	}
    	
    	Boolean isFirst = (Boolean) parameter.get(IS_FIRST);
    	Game game = Game.create(player, isFirst);
    	games.put(game.getGameid(), game);

        return Response.ok(game).build();
    }

    @GET
    public List<Game> getGames(@QueryParam("state") String state) {
    	List<Game> result = new ArrayList<>();
    	for (Game game : games.values()) {
    		if (game.getGameState().equals(Game.GAME_STATE_OPEN)) {
    			result.add(game);
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
    		throw new WebApplicationException(Status.CONFLICT);
    	}

    	game.addPlayer(player);
        return Response.ok().build();
    }

    @GET
    @Path("/{gameid:.+}")
    public Response getGame(@PathParam("gameid") Integer gameId) {
    	Game game = games.get(gameId);

    	if (game == null) {
    		throw new WebApplicationException(Status.NOT_FOUND);
    	}

    	List<Map<String, Object>> result = new ArrayList<>();
    	result.add(buildMap(PLAYERS, game.getGamePlayers()));
    	result.add(buildMap(STATE, game.getGameState()));
    	result.add(buildMap(NEXT, game.getCurrentPlayer()));
    	result.add(buildMap(WINNER, game.getBoard().getWinningPlayer()));

        return Response.ok(result).build();
    }

	@POST
    @Path("/{gameid:.+}/move")
    @Consumes({ "application/json" })
    public Response move(@PathParam("gameid") Integer gameId, HashMap<String, Object> parameter) {
		Game game = games.get(gameId);
    	Player player = getPlayer(parameter);
    	String coordinate = (String) parameter.get(FIELD);

    	if (game == null || player == null || coordinate == null) {
    		throw new WebApplicationException(Status.CONFLICT);
    	}

		Move move = game.addMove(player, coordinate);
		if (move == null) {
			throw new WebApplicationException(Status.CONFLICT);
		}
		
    	return Response.ok(move).build();
    }

    @GET
    @Path("/{gameid:.+}/move")
    public Response getMoves(@PathParam("gameid") Integer gameId) {
    	Game game = games.get(gameId);

    	if (game == null) {
    		throw new WebApplicationException(Status.NOT_FOUND);
    	}

    	List<Move> result = new ArrayList<>();
    	for (Move move : game.getMoves()) {
    		result.add(move);
    	}
    	return Response.ok(result).build();
    }

    @GET
    @Path("/{gameid:.+}/move/{moveid:.+}")
    public Response getMove(@PathParam("gameid") Integer gameId, @PathParam("moveid") Integer moveId) {
    	Game game = games.get(gameId);
    	if (game == null || moveId == null) {
    		throw new WebApplicationException(Status.NOT_FOUND);
    	}

    	for (Move move : game.getMoves()) {
    		if (move.getMoveid() == moveId) {
    			List<Object> moveList = new ArrayList<>();
    			moveList.add(move.getPlayer());
    			moveList.add(move.getCoordinate());
    			return Response.ok(moveList).build();
    		}
    	}
    	throw new WebApplicationException(Status.NOT_FOUND);
    }

    private Map<String, Object> buildMap(String fieldName, Object value) {
    	Map<String, Object> result = new HashMap<>();
    	result.put(fieldName, value);
		return result;
	}

}
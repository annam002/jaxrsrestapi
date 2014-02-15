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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
	private static final String FIELD = "field";
	private static final String MOVEID = "moveid";
	private static final String PLAYERS = "players";
	private static final String STATE = "state";
	private static final String NEXT = "next";
	private static final String WINNER = "winner";

	public static Map<Integer, Game> games = new HashMap<>();
	
	private static Map<String, Integer> coordinateMap = createCoordinateMap();

//	private static Logger LOG = Logger.getLogger(GameResource.class);

    @POST
    @Consumes({ "application/json" })
    public Map<String, Object> create(HashMap<String, Object> parameter) {
    	Player player = getPlayer(parameter);
    	
    	Game game = new Game();
    	game.setPlayer1(player);
    	games.put(game.getId(), game);
    	
        return buildMap(GAMEID, game.getId());
    }
    
    @GET
    public List<Map<String, Object>> getGames() {
    	List<Map<String, Object>> result = new ArrayList<>();
    	for (Game game : games.values()) {
    		if (game.getPlayer2() == null) {
    			result.add(buildMap(GAMEID, game.getId()));
    		}
    	}
    	
    	return result;
    }
    
    @POST
    @Path("/{gameid:.+}")
    @Consumes({ "application/json" })
    public Response joinGame(@PathParam("gameid") Integer gameId, HashMap<String, Object> parameter) {
    	Game game = games.get(gameId);
    	Player player = getPlayer(parameter);
    	game.setPlayer2(player);
    	
        return Response.noContent().build();
    }
    
    //players: { { playerid: 789 }, { playerid: 012 } }, 
    //state: { "OPEN" | "RUNNING" | "FINISHED" }, 
    //next: { playerid: 123 }, 
    //winner: { playerid: 123 }
    @GET
    @Path("/{gameid:.+}")
    public List<Map<String, Object>> getGame(@PathParam("gameid") Integer gameId) {
    	Game game = games.get(gameId);
    	List<Map<String, Object>> result = new ArrayList<>();
    	List<Map<String, Object>> playerMap = new ArrayList<>();
    	playerMap.add(buildMap(PlayerResource.PLAYERID, game.getPlayer1().getId()));
    	playerMap.add(buildMap(PlayerResource.PLAYERID, game.getPlayer2().getId()));
    	result.add(buildMap(PLAYERS, playerMap));
    	
        return result;
    }

	@POST
    @Path("/{gameid:.+}/move")
    @Consumes({ "application/json" })
    public Response move(@PathParam("gameid") Integer gameId, HashMap<String, Object> parameter) {
    	Player player = getPlayer(parameter);
    	Game game = games.get(gameId);
    	Integer coordinate = getField(parameter);
		game.addMove(player, coordinate);
    	
    	return Response.noContent().build();
    }
    
    @GET
    @Path("/{gameid:.+}/move")
    public List<Map<String, Object>> getMoves(@PathParam("gameid") Integer gameId) {
    	Game game = games.get(gameId);
    	List<Map<String, Object>> result = new ArrayList<>();
    	for (Move move : game.getMoves()) {
    		result.add(buildMap(MOVEID, move.getId()));
    	}
    	return result;
    }
    
    @GET
    @Path("/{gameid:.+}/move/{moveid:.+}")
    public List<Map<String, Object>> getMove(@PathParam("gameid") Integer gameId, @PathParam("moveid") Integer moveId) {
    	Game game = games.get(gameId);
    	List<Map<String, Object>> result = new ArrayList<>();
    	for (Move move : game.getMoves()) {
    		if (move.getId() == moveId) {
    			Map<String, Object> moveMap = new HashMap<>();
    			moveMap.put(PlayerResource.PLAYERID, move.getPlayer().getId());
    			moveMap.put(FIELD, getCoordinate(move.getField()));
    			result.add(moveMap);
    			break;
    		}
    	}
    	return result;
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
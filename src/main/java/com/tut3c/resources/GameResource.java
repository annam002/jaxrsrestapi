package com.tut3c.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.tut3c.model.Game;
import com.tut3c.model.Player;

@Path("/game")
public class GameResource {

	private static final String GAMEID = "gameid";

	private final static AtomicInteger gameId = new AtomicInteger(0);

	public static Map<Integer, Game> games = new HashMap<>();

	private static Logger LOG = Logger.getLogger(GameResource.class);

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Map<String, Object> create(HashMap<String, Object> parameter) {
    	Player player = PlayerResource.players.get(parameter.get("playerid"));
    	
    	Game game = new Game();
    	game.setPlayer1(player);
    	game.setId(gameId.incrementAndGet());
    	games.put(game.getId(), game);
    	
    	Map<String, Object> result = new HashMap<>();
    	result.put(GAMEID, game.getId());
        return result;
    }
    
    @GET
    @Produces({ "application/json" })
    public List<Map<String, Object>> getGames() {
    	List<Map<String, Object>> result = new ArrayList<>();
    	for (Game game : games.values()) {
    		if (game.getPlayer2() == null) {
    			Map<String, Object> gameMap = new HashMap<>();
    			gameMap.put(GAMEID, game.getId());
    			result.add(gameMap);
    		}
    	}
    	
    	return result;
    }

}
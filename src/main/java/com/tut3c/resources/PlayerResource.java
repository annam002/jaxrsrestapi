package com.tut3c.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.tut3c.model.Player;

/**
 * The Ultimate Tic Tac Toe Challenge 2014
 *
 * http://jugda.wordpress.com/termine/tic-tac-toe/
 */
@Path("/player")
@Produces({ "application/json" })
public class PlayerResource {

	public static final String PLAYER_ID = "playerid";
	private static final String PLAYER_NAME = "name";

	public static Map<Integer, Player> players = new HashMap<>();

	private static Logger LOG = Logger.getLogger(PlayerResource.class);

	public static Player getPlayer(Map<String, Object> parameter) {
		return players.get(parameter.get(PLAYER_ID));
	}

	@POST
	@Consumes({ "application/json" })
	public Player register(Map<String, Object> parameters) {
		String name = (String) parameters.get(PLAYER_NAME);
		if (name == null) {
			throw new WebApplicationException(Status.CONFLICT);
		}
		
		Player player = new Player(name);
		players.put(player.getPlayerid(), player);
		LOG.info("player name: " + player.getName());
		return player;
	}

	@GET
	@Path("/{id}")
	public Map<String, Object> getById(@PathParam("id") Integer id) {
		if (!players.containsKey(id)) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		Player player = players.get(id);
		Map<String, Object> result = new HashMap<>();
		result.put(PLAYER_ID, player.getPlayerid());
		result.put(PLAYER_NAME, player.getName());
		return result;
	}

	@GET
	public List<Player> getAll() {
		return new ArrayList<>(players.values());
	}

}
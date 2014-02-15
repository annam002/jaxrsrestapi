package com.tut3c.resources;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.tut3c.model.Player;

/**
* A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
* is enabled
*
* @author gbrey@redhat.com
*
*/

@Path("/player")
public class PlayerResource {

	private final static AtomicInteger playerId = new AtomicInteger(0);

	public static HashMap<Integer, Player> players = new HashMap<>();

	private static Logger LOG = Logger.getLogger(PlayerResource.class);

    @POST
    @Produces({ "application/json" })
    @Consumes({ "application/json" })
    public HashMap<String, Object> register(Player player) {
    	player.setId(playerId.incrementAndGet());
    	players.put(player.getId(), player);
    	LOG.info("player name: " + player.getName());

    	HashMap<String, Object> response = new HashMap<>();
    	response.put("playerid", player.getId());

        return response;
    }

    @GET
    @Produces({ "application/json" })
    public Player get() {
    	Player p = new Player("name");
        return p;
    }

}
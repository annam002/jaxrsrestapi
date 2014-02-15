jaxrsrestapi
============

A first JAX-RS for Wildfly Implementation

![Brainstorming](brainstorming.jpg)

#Player#

##create a new player##

URL: /player

HTTP METHOD POST: { name: "user name" }

RESPONSE: { playerid: 123 }

#GAME#

##create a new game##

URL: /game

HTTP METHOD POST: { playerid: 123 }

RESPONSE: { gameid: 123 }

##list all games with one player##

URL: /game

HTTP METHOD GET

RESPONSE: [ { gameid: 123 }, { gameid: 456 } ]

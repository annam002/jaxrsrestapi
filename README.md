jaxrsrestapi
============

A first JAX-RS for Wildfly Implementation

![Brainstorming](brainstorming.jpg)

#Test-Server#

http://192.168.2.24:8080/TicTacWildfy-0.0.1-SNAPSHOT/

http://192.168.2.24:8080/TicTacWildfy-0.0.1-SNAPSHOT/rest/player

http://192.168.2.24:8080/TicTacWildfy-0.0.1-SNAPSHOT/rest/game

Management-Konsole:

http://admin:adminadmin@192.168.2.24:9990

#Player#

##create a new player##

URL: /player

HTTP METHOD POST: {"name":"user name"}

RESPONSE: {"playerid":123}

##get player##

URL: /player/{"playerid":123}

HTTP METHOD GET

RESPONSE: {"playerid":123,"name":"user name"}

ERROR RESPONSE: HTTP 404 NOT FOUND

##list all players##

URL: /player

HTTP METHOD GET

RESPONSE: [{"playerid":123},{"playerid":456}]

#Game#

##create a new game##

URL: /game

HTTP METHOD POST: {playerid:123}

RESPONSE: {gameid:123}

##join a game##

URL: /game/{gameid:123}

HTTP METHOD PUT: {playerid:789}

RESPONSE: HTTP 204 NO CONTENT

##show game state##

URL: /game/{gameid:123}

HTTP METHOD GET

RESPONSE: {players:[{playerid:789},{playerid:012}],state:{"OPEN"|"RUNNING"|"FINISHED"},next:{playerid:123},winner:{ playerid:123}}

ERROR RESPONSE: HTTP 404 NOT FOUND

##list all games##

URL: /game

HTTP METHOD GET

RESPONSE: [{gameid:123},{gameid:456}]

##list all games with one player##

URL: /game?state="OPEN"

HTTP METHOD GET

RESPONSE: [{gameid:123},{gameid:456}]

#Move#

##create a new move##

URL: /game/{gameid:123}/move

HTTP METHOD POST: {playerid:123,field:"B1"}

RESPONSE: HTTP 204 NO CONTENT

ERROR RESPONSE: HTTP 409 CONFLICT

Move not allowed!

##get move##

URL: /game/{gameid:123}/move/{moveid:123}

HTTP METHOD GET

RESPONSE: {playerid:123,field:"B1"}

ERROR RESPONSE: HTTP 404 NOT FOUND

##list all moves##

URL: /game/{gameid:123}/move

HTTP METHOD GET

RESPONSE: [{moveid:123},{moveid:456}]

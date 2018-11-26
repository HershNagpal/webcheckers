package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Message;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The {@code POST /game} route handler for checking player
 * turns in spectator mode.
 *
 * @author Michael Kha
 */
public class PostSpectatorCheckTurnRoute implements Route {

    /**
     * The game center
     */
    private GameCenter gameCenter;

    /**
     * Interprets and converts json
     */
    private Gson gson;

    /**
     * The constructor for the {@code POST /game} route handler to check
     * whose turn it is in spectator mode.
     *
     * @param gameCenter holds the ongoing games
     * @param gson used to interpret and convert json
     */
    public PostSpectatorCheckTurnRoute(GameCenter gameCenter, Gson gson) {
        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    /**
     * This action checks to see if players have submitted their turn.
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @return Info Message, true if a player submits the turn, false otherwise.
     */
    @Override
    public Object handle(Request request, Response response) {
        String gameID = request.body();
        gameID = String.join("+", gameID.split(" "));
        Message message = gameCenter.checkTurn(gameID);
        return gson.toJson(message);
    }
}

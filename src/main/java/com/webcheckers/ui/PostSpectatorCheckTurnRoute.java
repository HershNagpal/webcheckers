package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
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
     *
     * @param gameCenter
     * @param gson
     */
    public PostSpectatorCheckTurnRoute(GameCenter gameCenter, Gson gson) {
        this.gson = gson;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object handle(Request request, Response response) {
        String stringJSON = request.body();
        String gameID = gson.fromJson(stringJSON, String.class);

        return null;
    }
}

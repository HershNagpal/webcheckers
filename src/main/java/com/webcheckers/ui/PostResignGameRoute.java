package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.Objects;

/**
 * The {@code POST /game} route handler for resigning a game.
 *
 * @author Michael Kha
 */
public class PostResignGameRoute implements Route {

    /**
     * The game center
     */
    private GameCenter gameCenter;

    /**
     * Interprets and converts json
     */
    private Gson gson;

    /**
     * The constructor for the {@code POST /game} route handler to resign
     * from a game.
     *
     * @param gameCenter holds the ongoing games
     * @param gson used to interpret and convert json
     */
    public PostResignGameRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    /**
     * This action tells the game that the current player is attempting to resign.
     * @param request The HTTP request
     * @param response The HTTP response
     * @return Message indicating if the user successfully resigned or not
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Message message = gameCenter.resignGame(player);
        return gson.toJson(message);
    }
}

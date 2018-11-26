package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Player;
import spark.*;
import java.util.Objects;

/**
 * The {@code POST /game} route handler for checking player turns.
 *
 * @author Michael Kha
 * @author Matthew Bollinger
 * @author Luis Gutierrez
 */
public class PostCheckTurnRoute implements Route {

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
     * whose turn it is.
     *
     * @param gameCenter holds the ongoing games
     * @param gson used to interpret and convert json
     */
    public PostCheckTurnRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    /**
     * This action checks to see if the opponent has submitted their turn.
     * @param request The HTTP request
     * @param response The HTTP response
     * @return Info Message, true if opponent is done with turn, false otherwise.
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Message message = gameCenter.checkTurn(player);
        return gson.toJson(message);
    }
}
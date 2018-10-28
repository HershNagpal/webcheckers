package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import spark.*;

import java.util.Objects;

/**
 * The {@code POST /game} route handler for submitting player turns.
 * @author Michael Kha
 */
public class PostSubmitTurnRoute implements Route {

    /**
     * The game center
     */
    private GameCenter gameCenter;

    /**
     * Interprets and converts json
     */
    private Gson gson;

    /**
     * The constructor for the {@code POST /game} route handler to submit
     * the players turn. A turn consists of a valid move and changing whose
     * turn it is.
     *
     * @param gameCenter holds the ongoing games
     * @param gson used to interpret and convert json
     */
    public PostSubmitTurnRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    /**
     * Handles the request to submit a turn. Submitting a turn may be unsuccessful
     * indicated by the message. @see Game::submitTurn()
     * @param request The HTTP request
     * @param response The HTTP response
     * @return Message, error if turn invalid, info if turn is valid and turn processed.
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Game game = gameCenter.getGame(player);
        return gson.toJson(game.submitTurn());
    }
}

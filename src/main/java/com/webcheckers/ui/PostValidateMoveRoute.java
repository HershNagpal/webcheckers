package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import spark.*;

import java.util.Objects;


/**
 * The {@code POST /game} route handler for validating moves.
 *
 * @author Michael Kha
 */
public class PostValidateMoveRoute implements Route {

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
    public PostValidateMoveRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    /**
     * Handles the request to validate a move.
     * @param request The HTTP request
     * @param response The HTTP response
     * @return Message, error if invalid move, info if valid move
     */
    @Override
    public Object handle(Request request, Response response) {
        String moveJSON = request.body();
        Move move = gson.fromJson(moveJSON, Move.class);
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Game game = gameCenter.getGame(player);
        Message message = gameCenter.validateMove(game, move);
        return gson.toJson(message);
    }

}

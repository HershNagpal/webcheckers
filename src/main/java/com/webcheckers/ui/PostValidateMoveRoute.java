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

    private GameCenter gameCenter;
    private Gson gson;

    public PostValidateMoveRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        String moveJSON = request.body();
        Move move = gson.fromJson(moveJSON, Move.class);
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Game game = gameCenter.getGame(player);
        if (game.validateMove(move)) {
            return gson.toJson(new Message("", MessageType.INFO));
        }
        else {
            return gson.toJson(new Message("Invalid move. Try again.", MessageType.ERROR));
        }
    }

}

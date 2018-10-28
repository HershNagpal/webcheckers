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

    private GameCenter gameCenter;
    private Gson gson;

    public PostSubmitTurnRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Game game = gameCenter.getGame(player);
        return gson.toJson(game.submitTurn());
    }
}

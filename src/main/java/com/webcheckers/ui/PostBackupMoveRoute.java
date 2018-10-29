package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;

public class PostBackupMoveRoute implements Route {

    /**
     * The game center
     */
    private GameCenter gameCenter;

    /**
     * Interprets and converts json
     */
    private Gson gson;

    public PostBackupMoveRoute(GameCenter gameCenter, Gson gson) {
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
        return gson.toJson(game.backUpMove());
    }
}

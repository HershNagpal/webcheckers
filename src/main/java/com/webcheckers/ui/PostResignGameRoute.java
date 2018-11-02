package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
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

    private GameCenter gameCenter;
    private Gson gson;

    public PostResignGameRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Message message = gameCenter.resignGame(player);
        if (message.getType() == MessageType.info) {
            response.redirect(WebServer.HOME_URL);
        }
        return gson.toJson(message);
    }
}

package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Player;
import spark.*;
import java.util.Objects;

/**
 * The route handler for backing up moves.
 * @author Luis Gutierrez
 */
public class PostBackupMoveRoute implements Route {

    /**
     * The game center
     */
    private GameCenter gameCenter;

    /**
     * Interprets and converts json
     */
    private Gson gson;

    /**
     * The constructor for the route handler to back up a move.
     *
     * @param gameCenter holds the ongoing games
     * @param gson used to interpret and convert json
     */
    public PostBackupMoveRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(gson, "gson must not be null");
        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    /**
     * Handles the request to back up a move. Backing up a move may be unsuccessful
     * indicated by the message. @see Game::backUpMove()
     * @param request The HTTP request
     * @param response The HTTP response
     * @return Message, error if backUpMove invalid, info if turn is valid and turn processed.
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Message message = gameCenter.backupMove(player);
        return gson.toJson(message);
    }
}
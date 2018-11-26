package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * The UI Controller to GET the Home page upon a player stop watching a game.
 *
 * @author Michael Kha
 */
public class GetSpectatorStopRoute implements Route {

    /**
     * The player lobby
     */
    private PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request from the spectator page.
     *
     * @param playerLobby
     *   the controller that holds the players for access and storage
     */
    public GetSpectatorStopRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    /**
     * Render the WebCheckers home page upon a player exiting spectator mode.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the home page
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_ATTR);
        // Reset messages on home page
        session.removeAttribute(GetSpectatorGameRoute.MESSAGE_ATTR);
        playerLobby.stopSpectating(player);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
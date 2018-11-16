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
     *
     */
    private PlayerLobby playerLobby;

    /**
     *
     * @param playerLobby
     */
    public GetSpectatorStopRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_ATTR);
        if (player != null) {
            playerLobby.stopSpectating(player);
        }
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}

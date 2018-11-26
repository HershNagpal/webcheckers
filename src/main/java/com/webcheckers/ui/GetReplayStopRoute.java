package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayController;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * The UI Controller to GET the Home page upon a player exiting a replay game.
 *
 * @author Michael Kha
 */
public class GetReplayStopRoute implements Route {

    /**
     * The player lobby
     */
    private PlayerLobby playerLobby;

    /**
     * The replay controller holding finished games and players replaying
     */
    private ReplayController replayController;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request from the replay page.
     *
     * @param playerLobby
     *   the controller that holds the players for access and storage
     * @param replayController holds the finished games
     */
    public GetReplayStopRoute(PlayerLobby playerLobby, ReplayController replayController) {
        this.playerLobby = playerLobby;
        this.replayController = replayController;
    }

    /**
     * Render the WebCheckers home page upon a player exiting replay mode.
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
        session.removeAttribute(GetReplayGameRoute.MESSAGE_ATTR);
        playerLobby.stopReplaying(player);
        replayController.stopReplaying(player);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Home page upon a player signing out.
 *
 * @author Michael Kha
 */
public class GetSignOutRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    /**
     * The player lobby
     */
    private final PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param playerLobby
     *   the controller that holds the players for access and storage
     */
    public GetSignOutRoute(final PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");

        this.playerLobby = playerLobby;

        LOG.config("GetSignOutRoute is initialized.");
    }

    /**
     * Render the WebCheckers home page upon a player signing out.
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignOutRoute is invoked.");

        final Session session = request.session();

        Player player = session.attribute(GetHomeRoute.PLAYER_ATTR);
        if (player != null) {
            // No longer a signed in player; update controller and view
            playerLobby.signOut(player);
            session.removeAttribute(GetHomeRoute.PLAYER_ATTR);
        }
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}

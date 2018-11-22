package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {

    /**
     * Attribute for the title of the page
     */
    static final String TITLE_ATTR = "title";

    /**
     * The actual title of the page
     */
    static final String TITLE = "Welcome!";

    /**
     * Attribute for the number of players
     */
    static final String NUM_PLAYERS_ATTR = "numPlayers";

    /**
     * Attribute for the list of players
     */
    static final String PLAYER_LIST_ATTR = "playerList";

    /**
     * Attribute for the number of ongoing games
     */
    static final String NUM_GAMES_ATTR = "numGames";

    /**
     * Attribute for the list of games
     */
    static final String GAMES_LIST_ATTR = "gamesList";

    /**
     * Attribute for the current player
     */
    static final String PLAYER_ATTR = "currentPlayer";

    /**
     * Attribute for all messages
     */
    static final String MESSAGE_ATTR = "message";

    /**
     * Attribute for the view name
     */
    static final String VIEW_NAME = "home.ftl";

    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    /**
     * The game center
     */
    private final GameCenter gameCenter;

    /**
     * The player lobby
     */
    private final PlayerLobby playerLobby;

    /**
     * Renders the model and view map
     */
    private final TemplateEngine templateEngine;


    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetHomeRoute(final GameCenter gameCenter, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");

        final Session session = request.session();

        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);

        Player player = session.attribute(PLAYER_ATTR);
        if (player != null) {
            if (gameCenter.wasChallenged(player)) {
                response.redirect(WebServer.GAME_URL);
            }
            vm.put(PLAYER_ATTR, player);
            List<String> players = playerLobby.getPlayerLobbyNames(player);
            if (players.size() != 0) {
                vm.put(PLAYER_LIST_ATTR, players);
            }
            if (session.attribute(MESSAGE_ATTR) != null) {
                vm.put(MESSAGE_ATTR, session.attribute(MESSAGE_ATTR));
            }
            if (gameCenter.gamesOngoing()) {
                vm.put(GAMES_LIST_ATTR, gameCenter.getGames());
            }
        }
        else {
            vm.put(NUM_PLAYERS_ATTR, playerLobby.size());
            vm.put(NUM_GAMES_ATTR, gameCenter.size());
        }

        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }

}
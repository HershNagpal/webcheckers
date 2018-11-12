package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Game page
 *
 * @author Michael Kha
 * @author Hersh Nagpal
 * @author Matthew Bollinger
 */
public class GetGameRoute implements Route{

    /**
     * Attribute for the current player
     */
    static final String CURRENT_PLAYER_ATTR = "currentPlayer";

    /**
     * Attribute for the view mode
     */
    static final String VIEW_MODE_ATTR = "viewMode";

    /**
     * Attribute for the red player
     */
    static final String RED_PLAYER_ATTR = "redPlayer";

    /**
     * Attribute for the white player
     */
    static final String WHITE_PLAYER_ATTR = "whitePlayer";

    /**
     * Attribute for the active color
     */
    static final String ACTIVE_COLOR_ATTR = "activeColor";

    /**
     * Attribute for the player's ID denoted by their name
     */
    static final String ID_PARAM = "pid";

    /**
     * Attribute for the board
     */
    static final String BOARD_ATTR = "board";

    /**
     * Attribute for all messages
     */
    static final String MESSAGE_ATTR = "message";

    /**
     * Attribute for the title of the page
     */
    static final String TITLE_ATTR = "title";

    /**
     * Attribute for the view name
     */
    static final String VIEW_NAME = "game.ftl";

    /**
     * The actual title of the page
     */
    static final String TITLE = "Game Board";

    /**
     * Message to display when trying to start a game with
     * a player that is already in a game.
     */
    static final Message IN_GAME_ERROR = new Message(
            "That player is already in game!", MessageType.error);

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    /**
     * The game center that holds games and handles actions
     */
    private final GameCenter gameCenter;

    /**
     * The player lobby holding all signed-in players
     */
    private final PlayerLobby playerLobby;

    /**
     * The template engine used to render the page
     */
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     * the HTML template rendering engine
     */
    public GetGameRoute (final GameCenter gameCenter, final PlayerLobby playerLobby, final TemplateEngine templateEngine){
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(templateEngine, "playerLobby must not be null");

        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;

        LOG.config("GetGameRoute is initialized");
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request
     *  the HTTP request
     * @param response
     *  the HTTP response
     *
     * @return
     *  the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetGameRoute is invoked");
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        Session session = request.session();
        // Reset game messages
        session.removeAttribute(MESSAGE_ATTR);
        Game game;
        Player player = session.attribute(CURRENT_PLAYER_ATTR);
        // Is this player in game
        if (gameCenter.playerInGame(player)) {
            game = gameCenter.getGame(player);
        } else {
            Player opponent = playerLobby.getPlayer(request.queryParams(ID_PARAM));
            // Is other player in game
            if (gameCenter.playerInGame(opponent)) {
                session.attribute(MESSAGE_ATTR, IN_GAME_ERROR);
                response.redirect(WebServer.HOME_URL);
                return null;
            }
            // Create a new game
            game = gameCenter.createGame(player, opponent);
        }

        if (gameCenter.isGameOver(game)) {
            Message message = gameCenter.isWinner(game, player);
            session.attribute(MESSAGE_ATTR, message);
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        vm.put(BOARD_ATTR, game.getBoardView(player));
        vm.put(CURRENT_PLAYER_ATTR, player);
        vm.put(VIEW_MODE_ATTR, ViewMode.PLAY);
        vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
        vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}

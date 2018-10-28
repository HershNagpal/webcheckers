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
 */
public class GetGameRoute implements Route{

    //values used for rendering signin view
    static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String GAME_ATTR = "game";
    static final String ID_PARAM = "pid";
    static final String TITLE_ATTR = "title";
    static final String VIEW_NAME = "game.ftl";
    static final String BOARD_ATTR = "board";
    static final String MESSAGE_ATTR = "message";
    static final String IN_GAME_ERROR = "That player is already in game!";

    static final String TITLE = "Game Board";

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private final GameCenter gameCenter;
    private final PlayerLobby playerLobby;
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

        Game game;
        BoardView boardView;
        Board board;
        Player player = session.attribute(CURRENT_PLAYER_ATTR);
        // Is this player in game
        if (gameCenter.playerInGame(player)) {
            game = gameCenter.getGame(player);
            if (session.attribute(GAME_ATTR) == null) {
                session.attribute(GAME_ATTR, game);
            }
        } else {
            Player opponent = playerLobby.getPlayer(request.queryParams(ID_PARAM));
            // Is other player in game
            if (gameCenter.playerInGame(opponent)) {
                session.attribute(MESSAGE_ATTR, new Message(IN_GAME_ERROR, MessageType.ERROR));
                response.redirect(WebServer.HOME_URL);
                return null;
            }
            // Create a new game
            game = gameCenter.createGame(player, opponent);
            session.attribute(GAME_ATTR, game);
        }
        board = game.getBoard();
        if (game.isRedPlayer(player)) {
            boardView = board.getFlippedBoardView();
        } else {
            boardView = board.getBoardView();
        }

        vm.put(BOARD_ATTR, boardView);
        vm.put(CURRENT_PLAYER_ATTR, player);
        vm.put(VIEW_MODE_ATTR, ViewMode.PLAY);
        vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
        vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}

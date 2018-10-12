package com.webcheckers.ui;

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
    static final String TITLE_ATTR = "title";
    static final String VIEW_NAME = "game.ftl";
    static final String BOARD_ATTR = "board";
    static final String MESSAGE_ATTR = "message";
    static final String IN_GAME_ERROR = "That player is already in game!";

    static final String TITLE = "Game Board";

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    private BoardView boardView;
    private Board gameBoard;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     * the HTML template rendering engine
     */
    public GetGameRoute (final PlayerLobby playerLobby, final TemplateEngine templateEngine){
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(templateEngine, "playerLobby must not be null");

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
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        Session session = request.session();

        Game game;
        Player playerOne = session.attribute(CURRENT_PLAYER_ATTR);
        if (playerOne.getGame() != null) {
            game = playerOne.getGame();
        }
        else {
            // URL example: http://localhost:4567/game?pid=kid
            Player playerTwo = playerLobby.getPlayer(request.queryParams("pid"));
            System.out.println(playerTwo.getName());
            // Cannot create game with a player in game
            if (playerTwo.getGame() != null) {
                session.attribute(MESSAGE_ATTR, new Message(IN_GAME_ERROR, Message.MessageType.ERROR));
                response.redirect(WebServer.HOME_URL);
                return null;
            }

            gameBoard = new Board();
            boardView = gameBoard.getBoardView();
            game = new Game(playerOne, playerTwo, gameBoard);

        }
        vm.put(BOARD_ATTR, boardView);
        vm.put(CURRENT_PLAYER_ATTR, playerOne);
        vm.put(VIEW_MODE_ATTR, ViewMode.PLAY);
        vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
        vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());


        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}

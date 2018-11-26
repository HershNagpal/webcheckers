package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayController;
import com.webcheckers.model.*;
import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The UI Controller to GET the Game page in replay mode
 *
 * @author Michael Kha
 */
public class GetReplayGameRoute implements Route {

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
    static final String ID_PARAM = "gameID";

    /**
     * Attribute for the board
     */
    static final String BOARD_ATTR = "board";

    /**
     * Attribute for the mode options map
     */
    static final String MODE_OPTIONS_ATTR = "modeOptionsAsJSON";

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
    static final String TITLE = "Replaying Game";

    /**
     * Message to display when leaving the spectator game page
     * without exiting.
     */
    static final Message REPLAY_INFO = new Message(
            "You are replaying still. Return to the game and exit.", MessageType.info);

    /**
     * The replay controller holding finished games and players replaying
     */
    private final ReplayController replayController;

    /**
     * The player lobby holding all signed-in players
     */
    private final PlayerLobby playerLobby;

    /**
     * The template engine used to render the page
     */
    private final TemplateEngine templateEngine;

    /**
     * Interprets and converts json
     */
    private Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /spectator/game} HTTP request.
     * @param replayController the controller that holds finished games
     * @param playerLobby the controller that holds the players for access and storage
     * @param templateEngine the HTML template rendering engine
     * @param gson used to interpret and convert json
     */
    public GetReplayGameRoute(ReplayController replayController, PlayerLobby playerLobby, TemplateEngine templateEngine, Gson gson) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(replayController, "replayController must not be null");

        this.replayController = replayController;
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        this.gson = gson;

    }

    /**
     * Render the WebCheckers Game page in replay mode
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Game page in replay mode
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        session.attribute(MESSAGE_ATTR, REPLAY_INFO);
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        String gameID = request.queryParams(ID_PARAM);
        gameID = String.join("+", gameID.split(" "));
        Player currentPlayer = session.attribute(CURRENT_PLAYER_ATTR);
        playerLobby.startReplaying(currentPlayer);
        Replay game = replayController.getReplayGame(currentPlayer, gameID);
        vm.put(CURRENT_PLAYER_ATTR, currentPlayer);
        vm.put(VIEW_MODE_ATTR, ViewMode.REPLAY);
        Player red = game.getRedPlayer();
        Player white = game.getWhitePlayer();
        if (game.isActivePlayer(red)) {
            vm.put(BOARD_ATTR, game.getBoardView(red));
        }
        else {
            vm.put(BOARD_ATTR, game.getBoardView(white));
        }
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());
        vm.put(RED_PLAYER_ATTR, red);
        vm.put(WHITE_PLAYER_ATTR, white);
        vm.put(MODE_OPTIONS_ATTR, gson.toJson(game.getModeOptions()));

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

}
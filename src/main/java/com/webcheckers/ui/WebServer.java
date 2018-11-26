package com.webcheckers.ui;

import static spark.Spark.*;
import java.util.Objects;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayController;
import spark.TemplateEngine;


/**
 * The server that initializes the set of HTTP request handlers.
 * This defines the <em>web application interface</em> for this
 * WebCheckers application.
 *
 * <p>
 * There are multiple ways in which you can have the client issue a
 * request and the application generate responses to requests. If your team is
 * not careful when designing your approach, you can quickly create a mess
 * where no one can remember how a particular request is issued or the response
 * gets generated. Aim for consistency in your approach for similar
 * activities or requests.
 * </p>
 *
 * <p>Design choices for how the client makes a request include:
 * <ul>
 *     <li>Request URL</li>
 *     <li>HTTP verb for request (GET, POST, PUT, DELETE and so on)</li>
 *     <li><em>Optional:</em> Inclusion of request parameters</li>
 * </ul>
 * </p>
 *
 * <p>Design choices for generating a response to a request include:
 * <ul>
 *     <li>View templates with conditional elements</li>
 *     <li>Use different view templates based on results of executing the client request</li>
 *     <li>Redirecting to a different application URL</li>
 * </ul>
 * </p>
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class WebServer {
    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

    //
    // Constants
    //

    /**
     * The URL pattern to request the Home page.
     */
    public static final String HOME_URL = "/";

    /**
     * The URL pattern to request the Sign-In page.
     */
    public static final String SIGNIN_URL = "/signin";

    /**
     * The URL pattern to request a sign-out action.
     */
    public static final String SIGNOUT_URL = "/signout";

    /**
     * The URL pattern to request the Game page.
     */
    public static final String GAME_URL = "/game";

    /**
     * The URL pattern to request a check turn action.
     */
    public static final String CHECK_TURN_URL = "/checkTurn";

    /**
     * The URL pattern to request a validate move action.
     */
    public static final String VALIDATE_MOVE_URL = "/validateMove";

    /**
     * The URL pattern to request a submit turn action.
     */
    public static final String SUBMIT_TURN_URL = "/submitTurn";

    /**
     * The URL pattern to request a backup move action.
     */
    public static final String BACKUP_MOVE_URL = "/backupMove";

    /**
     * The URL pattern to request a resign game action.
     */
    public static final String RESIGN_URL = "/resignGame";

    /**
     * The URL pattern to request the spectator game page.
     */
    public static final String SPECTATE_GAME_URL = "/spectator/game";

    /**
     * The URL pattern to request the stop spectating action.
     */
    public static final String SPECTATE_STOP_URL = "/spectator/stopWatching";

    /**
     * The URL pattern to request a check turn action in spectator mode.
     */
    public static final String SPECTATE_CHECK_TURN_URL = "/spectator/checkTurn";

    /**
     * The URL pattern to request the replay game page.
     */
    public static final String REPLAY_GAME_URL = "/replay/game";

    /**
     * The URL pattern to request the stop watching replay action.
     */
    public static final String REPLAY_STOP_URL = "/replay/stopWatching";

    /**
     * The URL pattern to request the replay to go to the next turn.
     */
    public static final String REPLAY_NEXT_TURN_URL = "/replay/nextTurn";

    /**
     * The URL pattern to request the replay to go to the previous turn.
     */
    public static final String REPLAY_PREV_TURN_URL = "/replay/previousTurn";

    //
    // Attributes
    //

    /**
     * The game center that holds games and handles actions
     */
    private final GameCenter gameCenter;

    /**
     * The player lobby holding all signed-in players
     */
    private final PlayerLobby playerLobby;

    /**
     * The replay controller holding finished games and players replaying
     */
    private final ReplayController replayController;

    /**
     * The template engine used to render the page
     */
    private final TemplateEngine templateEngine;

    /**
     * Used to communicate with json
     */
    private final Gson gson;

    //
    // Constructor
    //

    /**
     * The constructor for the Web Server.
     *
     * @param templateEngine
     *    The default {@link TemplateEngine} to render page-level HTML views.
     * @param gson
     *    The Google JSON parser object used to render Ajax responses.
     *
     * @throws NullPointerException
     *    If any of the parameters are {@code null}.
     */
    public WebServer(final GameCenter gameCenter, final PlayerLobby playerLobby,
                     final ReplayController replayController, final TemplateEngine templateEngine, final Gson gson) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
        this.replayController = replayController;
        this.templateEngine = templateEngine;
        this.gson = gson;
    }

    //
    // Public methods
    //

    /**
     * Initialize all of the HTTP routes that make up this web application.
     *
     * <p>
     * Initialization of the web server includes defining the location for static
     * files, and defining all routes for processing client requests. The method
     * returns after the web server finishes its initialization.
     * </p>
     */
    public void initialize() {

        // Configuration to serve static files
        staticFileLocation("/public");

        //// Setting any route (or filter) in Spark triggers initialization of the
        //// embedded Jetty web server.

        //// A route is set for a request verb by specifying the path for the
        //// request, and the function callback (request, response) -> {} to
        //// process the request. The order that the routes are defined is
        //// important. The first route (request-path combination) that matches
        //// is the one which is invoked. Additional documentation is at
        //// http://sparkjava.com/documentation.html and in Spark tutorials.

        //// Each route (processing function) will check if the request is valid
        //// from the client that made the request. If it is valid, the route
        //// will extract the relevant data from the request and pass it to the
        //// application object delegated with executing the request. When the
        //// delegate completes execution of the request, the route will create
        //// the parameter map that the response template needs. The data will
        //// either be in the value the delegate returns to the route after
        //// executing the request, or the route will query other application
        //// objects for the data needed.

        //// FreeMarker defines the HTML response using templates. Additional
        //// documentation is at
        //// http://freemarker.org/docs/dgui_quickstart_template.html.
        //// The Spark FreeMarkerEngine lets you pass variable values to the
        //// template via a map. Additional information is in online
        //// tutorials such as
        //// http://benjamindparrish.azurewebsites.net/adding-freemarker-to-java-spark/.

        //// These route definitions are examples. You will define the routes
        //// that are appropriate for the HTTP client interface that you define.
        //// Create separate Route classes to handle each route; this keeps your
        //// code clean; using small classes.

        // Shows the Checkers game Home page.
        get(HOME_URL, new GetHomeRoute(gameCenter, playerLobby, templateEngine));

        // Shows the Checkers game Sign-In page.
        get(SIGNIN_URL, new GetSignInRoute(templateEngine));

        // Posts the user's input for names for validation
        post(SIGNIN_URL, new PostSignInRoute(playerLobby, templateEngine));

        // Gets the user's request to sign out
        get(SIGNOUT_URL, new GetSignOutRoute(playerLobby));

        // Gets the Checkers game Game page that the player is in
        get(GAME_URL, new GetGameRoute(gameCenter, playerLobby, templateEngine));

        // Checks the turn for a given player and posts
        post(CHECK_TURN_URL, new PostCheckTurnRoute(gameCenter, gson));

        // Validates the move by a player and posts
        post(VALIDATE_MOVE_URL, new PostValidateMoveRoute(gameCenter, gson));

        // Submits the move made this turn by the player and posts
        post(SUBMIT_TURN_URL, new PostSubmitTurnRoute(gameCenter, gson));

        // Backup the move made this turn by the player and posts
        post(BACKUP_MOVE_URL, new PostBackupMoveRoute(gameCenter, gson));

        // Resign the game for a player and posts
        post(RESIGN_URL, new PostResignGameRoute(gameCenter, gson));

        // Gets the Checkers game Game page that the player is spectating
        get(SPECTATE_GAME_URL, new GetSpectatorGameRoute(gameCenter, playerLobby, templateEngine));

        // Gets the user's request to stop watching the spectated game
        get(SPECTATE_STOP_URL, new GetSpectatorStopRoute(playerLobby));

        // Checks the turn of a spectated game
        post(SPECTATE_CHECK_TURN_URL, new PostSpectatorCheckTurnRoute(gameCenter, gson));

        // Gets the Checkers game Game page that the player is replaying
        get(REPLAY_GAME_URL, new GetReplayGameRoute(replayController, playerLobby, templateEngine, gson));

        // Gets the user's request to stop watching the game replay
        get(REPLAY_STOP_URL, new GetReplayStopRoute(playerLobby, replayController));

        // Moves the replayed game's state to the next turn
        post(REPLAY_NEXT_TURN_URL, new PostReplayNextRoute(replayController, gson));

        // Moves the replayed game's state to the previous turn
        post(REPLAY_PREV_TURN_URL, new PostReplayPrevRoute(replayController, gson));

        LOG.config("WebServer is initialized.");
    }

}
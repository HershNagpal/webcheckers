package com.webcheckers.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {

  static final String TITLE_ATTR = "title";
  static final String TITLE = "Welcome!";
  static final String NUM_PLAYERS_ATTR = "numPlayers";
  static final String PLAYER_LIST_ATTR = "playerList";
  static final String PLAYER_ATTR = "currentPlayer";
  static final String MESSAGE_ATTR = "message";
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
      if (gameCenter.playerInGame(player)) {
        response.redirect(WebServer.GAME_URL);
      }
      vm.put(PLAYER_ATTR, player);
      List<String> players = playerLobby.getPlayerLobbyNames(player);
      if (players.size() != 0) {
        vm.put(PLAYER_LIST_ATTR, players);
      }
      if (session.attribute(MESSAGE_ATTR) != null) {
        vm.put(MESSAGE_ATTR, session.attribute(MESSAGE_ATTR));
        session.removeAttribute(MESSAGE_ATTR);
      }
    }
    else {
      vm.put(NUM_PLAYERS_ATTR, playerLobby.size());
    }

    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }

}
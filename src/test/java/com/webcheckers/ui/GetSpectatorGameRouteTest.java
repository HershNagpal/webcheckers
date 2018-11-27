package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.MessageType;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for GetSpectatorGameRoute class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class GetSpectatorGameRouteTest {

    /**
     * Component under test
     */
    private GetSpectatorGameRoute CuT;

    /**
     * friendly objects
     */
    private Player p1;
    private Player p2;
    private Game game;

    /**
     * mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private TemplateEngine engine;
    private Player spectator;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);
        //playerLobby = new PlayerLobby();
        engine = mock(TemplateEngine.class);
        spectator = mock(Player.class);
        p1 = new Player("1");
        p2 = new Player("2");
        game = new Game(p1, p2, 0);
        when(gameCenter.createGame(p1, p2)).thenReturn(game);

        String gameID = p1.getName() + "+" + p2.getName();
        when(request.queryParams(GetSpectatorGameRoute.ID_PARAM)).thenReturn(gameID);
        when(session.attribute(GetSpectatorGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(spectator);
        when(gameCenter.getGame(gameID)).thenReturn(game);

        CuT = new GetSpectatorGameRoute(gameCenter, playerLobby, engine);
    }

    /**
     * Test that the view model attributes are populated during red's turn
     */
    @Test
    public void testSpectatorViewModelRedTurn() {
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        // Invoke test
        CuT.handle(request, response);

        tester.assertViewModelAttribute(GetSpectatorGameRoute.CURRENT_PLAYER_ATTR, spectator);
        tester.assertViewModelAttribute(GetSpectatorGameRoute.VIEW_MODE_ATTR, ViewMode.SPECTATOR);
        tester.assertViewModelAttribute(GetSpectatorGameRoute.BOARD_ATTR, game.getBoardView(p1));
        tester.assertViewModelAttribute(GetSpectatorGameRoute.ACTIVE_COLOR_ATTR, game.getActiveColor());
        tester.assertViewModelAttribute(GetSpectatorGameRoute.RED_PLAYER_ATTR, p1);
        tester.assertViewModelAttribute(GetSpectatorGameRoute.WHITE_PLAYER_ATTR, p2);
        tester.assertViewModelAttribute(GetSpectatorGameRoute.TITLE_ATTR, GetSpectatorGameRoute.TITLE);
    }

    /**
     * Test that the view model attributes are populated during white's turn
     */
    @Test
    public void testSpectatorViewModelWhiteTurn() {
        // Create the test scenario
        game.switchActiveColor();
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        // Invoke test
        CuT.handle(request, response);

        tester.assertViewModelAttribute(GetSpectatorGameRoute.CURRENT_PLAYER_ATTR, spectator);
        tester.assertViewModelAttribute(GetSpectatorGameRoute.VIEW_MODE_ATTR, ViewMode.SPECTATOR);
        tester.assertViewModelAttribute(GetSpectatorGameRoute.BOARD_ATTR, game.getBoardView(p2));
        tester.assertViewModelAttribute(GetSpectatorGameRoute.ACTIVE_COLOR_ATTR, game.getActiveColor());
        tester.assertViewModelAttribute(GetSpectatorGameRoute.RED_PLAYER_ATTR, p1);
        tester.assertViewModelAttribute(GetSpectatorGameRoute.WHITE_PLAYER_ATTR, p2);
        tester.assertViewModelAttribute(GetSpectatorGameRoute.TITLE_ATTR, GetSpectatorGameRoute.TITLE);
    }

    /**
     * Test that the route redirects home when the game ends
     */
    @Test
    public void testGameEndRedirect() {
        // Create the test scenario
        when(gameCenter.isGameOver(game)).thenReturn(true);
        Message message = new Message("test", MessageType.info);
        when(gameCenter.whoWon(game)).thenReturn(message);
        // Invoke test
        CuT.handle(request, response);

        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Test that the route redirects home when the game is not found
     */
    @Test
    public void testGameNotFoundRedirect() {
        // Create the test scenario
        game = null;
        String gameID = p1.getName() + "+" + p2.getName();
        when(gameCenter.getGame(gameID)).thenReturn(game);
        // Invoke test
        CuT.handle(request, response);

        verify(response).redirect(WebServer.HOME_URL);
    }
}
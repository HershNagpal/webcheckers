package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The unit test class for {@link GetGameRoute} componet.
 *
 * @author Matthew Bollinger mxb9771@rit.edu
 * @author Michael Kha
 */
@Tag("UI-tier")
public class GetGameRouteTest {

    /**
     * Component under test
     */
    private GetGameRoute CuT;

    /**
     * friendly objects
     */
    private Color activeColor;

    /**
     * mock objects
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player p1;
    private Player p2;
    private GameCenter gameCenter;
    private Game game;
    private BoardView boardView;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        p1 = mock(Player.class);
        p2 = mock(Player.class);
        gameCenter = mock(GameCenter.class);
        game = mock(Game.class);
        boardView = mock(BoardView.class);
        activeColor = Color.RED;
        String gameID = p1.getName() + " " + p2.getName();
        when(request.queryParams(GetGameRoute.ID_PARAM)).thenReturn(gameID);
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(p1);
        when(playerLobby.getPlayer(gameID.split(" ")[1])).thenReturn(p2);
        when(gameCenter.createGame(p1, p2)).thenReturn(game);
        when(game.getBoardView(p1)).thenReturn(boardView);
        when(game.getBoardView(p2)).thenReturn(boardView);
        when(game.getRedPlayer()).thenReturn(p1);
        when(game.getWhitePlayer()).thenReturn(p2);
        when(game.getActiveColor()).thenReturn(activeColor);

        CuT = new GetGameRoute(gameCenter, playerLobby, engine);
    }

    /**
     * Test that if you attempt to start a game with a player who is already in
     * game then you should be redirected to home with a message.
     */
    @Test
    public void testPlayerInGame() {
        // Player one is not in a game, player two is in a game
        when(gameCenter.playerInGame(p1)).thenReturn(false);
        when(gameCenter.playerInGame(p2)).thenReturn(true);
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        // Invoke test
        CuT.handle(request, response);
        // Verify that user is sent back to home
        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Test that if you attempt to start a game with a player who is spectating
     * a game then you should be redirected to home with a message.
     */
    @Test
    public void testPlayerSpectating() {
        // Player one is not in a game, player two is in a game
        when(gameCenter.playerInGame(p1)).thenReturn(false);
        when(gameCenter.playerInGame(p2)).thenReturn(false);
        when(playerLobby.isSpectating(p2)).thenReturn(true);
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        // Invoke test
        CuT.handle(request, response);
        // Verify that user is sent back to home
        verify(response).redirect(WebServer.HOME_URL);
    }


    /**
     * Test if a new game is created automatically when both players
     * are not in game.
     */
    @Test
    public void newGame(){
        // Both players are not in a game
        when(gameCenter.playerInGame(p1)).thenReturn(false);
        when(gameCenter.playerInGame(p2)).thenReturn(false);

        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        // Invoke the test
        CuT.handle(request,response);
        // Make sure engine is setup
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        // Make view model is populated correctly
        tester.assertViewModelAttributeIsPresent(GetGameRoute.BOARD_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.VIEW_MODE_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.CURRENT_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.RED_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.WHITE_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.ACTIVE_COLOR_ATTR);
    }

    /**
     * Test if a player already is already in a game they rejoin their game.
     */
    @Test
    public void rejoinGame(){
        // Player one is in a game
        when(gameCenter.playerInGame(p1)).thenReturn(true);
        when(gameCenter.getGame(p1)).thenReturn(game);

        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        // Invoke test
        CuT.handle(request, response);
        // Make sure engine is setup
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        // Make sure view model is populated correctly
        tester.assertViewModelAttributeIsPresent(GetGameRoute.BOARD_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.VIEW_MODE_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.CURRENT_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.RED_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.WHITE_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.ACTIVE_COLOR_ATTR);
    }

    /**
     * Test when the game is over, the right message is displayed to the winner.
     */
    @Test
    public void testGameOverWinner() {
        // Create scenario: player one in a game where the game is over
        when(gameCenter.playerInGame(p1)).thenReturn(true);
        when(gameCenter.getGame(p1)).thenReturn(game);
        when(gameCenter.isGameOver(game)).thenReturn(true);

        // Invoke the test
        CuT.handle(request, response);

        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Test when the game is over, the right message is displayed to the loser.
     */
    @Test
    public void testGameOverLoser() {
        // Create scenario: player two in a game where the game is over
        when(gameCenter.playerInGame(p2)).thenReturn(true);
        when(gameCenter.getGame(p2)).thenReturn(game);
        when(gameCenter.isGameOver(game)).thenReturn(true);

        // Invoke the test
        CuT.handle(request, response);

        verify(response).redirect(WebServer.HOME_URL);
    }

}

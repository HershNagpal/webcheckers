package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the GetHomeRoute class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class GetHomeRouteTest {

    // component under test
    private GetHomeRoute CuT;

    // friendly objects
    private PlayerLobby playerLobby;

    // mock objects
    private Request request;
    private Session session;
    private TemplateEngine templateEngine;
    private Response response;
    private Player player;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        player = mock(Player.class);

        playerLobby = new PlayerLobby();
        CuT = new GetHomeRoute(playerLobby, templateEngine);
    }

    /**
     * Test when a new session enters the home page.
     */
    @Test
    public void testNewSession() {
        // Arrange test scenario: the player is not logged in yet
        when(session.attribute(GetHomeRoute.PLAYER_ATTR)).thenReturn(null);
        // Mock up the view model
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        // Check view model is initialized
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Check view model has the necessary data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.NUM_PLAYERS_ATTR, playerLobby.size());
        // Check view model does not have certain data
        testHelper.assertViewModelAttributeIsAbsent(GetHomeRoute.PLAYER_ATTR);
        testHelper.assertViewModelAttributeIsAbsent(GetHomeRoute.PLAYER_LIST_ATTR);
        testHelper.assertViewModelAttributeIsAbsent(GetHomeRoute.MESSAGE_ATTR);
        // Check view name
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    /**
     * Test when a session is logged in.
     */
    @Test
    public void testPlayerSession() {
        // Arrange test scenario: the player is logged in
        when(session.attribute(GetHomeRoute.PLAYER_ATTR)).thenReturn(player);
        // Mock up the view model
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        playerLobby.signIn(player);
        // Check view model is initialized
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Check view model has the necessary data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, session.attribute(GetHomeRoute.MESSAGE_ATTR));
        // Check view model does not have certain data
        testHelper.assertViewModelAttributeIsAbsent(GetHomeRoute.PLAYER_LIST_ATTR);
        testHelper.assertViewModelAttributeIsAbsent(GetHomeRoute.NUM_PLAYERS_ATTR);
        // Check view name
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    /**
     * Test when there are multiple players logged in.
     */
    @Test
    public void testMultiplePlayerSessions() {
        // Arrange test scenario: the player is logged in
        when(session.attribute(GetHomeRoute.PLAYER_ATTR)).thenReturn(player);
        when(session.attribute(GetHomeRoute.PLAYER_LIST_ATTR)).thenReturn(playerLobby.getPlayerLobbyNames(player));
        // Mock up the view model
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        playerLobby.signIn(mock(Player.class));
        // Invoke the test
        CuT.handle(request, response);
        // Check view model is initialized
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Check view model has the necessary data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST_ATTR, playerLobby.getPlayerLobbyNames(player));
        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, session.attribute(GetHomeRoute.MESSAGE_ATTR));
        // Check view model does not have certain data
        testHelper.assertViewModelAttributeIsAbsent(GetHomeRoute.NUM_PLAYERS_ATTR);
        // Check view name
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    /**
     * Test when a player tries to start a game with a player already in-game
     */
    @Test
    public void testErrorMessage() {
        // Arrange test scenario: the player attempts to start a game
        when(session.attribute(GetHomeRoute.PLAYER_ATTR)).thenReturn(player);
        when(session.attribute(GetHomeRoute.MESSAGE_ATTR)).thenReturn(GetGameRoute.IN_GAME_ERROR);
        // Mock up the view model
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        // Check view model is initialized
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Check view model has the necessary data
        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, session.attribute(GetHomeRoute.MESSAGE_ATTR));
    }

    /**
     * Test when a player goes to the home page but is already signed in and is in a game
     */
    @Test
    public void testInAGame() {
        // Arrange test scenario: the player joins but is already in game
        when(session.attribute(GetHomeRoute.PLAYER_ATTR)).thenReturn(player);
        when(player.getGame()).thenReturn(mock(Game.class));
        // Mock up the view model
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        // Verify the player is redirected to the game from the home page
        verify(response).redirect(WebServer.GAME_URL);
    }

}

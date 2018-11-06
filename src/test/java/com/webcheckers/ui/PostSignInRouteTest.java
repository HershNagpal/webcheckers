package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the PostSignInRoute class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class PostSignInRouteTest {

    private static final String PLAYER_NAME = "testing";

    /**
     * Component under test
     */
    private PostSignInRoute CuT;

    /**
     * friendly objects
     */
    private PlayerLobby playerLobby;
    private Player player;

    /**
     * mock objects
     */
    private TemplateEngine templateEngine;
    private Session session;
    private Request request;
    private Response response;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);
        request = mock(Request.class);
        response = mock(Response.class);

        playerLobby = new PlayerLobby();
        player = new Player(PLAYER_NAME);
        CuT = new PostSignInRoute(playerLobby, templateEngine);
    }

    /**
     * Test when a user tries to sign in with an invalid username.
     */
    @Test
    public void testInvalidNameMessage() {
        // Arrange test scenario
        when(request.queryParams(PostSignInRoute.NAME_PARAM)).thenReturn("Invalid!!!");
        // Mock up the view model
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        // Check view model is initialized
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Check view model has the necessary data
        testHelper.assertViewModelAttribute(PostSignInRoute.TITLE_ATTR, PostSignInRoute.TITLE);
        testHelper.assertViewModelAttribute(PostSignInRoute.MESSAGE_ATTR, PostSignInRoute.INVALID_NAME);
        // Check view model does not have certain data
        testHelper.assertViewModelAttributeIsAbsent(PostSignInRoute.PLAYER_ATTR);
        // Check view name
        testHelper.assertViewName(PostSignInRoute.VIEW_NAME);
    }

    /**
     * Test when a user tries to sign in with an already taken username.
     */
    @Test
    public void testTakenNameMessage() {
        // Arrange test scenario
        when(request.queryParams(PostSignInRoute.NAME_PARAM)).thenReturn(PLAYER_NAME);
        playerLobby.signIn(player);
        // Mock up the view model
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        // Invoke the test
        CuT.handle(request, response);
        // Check view model is initialized
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Check view model has the necessary data
        testHelper.assertViewModelAttribute(PostSignInRoute.TITLE_ATTR, PostSignInRoute.TITLE);
        testHelper.assertViewModelAttribute(PostSignInRoute.MESSAGE_ATTR, PostSignInRoute.NAME_TAKEN);
        // Check view model does not have certain data
        testHelper.assertViewModelAttributeIsAbsent(PostSignInRoute.PLAYER_ATTR);
        // Check view name
        testHelper.assertViewName(PostSignInRoute.VIEW_NAME);
    }

    /**
     * Test when a user successfully signs in.
     */
    @Test
    public void testSignInSuccessful() {
        // Arrange test scenario
        when(request.session()).thenReturn(session);
        when(request.queryParams(PostSignInRoute.NAME_PARAM)).thenReturn(PLAYER_NAME);
        // Invoke the test
        int unexpected = playerLobby.size();
        CuT.handle(request, response);
        int actual = playerLobby.size();
        // Check that the sign in was successful
        assertNotEquals(unexpected, actual);
        // Verify that the player is redirected to the home page
        verify(response).redirect(WebServer.HOME_URL);
    }


}

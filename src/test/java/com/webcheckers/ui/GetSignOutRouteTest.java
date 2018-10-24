package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the GetSignOutRoute class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class GetSignOutRouteTest {

    private static final String PLAYER_NAME = "testing";

    /**
     * Component under test
     */
    private GetSignOutRoute CuT;

    /**
     * friendly objects
     */
    private PlayerLobby playerLobby;
    private Player player;

    /**
     * mock objects
     */
    private Request request;
    private Session session;
    private Response response;


    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);

        response = mock(Response.class);
        playerLobby = new PlayerLobby();
        player = new Player(PLAYER_NAME);
        CuT = new GetSignOutRoute(playerLobby);
    }

    /**
     * Test that the player is signed out. The view and controller should update.
     */
    @Test
    public void testSignOut() {
        // Arrange test scenario: the player is signed in
        when(request.session()).thenReturn(session);
        when(session.attribute(GetHomeRoute.PLAYER_ATTR)).thenReturn(player);
        playerLobby.signIn(player);
        int unexpected = playerLobby.size();
        // Invoke the test
        CuT.handle(request, response);
        int actual = playerLobby.size();
        assertTrue(playerLobby.isValidUsername(PLAYER_NAME));
        assertNotEquals(unexpected, actual);
    }

    /**
     * Test that a not signed in user does nothing to the player lobby.
     */
    @Test
    public void testSignOutNoPlayer() {
        // Arrange test scenario: the player is signed in
        when(request.session()).thenReturn(session);
        when(session.attribute(GetHomeRoute.PLAYER_ATTR)).thenReturn(null);
        playerLobby.signIn(player);
        int expected = playerLobby.size();
        // Invoke the test
        CuT.handle(request, response);
        int actual = playerLobby.size();
        assertEquals(expected, actual);
    }

}

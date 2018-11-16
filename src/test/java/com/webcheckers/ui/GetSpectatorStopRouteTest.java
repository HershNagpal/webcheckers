package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for GetSpectatorStopRoute class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class GetSpectatorStopRouteTest {

    /**
     * Component under test
     */
    private GetSpectatorStopRoute CuT;

    /**
     * friendly objects
     */
    private PlayerLobby playerLobby;

    /**
     * mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private Player player;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        player = mock(Player.class);

        CuT = new GetSpectatorStopRoute(playerLobby);
    }

    /**
     * Test that the response redirects to the home page.
     */
    @Test
    public void testStopSpectating() {
        playerLobby.startSpectating(player);
        when(session.attribute(GetHomeRoute.PLAYER_ATTR)).thenReturn(player);
        CuT.handle(request, response);
        verify(response).redirect(WebServer.HOME_URL);
    }


}

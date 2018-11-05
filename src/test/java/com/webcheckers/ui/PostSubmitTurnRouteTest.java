package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Messenger;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for PostSubmitTurnRoute
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class PostSubmitTurnRouteTest {

    /**
     * Component under test
     */
    private PostSubmitTurnRoute CuT;

    /**
     * friendly objects
     */
    private Game game;
    private GameCenter gameCenter;

    /**
     * mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private Player player;
    private Messenger messenger;

    /**
     * Setup the objects for each test
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        player = mock(Player.class);
        Player opponent = mock(Player.class);
        messenger = mock(Messenger.class);

        gameCenter = new GameCenter(messenger);
        game = gameCenter.createGame(player, opponent);
        Gson gson = new Gson();

        CuT = new PostSubmitTurnRoute(gameCenter, gson);
    }

    /**
     * Validate that the message is correct for when submitting the turn
     * is successful.
     */
    @Test
    public void testSubmitSuccessful() {
        Message message = new Message("", MessageType.info);
        // Arrange the test scenario; the player checks the turn and it is their turn
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
        when(messenger.submitTurn(game)).thenReturn(message);
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gameCenter.submitTurn(player).getText(), message.getText());
        assertEquals(gameCenter.submitTurn(player).getType(), message.getType());
    }

    /**
     * Validate that the message is correct for when submitting the turn fails.
     */
    @Test
    public void testSubmitFailed() {
        Message message = new Message("Invalid move. Cannot submit turn.", MessageType.info);
        // Arrange the test scenario; the player checks the turn and it is their turn
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
        when(messenger.submitTurn(game)).thenReturn(message);
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gameCenter.submitTurn(player).getText(), message.getText());
        assertEquals(gameCenter.submitTurn(player).getType(), message.getType());
    }
}

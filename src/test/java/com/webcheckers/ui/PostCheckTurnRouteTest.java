package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Messenger;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.MessageType;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for PostCheckTurnRoute
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class PostCheckTurnRouteTest {

    /**
     * Component under test
     */
    private PostCheckTurnRoute CuT;

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
    private Player opponent;
    private Messenger messenger;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        player = mock(Player.class);
        opponent = mock(Player.class);
        messenger = mock(Messenger.class);

        gameCenter = new GameCenter(messenger, new HashMap<>());
        game = gameCenter.createGame(player, opponent);
        Gson gson = new Gson();

        CuT = new PostCheckTurnRoute(gameCenter, gson);
    }

    /**
     * Validate that the message is correct for a player
     * who is taking their turn.
     */
    @Test
    public void testIsYourTurn() {
        Message message = new Message("true", MessageType.info);
        // Arrange the test scenario; the player checks the turn and it is their turn
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
        when(messenger.checkTurn(game, player)).thenReturn(message);
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gameCenter.checkTurn(player).getText(), message.getText());
        assertEquals(gameCenter.checkTurn(player).getType(), message.getType());
    }

    /**
     * Validate that the message is correct for a player
     * who is not taking their turn.
     */
    @Test
    public void testNotYourTurn() {
        Message message = new Message("false", MessageType.info);
        // Arrange the test scenario; the player checks the turn and it is their turn
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(opponent);
        when(messenger.checkTurn(game, opponent)).thenReturn(message);
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gameCenter.checkTurn(opponent).getText(), message.getText());
        assertEquals(gameCenter.checkTurn(opponent).getType(), message.getType());
    }

}

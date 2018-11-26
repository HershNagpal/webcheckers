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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for PostResignGameRoute
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class PostResignGameRouteTest {

    /**
     * Component under test
     */
    private PostResignGameRoute CuT;

    /**
     * friendly objects
     */
    private Game game;
    private GameCenter gameCenter;
    private Map<String, Game> finishedGames;

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

        finishedGames = new HashMap<>();
        gameCenter = new GameCenter(messenger, finishedGames);
        game = gameCenter.createGame(player, opponent);
        Gson gson = new Gson();

        CuT = new PostResignGameRoute(gameCenter, gson);
    }

    /**
     * Validate that the message is correct for when a resignation
     * is successful.
     */
    @Test
    public void testResignSuccessful() {
        Message message = new Message("", MessageType.info);
        // Arrange the test scenario; the player checks the turn and it is their turn
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
        when(messenger.resignGame(game, player)).thenReturn(message);
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gameCenter.resignGame(player).getText(), message.getText());
        assertEquals(gameCenter.resignGame(player).getType(), message.getType());
    }

    /**
     * Validate that the message is correct for when a resignation fails.
     */
    @Test
    public void testResignFailed() {
        Message message = new Message("", MessageType.error);
        // Arrange the test scenario; the player checks the turn and it is their turn
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
        when(messenger.resignGame(game, player)).thenReturn(message);
        // Invoke the test
        CuT.handle(request, response);
        assertEquals(gameCenter.resignGame(player).getText(), message.getText());
        assertEquals(gameCenter.resignGame(player).getType(), message.getType());
    }

}

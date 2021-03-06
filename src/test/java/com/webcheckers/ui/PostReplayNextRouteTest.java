package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.ReplayController;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.MessageType;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
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
 * Unit test suite for PostSpectatorNextTurnRoute class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class PostReplayNextRouteTest {

    /**
     * Component under test
     */
    private PostReplayNextRoute CuT;

    /**
     * friendly objects
     */

    private Gson gson;

    /**
     * mock objects
     */
    private ReplayController replayController;
    private Request request;
    private Response response;
    private Session session;
    private Player player;
    private Replay replay;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        gson = new Gson();
        replayController = mock(ReplayController.class);
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        player = mock(Player.class);
        replay = mock(Replay.class);

        when(request.session()).thenReturn(session);
        when(replayController.getReplayGame(player, "test")).thenReturn(replay);
        replayController.getReplayGame(player, "test");
        CuT = new PostReplayNextRoute(replayController, gson);
    }

    /**
     * Test when the message returned is true
     */
    @Test
    public void testTrueMessage() {
        Message message = new Message("true", MessageType.info);
        when(replayController.nextTurn(player)).thenReturn(message);

        CuT.handle(request, response);
        assertEquals(replayController.nextTurn(player).getText(), message.getText());
        assertEquals(replayController.nextTurn(player).getType(), message.getType());
    }

    /**
     * Test when the message returned is false
     */
    @Test
    public void testFalseMessage() {
        Message message = new Message("false", MessageType.info);
        when(replayController.nextTurn(player)).thenReturn(message);

        CuT.handle(request, response);
        assertEquals(replayController.nextTurn(player).getText(), message.getText());
        assertEquals(replayController.nextTurn(player).getType(), message.getType());
    }

}
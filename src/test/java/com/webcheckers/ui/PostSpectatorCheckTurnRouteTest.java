package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for PostSpectatorCheckTurnRoute class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class PostSpectatorCheckTurnRouteTest {

    private static final String gameID = "test";

    /**
     * Component under test
     */
    private PostSpectatorCheckTurnRoute CuT;

    /**
     * friendly objects
     */
    private Gson gson;

    /**
     * mock objects
     */
    private GameCenter gameCenter;
    private Request request;
    private Response response;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        gson = new Gson();
        gameCenter = mock(GameCenter.class);
        request = mock(Request.class);
        response = mock(Response.class);

        CuT = new PostSpectatorCheckTurnRoute(gameCenter, gson);
        when(request.body()).thenReturn(gameID);
    }

    /**
     * Test when the message returned is true
     */
    @Test
    public void testTrueMessage() {
        Message message = new Message("true", MessageType.info);
        when(gameCenter.checkTurn(gameID)).thenReturn(message);

        CuT.handle(request, response);
        assertEquals(gameCenter.checkTurn(gameID).getText(), message.getText());
        assertEquals(gameCenter.checkTurn(gameID).getType(), message.getType());
    }

    /**
     * Test when the message returned is false
     */
    @Test
    public void testFalseMessage() {
        Message message = new Message("false", MessageType.info);
        when(gameCenter.checkTurn(gameID)).thenReturn(message);

        CuT.handle(request, response);
        assertEquals(gameCenter.checkTurn(gameID).getText(), message.getText());
        assertEquals(gameCenter.checkTurn(gameID).getType(), message.getType());
    }

}
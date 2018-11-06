package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Messenger;
import com.webcheckers.model.*;
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
 * Unit test suite for the PostBackupMoveRoute class.
 * @author Luis Gutierrez
 */
@Tag("UI-tier")
public class PostValidateMoveRouteTest {

    /**
     * Component under test
     */
    private PostValidateMoveRoute CuT;

    /**
     * friendly objects
     */
    private Game game;
    private Move move;
    private Messenger messenger;
    private GameCenter gameCenter;

    /**
     * mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private Player player;
    private Player opponent;
    private Move move_Success;
    private Move move_Fail;

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

        move = mock(Move.class);

        gameCenter = new GameCenter(messenger);
        game = gameCenter.createGame(player, opponent);
        Gson gson = new Gson();

        CuT = new PostValidateMoveRoute(gameCenter, gson);

    }

    /**
     * Validate the message is correct for an info validateMove move message.
     */
    @Test
    public void testValidateMoveSuccess() {
        Message message_True = new Message("", MessageType.info);

        //Arrange the test scenario: the player makes a move
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
        when(messenger.validateMove(game, move)).thenReturn(message_True);

        //Invoke the test
        CuT.handle(request, response);
        assertEquals(gameCenter.validateMove(player,move).getText(), message_True.getText());
        assertEquals(gameCenter.validateMove(player,move).getType(), message_True.getType());
    }

  /**
   * Validate the message is correct for an error validateMove move message.
   */
    @Test
    public void testValidateMoveFail() {
        Message message_False = new Message("Invalid move. Try again.", MessageType.error);

        //Arrange the test scenario: the player makes a move
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
        when(messenger.validateMove(game, move)).thenReturn(message_False);

        //Invoke the test
        CuT.handle(request, response);
        assertEquals(gameCenter.validateMove(player,move).getText(), message_False.getText());
        assertEquals(gameCenter.validateMove(player,move).getType(), message_False.getType());
    }
}

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
 *
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

    private Messenger messengerSuccess;
    private GameCenter gameCenterSuccess;
    //private Move moveSuccess;

    private Messenger messengerFail;
    private GameCenter gameCenterFail;
    //private Move moveFail;

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
        player = mock(Player.class);

        move = mock(Move.class);

        //Setup for BackUp success
        messengerSuccess = mock(Messenger.class);
        gameCenterSuccess = new GameCenter(messengerSuccess);

        //Setup for BackUp fail
        messengerFail = mock(Messenger.class);
        gameCenterFail = new GameCenter(messengerFail);
    }

    /**
     * Validate the message is correct for an info validateMove move message.
     */
    @Test
    public void testValidateMoveSuccess() {
        Gson gson = new Gson();
        CuT = new PostValidateMoveRoute(gameCenterSuccess,gson);
        Message validate_True = new Message("", MessageType.info);

        //Arrange the test scenario: the player does a backUpMove
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);

        //Invoke the test
        CuT.handle(request, response);

        //BackUpMove Success
        when(messengerSuccess.validateMove(game,move)).thenReturn(validate_True);
        assertEquals(gameCenterSuccess.validateMove(player,move).getText(), validate_True.getText());
        assertEquals(gameCenterSuccess.validateMove(player,move).getType(), validate_True.getType());
    }

    @Test
    public void testValidateMoveFail() {
        Gson gson = new Gson();
        CuT = new PostValidateMoveRoute(gameCenterSuccess,gson);
        Message validate_False = new Message("Invalid move. Try again.", MessageType.error);

        //Arrange the test scenario: the player does a backUpMove
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);

        //Invoke the test
        CuT.handle(request, response);

        //BackUpMove Success
        when(messengerFail.validateMove(game,move)).thenReturn(validate_False);
        assertEquals(gameCenterFail.validateMove(player,move).getText(), validate_False.getText());
        assertEquals(gameCenterFail.validateMove(player,move).getType(), validate_False.getType());
    }
}

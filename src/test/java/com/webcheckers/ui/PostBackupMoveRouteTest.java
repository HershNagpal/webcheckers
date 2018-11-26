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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the PostBackupMoveRoute class.
 * @author Luis Gutierrez
 */
@Tag("UI-tier")
public class PostBackupMoveRouteTest {

    /**
     * Component under test
     */
    private PostBackupMoveRoute CuT;

    /**
     * friendly objects
     */
    private Game game;

    private Messenger messengerSuccess;
    private GameCenter gameCenterSuccess;

    private Messenger messengerFail;
    private GameCenter gameCenterFail;

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

        //Setup for BackUp success
        messengerSuccess = mock(Messenger.class);
        gameCenterSuccess = new GameCenter(messengerSuccess, new HashMap<>());

        //Setup for BackUp fail
        messengerFail = mock(Messenger.class);
        gameCenterFail = new GameCenter(messengerFail, new HashMap<>());

    }

    /**
     * Validate the message is correct for an info backUp move message.
     */
    @Test
    public void testBackUpMoveSuccess() {
        Gson gson = new Gson();
        CuT = new PostBackupMoveRoute(gameCenterSuccess,gson);
        Message backUp_True = new Message("", MessageType.info);

        //Arrange the test scenario: the player does a backUpMove
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);

        //Invoke the test
        CuT.handle(request, response);

        //BackUpMove Success
        when(messengerSuccess.backupMove(game)).thenReturn(backUp_True);
        assertEquals(gameCenterSuccess.backupMove(player).getText(), backUp_True.getText());
        assertEquals(gameCenterSuccess.backupMove(player).getType(), backUp_True.getType());

    }

    /**
     * Validate the message is correct for an error backUp move message.
     */
    @Test
    public void testBackUpMoveFail(){
        Gson gson = new Gson();
        CuT = new PostBackupMoveRoute(gameCenterFail,gson);
        Message backUp_False = new Message("Cannot Backup, there are no moves to undo.", MessageType.error);

        //Arrange the test scenario: the player does a backUpMove
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);

        //Invoke the test
        CuT.handle(request, response);

        //BackUpMove Fail
        when(gameCenterFail.backupMove(player)).thenReturn(backUp_False);
        assertEquals(gameCenterFail.backupMove(player).getText(), backUp_False.getText());
        assertEquals(gameCenterFail.backupMove(player).getType(), backUp_False.getType());
    }
}

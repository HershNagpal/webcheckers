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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
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
    private Game game_NoLastMoves;
    private Messenger messenger_NoLastMoves;
    private GameCenter gameCenter_NoLastMoves;

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

        gameCenter = new GameCenter(messenger);

        game = gameCenter.createGame(player, opponent);
        Gson gson = new Gson();

        game_NoLastMoves = mock(Game.class);
        gameCenter_NoLastMoves = mock(GameCenter.class);
        messenger_NoLastMoves = mock(Messenger.class);

        CuT = new PostBackupMoveRoute(gameCenter, gson);
    }

    @Test
    public void testBackUpMove() {
        Message backUp_True = new Message("", MessageType.info);
        Message backUp_False = new Message("Cannot Backup, there are no moves to undo.", MessageType.error);

        //Arrange the test scenario: the player does a backUpMove
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
        when(messenger.backupMove(game)).thenReturn(backUp_True);

        //Invoke the test
        CuT.handle(request, response);

        //BackUpMove Success
        assertEquals(gameCenter.backupMove(player).getText(), backUp_True.getText());
        assertEquals(gameCenter.backupMove(player).getType(), backUp_True.getType());

        //BackUpMove Fail
        when(gameCenter_NoLastMoves.backupMove(player)).thenReturn(backUp_False);
        assertEquals(gameCenter_NoLastMoves.backupMove(player).getText(), backUp_False.getText());
        assertEquals(gameCenter_NoLastMoves.backupMove(player).getType(), backUp_False.getType());

    }

    //TODO split testBackUpMove into success and fail
}

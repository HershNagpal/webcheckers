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


    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        player = mock(Player.class);
        opponent = mock(Player.class);
        messenger = mock(Messenger.class);

        gameCenter = new GameCenter();
        game = gameCenter.createGame(player, opponent);
        Gson gson = new Gson();

        CuT = new PostBackupMoveRoute(gameCenter, gson);
    }

    @Test
    public void testIs() {
        Message backUp_True = new Message("", MessageType.info);

        //Arrange the test scenario: the player does a backUpMove
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
        when(messenger.backupMove(game)).thenReturn(backUp_True);

        //Invoke the test
        CuT.handle(request, response);


    }

    @Test
    public void testNotYourTurn() {
        Message backUp_False = new Message("Cannot Backup, there are no moves to undo.", MessageType.error);

    }
}

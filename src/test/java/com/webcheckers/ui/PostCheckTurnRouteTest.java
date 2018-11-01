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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author
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
    private Gson gson;
    private GameCenter gameCenter;
    private Messenger messenger;

    /**
     * mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private Player player;
    private Game game;



    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        player = mock(Player.class);
        game = mock(Game.class);
        messenger = new Messenger();
        gameCenter = new GameCenter();
        gson = new Gson();

        CuT = new PostCheckTurnRoute(gameCenter, gson);
    }

    @Test
    public void testIsYourTurn() {
        // Arrange the test scenario; the player checks the turn and it is their turn
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);
    //    when(game.isActivePlayer(player)).thenReturn(true);
        doReturn(true).when(game).isActivePlayer(player);
    //    doReturn(new Message("true", MessageType.info)).when(messenger).checkTurn(game, player);
        doReturn(messenger.checkTurn(game, player)).when(gameCenter).checkTurn(player);
        CuT.handle(request, response);
        assertTrue(messenger.checkTurn(game, player) == gameCenter.checkTurn(player));
    }

    @Test
    public void testNotYourTurn() {
        when(gameCenter.checkTurn(player)).thenReturn(new Message("false", MessageType.info));
    }
}

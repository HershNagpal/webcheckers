package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
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
        player = mock(Player.class);
        game = mock(Game.class);

        gameCenter = new GameCenter();
        gson = new Gson();

        CuT = new PostCheckTurnRoute(gameCenter, gson);
    }

    @Test
    public void testIsYourTurn() {
        when(game.isActivePlayer(player)).thenReturn(true);
        when(gameCenter.getGame(player)).thenReturn(game);

    }

    @Test
    public void testNotYourTurn() {
        when(game.isActivePlayer(player)).thenReturn(false);
        when(gameCenter.getGame(player)).thenReturn(game);

    }
}

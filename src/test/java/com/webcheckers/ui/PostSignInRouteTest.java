package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;

/**
 * Unit test suite for the PostSignInRoute class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class PostSignInRouteTest {

    private static final String PLAYER_NAME = "testing";

    /**
     * Component under test
     */
    private PostSignInRoute CuT;

    /**
     * friendly objects
     */
    private PlayerLobby playerLobby;
    private Player player;

    /**
     * mock objects
     */
    private TemplateEngine templateEngine;
    private Session session;
    private Request request;
    private Response response;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);
        request = mock(Request.class);
        response = mock(Response.class);

        playerLobby = new PlayerLobby();
        player = new Player(PLAYER_NAME);
        CuT = new PostSignInRoute(playerLobby, templateEngine);
    }

    @Test
    public void testInvalidNameMessage() {

    }

    @Test
    public void testTakenNameMessage() {

    }

    @Test
    public void testSignInSuccessful() {

    }


}

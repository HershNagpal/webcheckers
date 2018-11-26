package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;

/**
 * Unit test suite for the WebServer class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class WebServerTest {

    /**
     * Component under test
     */
    private WebServer CuT;

    /**
     * friendly objects
     */
    private Gson gson;

    /**
     * mock objects
     */
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private ReplayController replayController;

    /**
     * Setup the objects for testing.
     */
    @BeforeEach
    public void setup() {
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        replayController = mock(ReplayController.class);
        gson = new Gson();

        CuT = new WebServer(gameCenter, playerLobby, replayController, templateEngine, gson);
    }

    /**
     * Check that the routes are initialized
     */
    @Test
    public void testInit() {
        // No exceptions means objects are initialized
        CuT.initialize();
    }

}

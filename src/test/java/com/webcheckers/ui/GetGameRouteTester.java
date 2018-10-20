package com.webcheckers.ui;

import com.webcheckers.TemplateEngineTester;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.GetGameRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test class for {@link GetGameRoute} componet.
 *
 * author: Matthew Bollinger mxb9771@rit.edu
 */
@Tag("UI-tier")
public class GetGameRouteTester {

    /**
     * The componet being tested
     */
    private GetGameRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        CuT = new GetGameRoute(playerLobby, engine);

    }

    /**
     * Test that if GetGameRoute does not recive the correct paramaters
     * then it will throw the propper errors
     */
    @Test
    public void constructor_params(){

    }

    /**
     * Test if a new game is created automattically when
     * is no game
     */
    @Test
    public void new_game(){
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(null);

        final TemplateEngineTester tester = new TemplateEngineTester();

    }
}

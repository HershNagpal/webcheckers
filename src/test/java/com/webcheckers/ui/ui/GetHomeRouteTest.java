package com.webcheckers.ui.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.GetHomeRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;

@Tag("UI-tier")
public class GetHomeRouteTest {

    private GetHomeRoute CuT;

    private PlayerLobby playerLobby;

    // mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;


    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);

        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        playerLobby = new PlayerLobby();
        CuT = new GetHomeRoute(playerLobby, engine);
    }
}

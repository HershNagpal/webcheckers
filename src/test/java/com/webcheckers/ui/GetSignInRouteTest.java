package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the GetSignInRoute class.
 *
 * @author Luis Gutierrez
 */
@Tag("UI-tier")
public class GetSignInRouteTest {

  //Component under test
  private GetSignInRoute CuT;

  //Mock objects
  private Request request;
  private Session session;
  private TemplateEngine engine;
  private Response response;
  
  @BeforeEach
  public void setup(){
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);

    CuT = new GetSignInRoute(engine);
  }
}
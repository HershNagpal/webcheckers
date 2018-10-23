package com.webcheckers.ui;

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
  private TemplateEngine templateEngine;
  private Response response;

  @BeforeEach
  public void setup() {
    //Creating mock objects using Mockito mock()
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    templateEngine = mock(TemplateEngine.class);

    //Unique CuT for each test
    CuT = new GetSignInRoute(templateEngine);
  }

  //Test when a new session enters the signin page
  @Test
  public void enter_sign_in(){
    // Mock up the view model
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    // Invoke the test
    CuT.handle(request, response);

    // Check view model is initialized
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    // Check view model has the necessary data
    testHelper.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);

    // Check view name
    testHelper.assertViewName(GetSignInRoute.VIEW_NAME);
  }
}
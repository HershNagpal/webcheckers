package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    private Player p1;
    private Player p2;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        p1 = mock(Player.class);
        p2 = mock(Player.class);

        CuT = new GetGameRoute(playerLobby, engine);
    }

    /**
     * Test that if you attempt to start a game with a playewr who is already in
     * game then you should be redirected to home with message
     */
    @Test
    public void testPlayerInGame() {
        Game game = mock(Game.class);

        //get players
        when(session.attribute((GetGameRoute.CURRENT_PLAYER_ATTR))).thenReturn(p1);
        when(playerLobby.getPlayer(request.queryParams("pid"))).thenReturn(p2);
        //player two is already in a game
        when(p2.getGame()).thenReturn(game);

        final  TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        //invoke test
        CuT.handle(request, response);
        //verify that user is sent back to home
        verify(response).redirect(WebServer.HOME_URL);

    }

    /**
     * Test if a new game is created automattically when
     * is no game
     */
    @Test
    public void newGame(){
        //getting players
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(p1);
        when(playerLobby.getPlayer(request.queryParams("pid"))).thenReturn(p2);
        //set games to null
        when(p1.getGame()).thenReturn(null);
        when(p1.getGame()).thenReturn(null);

        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        //start test
        CuT.handle(request,response);
        //make sure engine is setup
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        //make view model is populated correctly
        tester.assertViewModelAttributeIsPresent(GetGameRoute.BOARD_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.VIEW_MODE_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.CURRENT_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.RED_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.WHITE_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.ACTIVE_COLOR_ATTR);
    }

    /**
     * This method test if a player already has a they are redirected back to
     * their game
     */
    @Test
    public void rejoinGame(){
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(p1);
        //make player one already have a game
        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(p1.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);

        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        //invoke test
        CuT.handle(request, response);
        //make sure engine is setup
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        //make sure view model is populated correctly
        tester.assertViewModelAttributeIsPresent(GetGameRoute.BOARD_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.VIEW_MODE_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.CURRENT_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.RED_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.WHITE_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.ACTIVE_COLOR_ATTR);
    }


}

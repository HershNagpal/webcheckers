package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
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
    private GameCenter gameCenter;
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
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);

        p1 = mock(Player.class);
        p2 = mock(Player.class);

        CuT = new GetGameRoute(gameCenter, playerLobby, engine);

    }

    /**
     * Test that if GetGameRoute does not recive the correct paramaters
     * then it will throw the propper errors
     */
    @Test
    public void constructor_params(){

    }

    /**
     * Test when a player clicks on another player to start a game
     */
    @Test
    public void testPlayerStartsGame() {
        //getting players
        when(session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(p1);
        when(playerLobby.getPlayer(request.queryParams("pid"))).thenReturn(p2);

        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());

        //start test
        CuT.handle(request,response);
        //make sure engine is setup
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        //make view model is populated correctly
        tester.assertViewModelAttributeIsPresent(GetGameRoute.BOARD_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.CURRENT_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.VIEW_MODE_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.WHITE_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.RED_PLAYER_ATTR);
        tester.assertViewModelAttributeIsPresent(GetGameRoute.ACTIVE_COLOR_ATTR);
    }

    public void testPlayerChallengedToGame() {

    }

    /**
     * Test if a new game is created automattically when
     * is no game
     */
    @Test
    public void new_game(){
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

        tester.assertViewModelAttribute(GetGameRoute.BOARD_ATTR, BoardView.class);
        //tester.assertViewModelAttribute(GetGameRoute.CURRENT_PLAYER_ATTR,);
        //tester.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR,);
        //tester.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR,);
    }
}

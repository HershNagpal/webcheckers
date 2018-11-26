package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayController;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for GetReplayGameRoute class.
 *
 * @author Michael Kha
 */
@Tag("UI-tier")
public class GetReplayGameRouteTest {

    /**
     * Component under test
     */
    private GetReplayGameRoute CuT;

    /**
     * friendly objects
     */
    private Player p1;
    private Player p2;
    private Game game;
    private Replay replay;
    private Gson gson;

    /**
     * mock objects
     */
    private Request request;
    private Response response;
    private Session session;
    private ReplayController replayController;
    private PlayerLobby playerLobby;
    private TemplateEngine engine;
    private Player replayer;


    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        replayController = mock(ReplayController.class);
        playerLobby = mock(PlayerLobby.class);
        engine = mock(TemplateEngine.class);
        replayer = mock(Player.class);
        p1 = new Player("1");
        p2 = new Player("2");
        game = new Game(p1, p2, 0);
        replay = new Replay(game);

        String gameID = p1.getName() + "+" + p2.getName();
        when(request.queryParams(GetSpectatorGameRoute.ID_PARAM)).thenReturn(gameID);
        when(session.attribute(GetSpectatorGameRoute.CURRENT_PLAYER_ATTR)).thenReturn(replayer);
        when(replayController.getReplayGame(replayer, gameID)).thenReturn(replay);

        gson = new Gson();
        CuT = new GetReplayGameRoute(replayController, playerLobby, engine, gson);
    }

    /**
     * Test that the view model attributes are populated during red's turn
     */
    @Test
    public void testSpectatorViewModelRedTurn() {
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        // Invoke test
        CuT.handle(request, response);

        tester.assertViewModelAttribute(GetReplayGameRoute.CURRENT_PLAYER_ATTR, replayer);
        tester.assertViewModelAttribute(GetReplayGameRoute.VIEW_MODE_ATTR, ViewMode.REPLAY);
        tester.assertViewModelAttribute(GetReplayGameRoute.BOARD_ATTR, replay.getBoardView(p1));
        tester.assertViewModelAttribute(GetReplayGameRoute.ACTIVE_COLOR_ATTR, replay.getActiveColor());
        tester.assertViewModelAttribute(GetReplayGameRoute.RED_PLAYER_ATTR, p1);
        tester.assertViewModelAttribute(GetReplayGameRoute.WHITE_PLAYER_ATTR, p2);
        tester.assertViewModelAttribute(GetReplayGameRoute.TITLE_ATTR, GetReplayGameRoute.TITLE);
        tester.assertViewModelAttribute(GetReplayGameRoute.MODE_OPTIONS_ATTR, gson.toJson(replay.getModeOptions()));
    }

    /**
     * Test that the view model attributes are populated during white's turn
     */
    @Test
    public void testSpectatorViewModelWhiteTurn() {
        // Create the test scenario
        replay.switchActiveColor();
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        // Invoke test
        CuT.handle(request, response);

        tester.assertViewModelAttribute(GetReplayGameRoute.CURRENT_PLAYER_ATTR, replayer);
        tester.assertViewModelAttribute(GetReplayGameRoute.VIEW_MODE_ATTR, ViewMode.REPLAY);
        tester.assertViewModelAttribute(GetReplayGameRoute.BOARD_ATTR, replay.getBoardView(p2));
        tester.assertViewModelAttribute(GetReplayGameRoute.ACTIVE_COLOR_ATTR, replay.getActiveColor());
        tester.assertViewModelAttribute(GetReplayGameRoute.RED_PLAYER_ATTR, p1);
        tester.assertViewModelAttribute(GetReplayGameRoute.WHITE_PLAYER_ATTR, p2);
        tester.assertViewModelAttribute(GetReplayGameRoute.TITLE_ATTR, GetReplayGameRoute.TITLE);
        tester.assertViewModelAttribute(GetReplayGameRoute.MODE_OPTIONS_ATTR, gson.toJson(replay.getModeOptions()));
    }

}
package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.ReplayController;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * The {@code POST /game} route handler for going to the previous
 * turn in replay mode.
 *
 * @author Michael Kha
 */
public class PostReplayPrevRoute implements Route {

    /**
     * The replay controller holding finished games and players replaying
     */
    private ReplayController replayController;

    /**
     * Interprets and converts json
     */
    private Gson gson;

    /**
     * The constructor for the {@code POST /game} route handler to move
     * the replayed game to the previous turn in replay mode.
     *
     * @param replayController holds the finished games
     * @param gson used to interpret and convert json
     */
    public PostReplayPrevRoute(ReplayController replayController, Gson gson) {
        this.replayController = replayController;
        this.gson = gson;
    }

    /**
     * This action causes the replayed game to go to the previous turn.
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @return Info Message, true if a player submits the turn, false otherwise.
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Message message = replayController.previousTurn(player);
        return gson.toJson(message);
    }
}
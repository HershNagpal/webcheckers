package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The {@code POST /game} route handler for checking player turns.
 *
 * @author Michael Kha
 */
public class PostCheckTurnRoute implements Route {

    public PostCheckTurnRoute() {

    }

    @Override
    public Object handle(Request request, Response response) {
        return null;
    }
}

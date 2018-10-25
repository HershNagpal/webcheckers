package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The {@code POST /game} route handler for validating moves.
 *
 * @author Michael Kha
 */
public class PostValidateMove implements Route {

    public PostValidateMove() {

    }

    @Override
    public Object handle(Request request, Response response) {
        return null;
    }
}

package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * The {@code POST /game} route handler for checking player turns.
 *
 * @author Michael Kha
 */
public class PostCheckTurnRoute implements Route {

    private TemplateEngine templateEngine;

    public PostCheckTurnRoute(TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) {
        return null;
    }
}

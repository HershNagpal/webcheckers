package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
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
    private Gson gson;

    public PostCheckTurnRoute(TemplateEngine templateEngine, Gson gson) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gson, "gson must not be null");
        this.templateEngine = templateEngine;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        final String messageJSON = request.body();
        final Message message = gson.fromJson(messageJSON, Message.class);
        String text = message.getText();
        switch (text) {
            case "true":
                // This player's turn
                break;
            case "false":
                // Opponent still taking their turn
                break;
        }
        return null;
    }
}

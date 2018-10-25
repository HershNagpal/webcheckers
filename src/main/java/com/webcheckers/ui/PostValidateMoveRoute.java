package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import spark.*;

import java.util.Objects;


/**
 * The {@code POST /game} route handler for validating moves.
 *
 * @author Michael Kha
 */
public class PostValidateMoveRoute implements Route {

    private TemplateEngine templateEngine;
    private Gson gson;

    public PostValidateMoveRoute(TemplateEngine templateEngine, Gson gson) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.templateEngine = templateEngine;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        final String messageJSON = request.body();
        final Message message = gson.fromJson(messageJSON, Message.class);
        MessageType type = message.getType();
        switch (type) {
            case ERROR:
                break;
            case INFO:
                break;
        }

        return null;
    }
}

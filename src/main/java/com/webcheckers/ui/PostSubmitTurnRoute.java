package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

public class PostSubmitTurnRoute implements Route {

    private TemplateEngine templateEngine;
    private Gson gson;

    public PostSubmitTurnRoute(TemplateEngine templateEngine, Gson gson) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        final String messageJSON = request.body();
        System.out.println(messageJSON);
        final Message message = gson.fromJson(messageJSON, Message.class);
        MessageType type = message.getType();
        switch (type) {
            case ERROR:
                // Error occurred display error message
                break;
            case INFO:
                // Turn was processed
                break;
        }
        return null;
    }
}

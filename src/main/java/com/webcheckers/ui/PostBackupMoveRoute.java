package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;

public class PostBackupMoveRoute implements Route {

    private TemplateEngine templateEngine;
    private Gson gson;

    public PostBackupMoveRoute(TemplateEngine templateEngine, Gson gson) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(gson, "gson must not be null");
        this.templateEngine = templateEngine;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        final String messageJSON = request.body();
        System.out.println(messageJSON);
        final Message message = gson.fromJson(messageJSON, Message.class);
        MessageType type = message.getType();

        return null;
    }
}

package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;

/**
 * The {@code POST /game} route handler for submitting player turns.
 * @author Michael Kha
 */
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
        final Session session = request.session();
        final Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        final Game game = player.getGame();
        final String messageJSON = request.body();
        System.out.println(messageJSON);
        final Message message = gson.fromJson(messageJSON, Message.class);
        MessageType type = message.getType();
        switch (type) {
            case ERROR:
                // Error occurred display error message
                return new Message("Turn was not processed. Error occurred.", MessageType.ERROR);
            case INFO:
                // Turn was processed
                game.switchActiveColor();
                return new Message("Turn was submitted", MessageType.INFO);
        }
        return null;
    }
}

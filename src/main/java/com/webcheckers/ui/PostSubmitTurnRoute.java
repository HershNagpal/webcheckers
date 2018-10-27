package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
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
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Game game = player.getGame();
        //String messageJSON = request.body();
        //System.out.println(messageJSON);
        //Message message = gson.fromJson(messageJSON, Message.class);
        //System.out.println(message);
        //MessageType type = message.getType();
        //switch (type) {
        //    case ERROR:
        //        // Error occurred display error message
        //        return gson.toJson(new Message("Turn was not processed. Error occurred.", MessageType.ERROR));
        //    case INFO:
        //        // Turn was processed
        //
        //        return gson.toJson(new Message("Turn was submitted", MessageType.INFO));
        //}
    //    Move move = session.attribute("move");

        //if this is a move from the red player
        //we should flip the move to match our model
    //    if (game.isRedPlayer(player)){
    //        move = move.flipMove();
    //    }
    //    game.makeMove(move);
    //    game.switchActiveColor();
        System.out.println("Turn submitted");
        return gson.toJson(game.submitTurn());
    //    return gson.toJson(new Message("", MessageType.INFO));
    }
}

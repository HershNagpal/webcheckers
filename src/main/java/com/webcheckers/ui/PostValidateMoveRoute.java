package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
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
        String moveJSON = request.body();
        System.out.println(moveJSON);
        Move move = gson.fromJson(moveJSON, Move.class);
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        System.out.println("PostValidateMove:Grabbed player");
        Game game = player.getGame();
        System.out.println("PostValidateMove:Grabbed game");
        //if its a white player then we need to flip the move
        //before we validate it
        if (game.isRedPlayer(player)){
           move = move.flipMove();
        }
        if (valid(move, game)) {
            session.attribute("move", move);
            return gson.toJson(new Message("", MessageType.INFO));
        }
        else {
            return gson.toJson(new Message("Invalid move. Try again.", MessageType.ERROR));
        }
    }

    private boolean valid(Move move, Game game) {
        return game.validateMove(move);
    }
}

package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import com.webcheckers.model.Player;
import spark.*;

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

  /**
   * This action checks to see if the opponent has submitted their turn.
   * @param request The HTTP request
   * @param response The HTTP response
   * @return Info Message, true if opponent is done with turn, false otherwise.
   */
    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();
        final Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        System.out.println("Grabbed player");
        final Game game = player.getGame();

        final Message message;

        //Opponent ended his turn
        if(game.isActivePlayer(player)){
          message = new Message("true",MessageType.INFO);
        }
        //Opponent is still in his turn
        else{
          message = new Message("false",MessageType.INFO);
        }

        return message;
    }
}

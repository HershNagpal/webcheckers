package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
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
 * @author Matthew Bollinger
 * @author Luis Gutierrez
 */
public class PostCheckTurnRoute implements Route {

    private GameCenter gameCenter;
    private Gson gson;

    public PostCheckTurnRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(gson, "gson must not be null");

        this.gameCenter = gameCenter;
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
        Session session = request.session();
        Player player = session.attribute(GetGameRoute.CURRENT_PLAYER_ATTR);
        Game game = gameCenter.getGame(player);
        Message message;

        //Opponent ended his turn
        if(game.isActivePlayer(player)){
          message = new Message("true",MessageType.INFO);
        }
        //Opponent is still in his turn
        else{
          message = new Message("false",MessageType.INFO);
        }

        return gson.toJson(message);
    }
}

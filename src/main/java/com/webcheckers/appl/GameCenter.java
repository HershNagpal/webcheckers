package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all games that are occurring on the application.
 * Creates new games as necessary.
 *
 * @author Michael Kha
 */
public class GameCenter {

    private List<Game> games;

    public GameCenter() {
        games = new ArrayList<>();
    }

    public Boolean playerInGame(Player player) {
        for (Game game : games) {
            if (game.playerInGame(player)) {
                return true;
            }
        }
        return false;
    }

    public Game getGame(Player player) {
        return player.getGame();
    }

    public Game createGame(Player player, Player opponent) {
        Game game = new Game(player, opponent);
        games.add(game);
        return game;
    }

}

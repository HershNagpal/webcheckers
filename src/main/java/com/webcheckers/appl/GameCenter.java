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

    /**
     * All ongoing games.
     */
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

    /**
     * Get the game a player is in.
     * @param player The player to get a game from
     * @return The game or null
     */
    public Game getGame(Player player) {
        for (Game game : games) {
            if (game.playerInGame(player)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Create a new game for the two players.
     * @param player The player that started the game
     * @param opponent The player that was selected to be an opponent
     * @return The created game
     */
    public Game createGame(Player player, Player opponent) {
        Game game = new Game(player, opponent);
        games.add(game);
        return game;
    }

    /**
     * Used to remove games that ended.
     * @param game Game that ended
     */
    public void removeGame(Game game) {
        games.remove(game);
        // TODO remove players from game and game from players
    }

}

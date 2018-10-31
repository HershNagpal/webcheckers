package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * All ongoing games and their respective messengers
     */
    private Map<Game, Messenger> gamesM;

    /**
     * Initialize the list of games.
     */
    public GameCenter() {
        games = new ArrayList<>();
        gamesM = new HashMap<>();
    }

    /**
     * Is the given player in a game
     * @param player Player to check
     * @return If the player is in a game
     */
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
        Messenger messenger = new Messenger(game);
        gamesM.put(game, messenger);
        return game;
    }

    /**
     * Used to remove games that ended.
     * @param game Game that ended
     */
    public void removeGame(Game game) {
        games.remove(game);
        gamesM.remove(game);
        // TODO remove players from game and game from players
    }

    /**
     * Get the message from the messenger about whose turn it is.
     *
     * @return Message
     */
    public Message checkTurn(Game game) {
        return gamesM.get(game).checkTurn();
        //return messenger.checkTurn();
    }

    /**
     * Get the message from the messenger about a valid move.
     *
     * @return Message
     */
    public Message validateMove(Game game) {
        return gamesM.get(game).validateMove();
    }

    /**
     * Get the message from the messenger about submitting a turn.
     *
     * @return Message
     */
    public Message submitTurn(Game game) {
        return gamesM.get(game).submitTurn();
    }

    /**
     * Get the message from the messenger about backing up a move.
     *
     * @return Message
     */
    public Message backupMove(Game game) {
        return gamesM.get(game).backupMove();
    }

}

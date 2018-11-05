package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
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
     * All ongoing games
     */
    private List<Game> games;

    /**
     * All games that have ended
     */
    private List<Game> endedGames;

    /**
     * Manages messages
     */
    private Messenger messenger;

    /**
     * Initialize the list of games.
     */
    public GameCenter(Messenger messenger) {
        games = new ArrayList<>();
        endedGames = new ArrayList<>();
        this.messenger = messenger;
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
     * Was the given player challenged to a game
     * @param player Player to check
     * @return If the player was challenged
     */
    public Boolean wasChallenged(Player player) {
        for (Game game : games) {
            // White player is the challenged player
            if (game.isWhitePlayer(player) && !game.didPlayerResign()) {
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
     * TODO: Delete and update test suite
     * Used to remove games that ended.
     * @param game Game that ended
     */
    public void removeGame(Game game) {
        games.remove(game);
    }

    /**
     * Check if the game has been resigned. Resigned games are tracked
     * by the game center.
     * @param game Game to check
     * @return If this game been resigned
     */
    public boolean isGameOver(Game game) {
        if (endedGames.contains(game)) {
            games.remove(game);
            return true;
        }
        return false;
    }

    /**
     * Is the player the winner of the game.
     * @param game Game to check
     * @param player Player to check
     * @return If player is the winner
     */
    public boolean isWinner(Game game, Player player) {
        return game.isWinner(player);
    }

    /**
     * Get the message from the messenger about whose turn it is.
     *
     * @return Message with correct type
     */
    public Message checkTurn(Player player) {
        Game game = getGame(player);
        return messenger.checkTurn(game, player);
    }

    /**
     * Get the message from the messenger about a valid move.
     *
     * @return Message with correct type
     */
    public Message validateMove(Player player, Move move) {
        Game game = getGame(player);
        return messenger.validateMove(game, move);
    }

    /**
     * Get the message from the messenger about submitting a turn.
     *
     * @return Message with correct type
     */
    public Message submitTurn(Player player) {
        Game game = getGame(player);
        return messenger.submitTurn(game);
    }

    /**
     * Get the message from the messenger about backing up a move.
     *
     * @return Message with correct type
     */
    public Message backupMove(Player player) {
        Game game = getGame(player);
        return messenger.backupMove(game);
    }

    /**
     * TODO: update tests
     * Get the message from the messenger about resigning the game.
     * Update the list of ended games
     *
     * @return Message with correct type
     */
    public Message resignGame(Player player) {
        Game game = getGame(player);
        endedGames.add(game);
        return messenger.resignGame(game, player);
    }

}

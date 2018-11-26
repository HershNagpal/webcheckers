package com.webcheckers.appl;

import com.webcheckers.model.*;
import java.util.*;

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
    private Map<Player, Game> games;

    /**
     * All games that have finished
     */
    private Map<String, Game> finishedGames;

    /**
     * Access games through the game's ID
     */
    private Map<String, Game> gameIDMap;

    /**
     * Manages messages
     */
    private Messenger messenger;

    /**
     * Count the number of games created
     */
    private int gameCounter;

    /**
     * Initialize the list of games.
     *
     * @param messenger the messenger that will handle all message sending
     * @param finishedGames a map of the finished game names linked to the various games that has ended
     */
    public GameCenter(Messenger messenger, Map<String, Game> finishedGames) {
        games = new HashMap<>();
        this.finishedGames = finishedGames;
        gameIDMap = new HashMap<>();
        this.messenger = messenger;
        gameCounter = 0;
    }

    /**
     * Checks if there are any games ongoing.
     *
     * @return If there are games in the map
     */
    public boolean gamesOngoing() {
        return !getGames().isEmpty();
    }

    /**
     * Checks if there game finished.
     *
     * @return If there are games finished
     */
    public boolean gamesFinished() {
        return !finishedGames.isEmpty();
    }

    /**
     * Checks if the given player is in a game
     *
     * @param player Player to check
     * @return True if the player is in a game, false otherwise
     */
    public boolean playerInGame(Player player) {
        Game game = games.get(player);
        return game != null;
    }

    /**
     * Checks is the given player was challenged to a game
     *
     * @param player Player to check
     * @return True if the player was challenged, false otherwise
     */
    public boolean wasChallenged(Player player) {
        if (!playerInGame(player)) {
            return false;
        }
        Game game = games.get(player);
        return (game.isWhitePlayer(player) && !game.isGameOver());
    }

    /**
     * Get the game a player is in.
     *
     * @param player The player to get a game from
     * @return The game or null if it does not exist
     */
    public Game getGame(Player player) {
        return games.get(player);
    }

    /**
     * Get the game from a gameID.
     *
     * @param gameID The unique game ID
     * @return The game or null if it does not exist
     */
    public Game getGame(String gameID) {
        return gameIDMap.get(gameID);
    }

    /**
     * Get all unique games from the map
     *
     * @return All unique games
     */
    public Set<Game> getGames() {
        return new HashSet<>(games.values());
    }

    /**
     * Get all games that are finished
     *
     * @return All finished games
     */
    public Set<Game> getFinishedGames() {
        return new HashSet<>(finishedGames.values());
    }

    /**
     * Get the size of the set of games.
     *
     * @return The size of the set of games.
     */
    public int size() {
        return getGames().size();
    }

    public Game determineGame(Player player, Player opponent) {
        return opponent == null ? createAIGame(player)
                : createGame(player, opponent);
    }

    /**
     * Create a new game for the two players.
     *
     * @param player The player that started the game
     * @param opponent The player that was selected to be an opponent
     * @return The created game
     */
    public Game createGame(Player player, Player opponent) {
        Game game = new Game(player, opponent, ++gameCounter);
        games.put(player, game);
        games.put(opponent, game);
        gameIDMap.put(game.getGameID(), game);
        return game;
    }

    /**
     * Creates a new AI game for one player, includes creating the MoveBrain AI
     * @param player The player that started the game
     * @return The created game
     */
    public Game createAIGame(Player player){
       Player aiPlayer = new Player("AI");
       Game game = new MoveBrain(player, aiPlayer, ++gameCounter);
       games.put(player, game);
       return game;
    }

    /**
     * Check if the game has ended by any condition.
     *
     * @param game Game to check
     * @return True if this game been resigned, false otherwise
     */
    public boolean isGameOver(Game game) {
        return game.isGameOver();
    }

    /**
     * Update the state of the game center to remove the game a player is in
     *
     * @param game Game to update
     */
    public void updateGames(Game game) {
        if (game != null && game.isGameOver()) {
            String gameID = game.getGameID();
            Player red = game.getRedPlayer();
            Player white = game.getWhitePlayer();
            games.remove(red);
            games.remove(white);
            finishedGames.put(gameID, game);
            gameIDMap.remove(gameID);
        }
    }

    /**
     * Get the message from the messenger about how the player did in the game.
     *
     * @param game Game to check
     * @param player Player to check
     * @return Message about whether the player won or not
     */
    public Message isWinner(Game game, Player player) {
        return messenger.isWinner(game, player);
    }

    /**
     * Get the message from the messenger about who won the game.
     *
     * @param game Game to check
     * @return Message about who won
     */
    public Message whoWon(Game game) {
        return messenger.whoWon(game);
    }

    /**
     * Get the message from the messenger about if the turn has changed.
     * Used by spectator mode.
     *
     * @param gameID to be acquired from the list of games
     * @return Message about if the turn has changed
     */
    public Message checkTurn(String gameID) {
        Game game = getGame(gameID);
        return messenger.checkTurn(game);
    }

    /**
     * Get the message from the messenger about if it is a given player's turn.
     * Used by play mode.
     *
     * @param player to be checked if it is their turn
     * @return Message about whether it is the player's turn
     */
    public Message checkTurn(Player player) {
        Game game = getGame(player);
        return messenger.checkTurn(game, player);
    }

    /**
     * Get the message from the messenger about whether a move is valid
     * for a given player.
     *
     * @param player to be checked whether the move is valid
     * @param move to be checked if valid
     * @return Message about whether the move is valid for the player
     */
    public Message validateMove(Player player, Move move) {
        Game game = getGame(player);
        return messenger.validateMove(game, move);
    }

    /**
     * Get the message from the messenger about submitting a turn.
     *
     * @param player to check turn submit status for
     * @return Message about whether the player submitted the turn correctly
     */
    public Message submitTurn(Player player) {
        Game game = getGame(player);
        return messenger.submitTurn(game);
    }

    /**
     * Get the message from the messenger about backing up a moves
     *
     * @param player to check if a move has been backed up for
     * @return Message about whether the player has backed up a move correctly
     */
    public Message backupMove(Player player) {
        Game game = getGame(player);
        return messenger.backupMove(game);
    }

    /**
     * Get the message from the messenger about resigning the game.
     *
     * @param player to check if they have resigned
     * @return Message about whether the player has resigned correctly
     */
    public Message resignGame(Player player) {
        Game game = getGame(player);
        Message message = messenger.resignGame(game, player);
        // Update the games early if playing an AI game
        if (message.getType().equals(MessageType.info) &&
                game.getWhitePlayer().getName().equals("AI")) {
            updateGames(game);
        }
        return message;
    }

}
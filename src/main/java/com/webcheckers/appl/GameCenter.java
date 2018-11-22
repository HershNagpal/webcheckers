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
     * TODO: remove later if not needed for replays
     * All games that have ended
     */
    private Map<Player, Game> endedGames;

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
     */
    public GameCenter(Messenger messenger) {
        games = new HashMap<>();
        endedGames = new HashMap<>();
        gameIDMap = new HashMap<>();
        this.messenger = messenger;
        gameCounter = 0;
    }

    /**
     * Are there games ongoing?
     *
     * @return If there are games in the map
     */
    public boolean gamesOngoing() {
        return !getGames().isEmpty();
    }

    /**
     * Is the given player in a game
     * @param player Player to check
     * @return If the player is in a game
     */
    public boolean playerInGame(Player player) {
        Game game = games.get(player);
        return game != null;
    }

    /**
     * Was the given player challenged to a game
     * @param player Player to check
     * @return If the player was challenged
     */
    public boolean wasChallenged(Player player) {
        if (!playerInGame(player)) {
            return false;
        }
        Game game = games.get(player);
        return (game.isWhitePlayer(player) && !game.didPlayerResign());
    }

    /**
     * Get the game a player is in.
     * @param player The player to get a game from
     * @return The game or null if it does not exist
     */
    public Game getGame(Player player) {
        return games.get(player);
    }

    /**
     * Get the game from a gameID.
     * @param gameID The unique game ID
     * @return The game or null if it does not exist
     */
    public Game getGame(String gameID) {
        return gameIDMap.get(gameID);
    }

    /**
     * Get all unique games from the map
     * @return All unique games
     */
    public Set<Game> getGames() {
        return new HashSet<>(games.values());
    }

    /**
     * Get the size of the set of games.
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
       games.put(aiPlayer, game);
       return game;
    }

    /**
     * Check if the game has ended by any condition.
     * @param game Game to check
     * @return If this game been resigned
     */
    public boolean isGameOver(Game game) {
        return game.isGameOver();
    }

    /**
     * Update the state of the game center to remove the game
     * @param game Game to update
     */
    public void updateGames(Game game) {
        Player red = game.getRedPlayer();
        Player white = game.getWhitePlayer();
        games.remove(red);
        games.remove(white);
        endedGames.put(red, game);
        endedGames.put(white, game);
        gameIDMap.remove(game.getGameID());
    }

    /**
     * Get the message from the messenger about how the player did in the game.
     * @param game Game to check
     * @param player Player to check
     * @return Message with correct type
     */
    public Message isWinner(Game game, Player player) {
        return messenger.isWinner(game, player);
    }

    /**
     * Get the message from the messenger about who won the game.
     * @param game Game to check
     * @return Message about who won
     */
    public Message whoWon(Game game) {
        return messenger.whoWon(game);
    }

    /**
     * Get the message from the messenger about if the turn has changed.
     * Used by spectator mode.
     */
    public Message checkTurn(String gameID) {
        Game game = getGame(gameID);
        return messenger.checkTurn(game);
    }

    /**
     * Get the message from the messenger about whose turn it is.
     * Used by play mode.
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
     * Get the message from the messenger about resigning the game.
     * Update the list of ended games
     *
     * @return Message with correct type
     */
    public Message resignGame(Player player) {
        Game game = getGame(player);
        return messenger.resignGame(game, player);
    }

}

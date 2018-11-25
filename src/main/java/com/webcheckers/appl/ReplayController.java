package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Helps the UI interact with replaying games.
 *
 * @author Michael Kha
 */
public class ReplayController {

    /**
     * All games that have finished
     */
    private Map<String, Game> finishedGames;

    /**
     * Players that are replaying
     */
    private Map<Player, Game> replays;

    /**
     * Share the map of finished games with gameCenter
     *
     * @param finishedGames all finished games that can be replayed
     */
    public ReplayController(Map<String, Game> finishedGames) {
        this.finishedGames = finishedGames;
        replays = new HashMap<>();
    }

    /**
     * Get the replay game from a gameID and copy the original game.
     * Also tracks that the player is replaying.
     * @param gameID The unique game ID
     * @return The game or null if it does not exist
     */
    public Game getReplayGame(Player player, String gameID) {
        Game replayGame;
        if (!replays.containsKey(player)) {
            replayGame = new Game(finishedGames.get(gameID));
            replays.put(player, replayGame);
        } else {
            replayGame = replays.get(player);
        }
        return replayGame;
    }

    /**
     * Update that this player is no longer replaying
     * @param player Player to stop watching a replay
     */
    public void stopReplaying(Player player) {
        replays.remove(player);
    }


    /**
     * Get the message from the messenger about backing up a move.
     *
     * @return Message with correct type
     */
    public Message nextTurn(Player player) {
        Game game = replays.get(player);
        return game.nextTurn() ? Messenger.TURN_TRUE : Messenger.TURN_FALSE;
    }

    /**
     * Get the message from the messenger about backing up a move.
     *
     * @return Message with correct type
     */
    public Message previousTurn(Player player) {
        Game game = replays.get(player);
        return game.previousTurn() ? Messenger.TURN_TRUE : Messenger.TURN_FALSE;
    }
}

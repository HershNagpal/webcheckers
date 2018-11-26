package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import java.util.HashMap;
import java.util.Map;

/**
 * Helps the UI interact with replaying games.
 *
 * @author Michael Kha
 */
public class ReplayController {

    /**
     * Collection of all games that have finished
     */
    private Map<String, Game> finishedGames;

    /**
     * Players that are currently replaying games
     */
    private Map<Player, Replay> replays;

    /**
     * Constructor shares the map of finished games with gameCenter.
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
     *
     * @param player player who is accessing the replay
     * @param gameID The unique game ID
     * @return The game or null if it does not exist
     */
    public Replay getReplayGame(Player player, String gameID) {
        Replay replayGame;
        if (!replays.containsKey(player)) {
            replayGame = new Replay(finishedGames.get(gameID));
            replays.put(player, replayGame);
        } else {
            replayGame = replays.get(player);
        }
        return replayGame;
    }

    /**
     * Updates that a player is no longer replaying.
     *
     * @param player to stop watching a replay
     */
    public void stopReplaying(Player player) {
        replays.remove(player);
    }


    /**
     * Get the message from the messenger about continuing to the next turn.
     *
     * @param player replaying the game
     * @return Message about whether the next turn has been continued to successfully
     */
    public Message nextTurn(Player player) {
        Replay game = replays.get(player);
        return game.nextTurn() ? Messenger.TURN_TRUE : Messenger.TURN_FALSE;
    }

    /**
     * Get the message from the messenger about backing up to a previous turn.
     *
     * @param player replaying the game
     * @return Message about whether the previous turn has been returned to successfully
     */
    public Message previousTurn(Player player) {
        Replay game = replays.get(player);
        return game.previousTurn() ? Messenger.TURN_TRUE : Messenger.TURN_FALSE;
    }
}

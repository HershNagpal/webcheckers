package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The player lobby used to hold players.
 *
 * @author Michael Kha
 */
public class PlayerLobby {

    /**
     * Players must be unique
     */
    private Set<Player> players;

    /**
     * All players that are spectating games
     */
    private Set<Player> spectators;

    /**
     * All players that are replaying games
     */
    private Set<Player> replayers;

    /**
     * Initialize the set of players
     */
    public PlayerLobby(){
        players = new HashSet<>();
        spectators = new HashSet<>();
        replayers = new HashSet<>();
    }

    /**
     * Check if any players have the username
     * @param username name to check
     * @return is the username taken
     */
    public boolean isUsernameTaken(String username) {
        return players.contains(new Player(username));
    }

    /**
     * Alphanumeric regex to check if username contains at least
     * one letter or number and optionally spaces if this is satisfied.
     * @param username name to check
     * @return is the username valid
     */
    public boolean isValidUsername(String username) {
        if (username.length() == 0) {
            return false;
        }
        String[] parts;
        if (username.contains(" ")) {
            parts = username.split(" ");
            if (parts.length == 0) {
                return false;
            }
            for (String p : parts) {
                if (!p.matches("^[a-zA-Z0-9]*$")) {
                    return false;
                }
            }
            return true;
        }
        return username.matches("^[a-zA-Z0-9]*$");
    }

    /**
     * Let the player sign into the lobby
     * @param player Player signing in
     */
    public void signIn(Player player) {
        players.add(player);
    }

    /**
     * Let the player sign out of the lobby
     * @param player Player signing out
     */
    public void signOut(Player player) {
        players.remove(player);
    }

    /**
     * Get a player within the lobby given a name.
     * @param name Name of player
     * @return The specified player if they exist
     */
    public Player getPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Ease of access for player names within the lobby.
     *
     * @return List of player names other than the player's
     */
    public List<String> getPlayerLobbyNames(Player self) {
        List<String> names = new ArrayList<>();
        for (Player player : players) {
            if (!player.equals(self)) {
                names.add(player.getName());
            }
        }
        return names;
    }

    /**
     * The number of players in the lobby.
     *
     * @return number of players
     */
    public int size() {
        return players.size();
    }

    /**
     * Add the player to the set of spectators.
     *
     * @param player The player that is starting to spectate
     */
    public void startSpectating(Player player) {
        spectators.add(player);
    }

    /**
     * Remove the player from the set of spectators
     *
     * @param player The player who wants to stop spectating
     */
    public void stopSpectating(Player player) {
        spectators.remove(player);
    }

    /**
     * Is the player spectating a game?
     * @param player Player to check
     * @return If the player is spectating
     */
    public boolean isSpectating(Player player) {
        return spectators.contains(player);
    }

    /**
     * Add the player to the set of replayers.
     *
     * @param player The player that is starting to replay
     */
    public void startReplaying(Player player) {
        replayers.add(player);
    }

    /**
     * Remove the player from the set of replayers
     *
     * @param player The player who wants to stop replaying
     */
    public void stopReplaying(Player player) {
        replayers.remove(player);
    }

    /**
     * Is the player replaying a game?
     * @param player Player to check
     * @return If the player is replaying
     */
    public boolean isReplaying(Player player) {
        return replayers.contains(player);
    }

}

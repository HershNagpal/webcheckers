package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.HashSet;
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
     * Initialize the set of players
     */
    public PlayerLobby(){
        players = new HashSet<Player>(){};

    }

    /**
     * Check if any players have the username
     * @param username name to check
     * @return is the username taken
     */
    public synchronized boolean isUsernameTaken(String username) {
        return players.contains(new Player(username));
    }

    /**
     * Alphanumeric regex to check if username contains at least
     * one letter or number.
     * @param username name to check
     * @return is the username valid
     */
    public boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9]*$");
    }


    public boolean containsPlayer(Player player){
        if(player instanceof Player)
            return players.contains(player);
        else
            return false;
    }

    public Boolean addPlayer(Player player){
        if(!(player instanceof  Player))return  false;
        if(containsPlayer(player)) return false;
        else{
            players.add(player);
            return true;
        }


    }


}

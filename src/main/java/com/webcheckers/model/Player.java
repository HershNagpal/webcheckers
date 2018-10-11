package com.webcheckers.model;

/**
 * Model tier player class.
 *
 * @author Michael Kha
 */
public class Player {

    /**
     * Name of the player
     */
    private String name;

    /**
     * Game the player is in.
     */
    private Game game;

    /**
     * Create a player with a specified name. Set the game to null.
     *
     * @param name specified name
     */
    public Player(String name) {
        this.name = name;
        game = null;
    }

    /**
     * Get the player's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the game that this player is in, if any.
     *
     * @return Instance of a game or null
     */
    public Game getGame() {
        return game;
    }

    /**
     * Set the game to this player.
     * @param game Game to set to player
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Determine if another player is equal to this player based
     * on names.
     *
     * @param obj Object to be checked
     * @return  Is the object equal to this player
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Player) {
            Player p = (Player)obj;
            return p.name.equals(this.name);
        }
        return false;
    }

    /**
     * The hashcode of the player is determined by the name.
     *
     * @return  Hashcode of the player's name
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

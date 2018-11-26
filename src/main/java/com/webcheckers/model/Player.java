package com.webcheckers.model;

/**
 * Model tier player class.
 *
 * @author Michael Kha
 */
public class Player {

    /**
     * The String name of this player.
     */
    private String name;

    /**
     * Create a player with a specified name.
     *
     * @param name specified name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Get the player's name
     * @return name
     */
    public String getName() {
        return name;
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
     * @return Hashcode of the player's name
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
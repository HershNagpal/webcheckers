package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.HashSet;
import java.util.Set;

public class PlayerLobby {

    private Set<Player> players;

    public PlayerLobby(){
        players = new HashSet<Player>(){};

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

package it.polimi.se2018.model.events.messages;

import it.polimi.se2018.model.Player;

public abstract class Message {
    private Player player;

    public Message(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}

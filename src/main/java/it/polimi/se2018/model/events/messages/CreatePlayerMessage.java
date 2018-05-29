package it.polimi.se2018.model.events.messages;


import it.polimi.se2018.model.events.messages.Message;

public class CreatePlayerMessage extends Message {

    private String name;
    public CreatePlayerMessage(String name){
        this.name = name;
    }

    public String getPlayerName(){
        return name;
    }

}

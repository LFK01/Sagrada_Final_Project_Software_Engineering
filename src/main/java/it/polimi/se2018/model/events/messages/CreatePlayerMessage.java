package it.polimi.se2018.model.events.messages;

public class CreatePlayerMessage extends Message{  //non deve estendere la classe message perchè non ha player

    private String name;

    public CreatePlayerMessage( String sender, String recipients,String name){
        super(sender,recipients);
        this.name = name;
    }

    public String getPlayerName(){
        return name;
    }

}

package it.polimi.se2018.model.events.messages;

/**
 * @author Giovanni
 *Message containing the name of the player
 * to add in the game
 */
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

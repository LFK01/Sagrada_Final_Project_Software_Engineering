package it.polimi.se2018.model.events.messages;

public class SendSchemaAndTurn extends Message{
    private String[] schemaInGame;


    public SendSchemaAndTurn(String sender, String recipient,String[] schemaInGame) {
        super(sender, recipient);
        this.schemaInGame = schemaInGame;

    }

   public String[] getschemaInGame(){
        return schemaInGame;
   }
}

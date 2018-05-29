package it.polimi.se2018.view;
/**
 * @author Giovanni
 */

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.messages.CreatePlayerMessage;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.model.events.messages.SuccessMoveMessage;

//ha il metodo update che gli permette di ricevere messaggi e scrivere su terminale
public class RemoteView extends View {
    public RemoteView(Player player, Model model) {
        super(player, model);
    }


    public void update(Message message){
        if(message instanceof SuccessMoveMessage) {
            System.out.println("Giocatore creato" + ((CreatePlayerMessage) message).getPlayerName());
            //fai vedere la schemacard aggiornata con i dati della schemacard fornita dal messaggio show gameboard

        }



    }


}

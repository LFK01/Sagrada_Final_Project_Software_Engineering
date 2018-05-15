package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class TenagliaARotelle extends AbstractToolCard {

    private static TenagliaARotelle thisInstance;

    private TenagliaARotelle() {
        super("Tenaglia a Rotelle", "Dopo il tuo primo turno scegli immediatamente un altro dado. Salta il tuo secondo turno in questo round", true);
    }

    public static synchronized TenagliaARotelle getThisInstance(){
        if(thisInstance==null){
            thisInstance = new TenagliaARotelle();
        }
        return thisInstance;
    }

}

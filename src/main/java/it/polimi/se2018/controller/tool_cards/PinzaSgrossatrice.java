package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class PinzaSgrossatrice extends AbstractToolCard {

    private static PinzaSgrossatrice thisInstance;

    private PinzaSgrossatrice() {
        super("Pinza Sgrossatrice", "Dopo aver scelto un dado, aumenta o dominuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6", true);
    }

    public static synchronized PinzaSgrossatrice getThisInstance() {
        if(thisInstance==null){
            thisInstance = new PinzaSgrossatrice();
        }
        return thisInstance;
    }

    @Override
    public void activateCard(Player player) {

    }
}
package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class TamponeDiamantato extends AbstractToolCard {

    private static TamponeDiamantato thisInstance;

    private TamponeDiamantato() {
        super("Tampone Diamantato", "Dopo aver scelto un dado, giralo sulla faccia opposta 6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.", true);
    }

    public static synchronized TamponeDiamantato getThisInstance(){
        if(thisInstance==null){
            thisInstance = new TamponeDiamantato();
        }
        return thisInstance;
    }

}

package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class AlesatorePerLaminaDiRame extends AbstractToolCard {

    private static AlesatorePerLaminaDiRame thisInstance;

    private AlesatorePerLaminaDiRame() {
        super("Alesatore per lamina di rame", "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore. Devi rispettare tutte le altre restrizioni di piazzamento", true);
    }

    public static synchronized AlesatorePerLaminaDiRame getThisInstance(){
        if(thisInstance==null){
            thisInstance= new AlesatorePerLaminaDiRame();
        }
        return thisInstance;
    }

}

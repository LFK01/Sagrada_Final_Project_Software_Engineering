package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class DiluentePerPastaSalda extends AbstractToolCard {

    private static DiluentePerPastaSalda thisInstance;

    private DiluentePerPastaSalda() {
        super("Diluente per Pasta Salda", "Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto. Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento", true);
    }

    public static synchronized DiluentePerPastaSalda getThisInstance(){
        if(thisInstance==null){
            thisInstance = new DiluentePerPastaSalda();
        }
        return thisInstance;
    }

    @Override
    public void activateCard(Player player) {

    }
}

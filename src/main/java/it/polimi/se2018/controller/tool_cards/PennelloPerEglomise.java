package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class PennelloPerEglomise extends AbstractToolCard {

    private static PennelloPerEglomise thisInstance;

    private PennelloPerEglomise() {
        super("Pennello per Eglomise", "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore. Devi rispettare tutte le altre restrizioni di piazzamento", true);
    }

    public static synchronized PennelloPerEglomise getThisInstance() {
        if(thisInstance==null){
            thisInstance= new PennelloPerEglomise();
        }
        return thisInstance;
    }

    @Override
    public void activateCard(Player player) {

    }
}

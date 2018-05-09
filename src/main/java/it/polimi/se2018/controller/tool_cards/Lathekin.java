package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Player;

public class Lathekin extends AbstractToolCard {

    private static Lathekin thisInstance;

    private Lathekin() {
        super("Lathekin", "Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento", true);
    }

    public synchronized static Lathekin getThisInstance(){
        if(thisInstance==null){
            thisInstance = new Lathekin();
        }
        return thisInstance;
    }

    @Override
    public void activateCard(Player player) {

    }
}

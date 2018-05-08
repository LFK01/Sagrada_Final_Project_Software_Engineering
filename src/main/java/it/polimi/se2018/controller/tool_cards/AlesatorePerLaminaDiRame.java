package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Player;
//Luciano
public class AlesatorePerLaminaDiRame extends AbstractToolCard implements InterfaceToolCard {

    private static AlesatorePerLaminaDiRame thisInstance;

    private AlesatorePerLaminaDiRame() {
        super("Alesatore per lamina di rame", "", true);
    }

    public static synchronized AlesatorePerLaminaDiRame getThisIstance(){
        if(thisInstance==null){
            thisInstance= new AlesatorePerLaminaDiRame();
        }
        return thisInstance;
    }

    @Override
    public AbstractToolCard getInstance() {
        return thisInstance;
    }

    @Override
    public void activateCard(Player player) {

    }
}

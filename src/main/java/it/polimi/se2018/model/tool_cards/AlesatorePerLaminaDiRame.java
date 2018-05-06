package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Player;
//Luciano
public class AlesatorePerLaminaDiRame extends AbstractToolCard implements InterfaceToolCard {

    private static AlesatorePerLaminaDiRame thisIstance;

    private AlesatorePerLaminaDiRame() {
        super("Alesatore per lamina di rame", "", true);
    }

    public static synchronized AlesatorePerLaminaDiRame getThisIstance(){
        if(thisIstance==null){
            thisIstance= new AlesatorePerLaminaDiRame();
        }
        return thisIstance;
    }
    @Override
    public void activateCard(Player player) {

    }
}

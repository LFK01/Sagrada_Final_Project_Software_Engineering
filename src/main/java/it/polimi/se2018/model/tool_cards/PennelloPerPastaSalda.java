package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class PennelloPerPastaSalda extends AbstractToolCard {

    private static PennelloPerPastaSalda thisInstance;

    private PennelloPerPastaSalda() {
        super("Pennello per Pasta Salda", "Dopo aver scelto un dado, tira nuovamente quel dado. Se non puoi piazzarlo, riponilo nella Riserva", true);
    }

    public static synchronized PennelloPerPastaSalda getThisInstance() {
        if(thisInstance==null){
            thisInstance = new PennelloPerPastaSalda();
        }
        return thisInstance;
    }

}

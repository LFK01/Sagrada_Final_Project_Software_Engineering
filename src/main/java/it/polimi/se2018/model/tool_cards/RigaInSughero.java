package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */

public class RigaInSughero extends AbstractToolCard {

    private static RigaInSughero thisInstance;

    private RigaInSughero() {
        super("Riga in Sughero", "Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado. Devi rispettare tutte le restrizioni di piazzamento", true);
    }

    public static synchronized RigaInSughero getThisInstance(){
        if(thisInstance==null){
            thisInstance = new RigaInSughero();
        }
        return  thisInstance;
    }

}

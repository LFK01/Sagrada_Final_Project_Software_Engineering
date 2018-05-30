package it.polimi.se2018.model.tool_cards;

/**
 * @author Luciano
 */

public class PennelloPerEglomise extends AbstractToolCard {

    private static PennelloPerEglomise thisIstance;

    private PennelloPerEglomise() {
        super("Pennello per Eglomise", "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore. Devi rispettare tutte le altre restrizioni di piazzamento", true);
    }

    public static synchronized PennelloPerEglomise getThisInstance() {
        if(thisIstance==null){
            thisIstance= new PennelloPerEglomise();
        }
        return thisIstance;
    }

}

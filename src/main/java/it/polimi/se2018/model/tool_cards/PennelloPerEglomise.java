package it.polimi.se2018.model.tool_cards;
//Luciano
import it.polimi.se2018.model.Player;

public class PennelloPerEglomise extends AbstractToolCard implements InterfaceToolCard {

    private static PennelloPerEglomise thisIstance;

    public PennelloPerEglomise() {
        super("Pennello per Eglomise", "", true);
    }

    public static synchronized PennelloPerEglomise getThisIstance() {
        if(thisIstance==null){
            thisIstance= new PennelloPerEglomise();
        }
        return thisIstance;
    }

    @Override
    public AbstractToolCard getInstance() {
        return thisIstance;
    }

    @Override
    public void activateCard(Player player) {

    }
}

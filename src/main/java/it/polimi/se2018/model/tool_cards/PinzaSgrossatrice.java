package it.polimi.se2018.model.tool_cards;
//Luciano
import it.polimi.se2018.model.Player;

public class PinzaSgrossatrice extends AbstractToolCard implements InterfaceToolCard {

    private static PinzaSgrossatrice thisIstance;

    private PinzaSgrossatrice() {
        super("Pinza Sgrossatrice", "", true);
    }

    public static synchronized PinzaSgrossatrice getThisIstance() {
        if(thisIstance==null){
            thisIstance = new PinzaSgrossatrice();
        }
        return thisIstance;
    }

    @Override
    public void activateCard(Player player) {

    }
}

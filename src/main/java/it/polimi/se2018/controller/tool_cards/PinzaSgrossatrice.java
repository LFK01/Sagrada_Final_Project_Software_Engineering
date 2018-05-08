package it.polimi.se2018.controller.tool_cards;
//Luciano
import it.polimi.se2018.model.Player;

public class PinzaSgrossatrice extends AbstractToolCard implements InterfaceToolCard {

    private static PinzaSgrossatrice thisInstance;

    private PinzaSgrossatrice() {
        super("Pinza Sgrossatrice", "", true);
    }

    public static synchronized PinzaSgrossatrice getThisInstance() {
        if(thisInstance==null){
            thisInstance = new PinzaSgrossatrice();
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

package it.polimi.se2018.model.events;

import it.polimi.se2018.model.tool_cards.*;
import it.polimi.se2018.model.Player;

//Giorgia
public class UseToolCardMove extends PlayerMove {

    //i riferimenti alla View sono momentaneamente commentati in attesa dell'implementazione della classe corrispondente

    private InterfaceToolCard toolCard;

    public UseToolCardMove(InterfaceToolCard toolCard, Player p/*, View v*/) {
        super(p/*, v)*/);
        this.toolCard = toolCard;
    }

    public InterfaceToolCard getToolCard() {
        return toolCard;
    }

    @Override
    public boolean isDiceMove() {
        return false;
    }

}

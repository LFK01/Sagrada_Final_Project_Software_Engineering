package it.polimi.se2018.model.events;

import it.polimi.se2018.controller.tool_cards.AbstractToolCard;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.view.View;

//Giorgia
public class UseToolCardMove extends PlayerMove {

    //i riferimenti alla View sono momentaneamente commentati in attesa dell'implementazione della classe corrispondente

    private AbstractToolCard toolCard;
    private View view;

    public UseToolCardMove(AbstractToolCard toolCard, Player p/*, View v*/) {
        super(p/*, v)*/);
        this.toolCard = toolCard;
    }

    public AbstractToolCard getToolCard() {
        return toolCard;
    }

    @Override
    public boolean isDiceMove() {
        return false;
    }

}

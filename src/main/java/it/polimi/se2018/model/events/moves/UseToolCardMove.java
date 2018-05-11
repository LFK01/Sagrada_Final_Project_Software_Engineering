package it.polimi.se2018.model.events.moves;

import it.polimi.se2018.controller.tool_cards.AbstractToolCard;
import it.polimi.se2018.model.Player;

//Giorgia
public class UseToolCardMove extends PlayerMove {

    private AbstractToolCard toolCard;


    public UseToolCardMove(AbstractToolCard toolCard, Player p) {
        super(p);
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

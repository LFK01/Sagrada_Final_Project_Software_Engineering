package it.polimi.se2018.model.events.moves;

import it.polimi.se2018.controller.tool_cards.InterfaceToolCard;
import it.polimi.se2018.model.Player;

/**
 * @author giorgia
 */

public class UseToolCardMove extends PlayerMove {

    private InterfaceToolCard toolCard;


    public UseToolCardMove(InterfaceToolCard toolCard, Player p) {
        super(p);
        this.toolCard = toolCard;
    }

    public InterfaceToolCard getToolCard() {
        return toolCard;
    }



    @Override
    public boolean isDiceMove() {
        return false;
    }

    @Override
    public boolean isNoActionMove() {
        return false;
    }

}

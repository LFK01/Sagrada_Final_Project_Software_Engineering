package it.polimi.se2018.model.events.moves;

import it.polimi.se2018.controller.tool_cards.InterfaceToolCard;
import it.polimi.se2018.model.game_equipment.Player;
import it.polimi.se2018.model.events.messages.Message;

/**
 * @author giorgia
 */

public class UseToolCardMove extends Message {

    private InterfaceToolCard toolCard;


    public UseToolCardMove(InterfaceToolCard toolCard, Player player) {
        super("prova", "prova");
        this.toolCard = toolCard;
    }

    public InterfaceToolCard getToolCard() {
        return toolCard;
    }




    public boolean isDiceMove() {
        return false;
    }


    public boolean isNoActionMove() {
        return false;
    }

}

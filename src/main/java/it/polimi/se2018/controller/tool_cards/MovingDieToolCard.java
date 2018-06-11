package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.tool_cards.AbstractToolCard;
import it.polimi.se2018.view.comand_line.InputManager;

public class MovingDieToolCard extends AbstractToolCard {

    public MovingDieToolCard(int toolCardNumber) {
        super(toolCardNumber);
    }

    @Override
    public void activateToolCard(String username, String name, String values, Model model) {

    }

    @Override
    public InputManager getInputManager(String name) {
        return InputManager.INPUT_TOOL_CARD_MOVE_DICE_POSITIONS;
    }

}

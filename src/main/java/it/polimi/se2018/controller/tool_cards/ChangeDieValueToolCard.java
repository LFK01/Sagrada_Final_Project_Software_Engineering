package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.model.events.messages.RequestMessage;
import it.polimi.se2018.model.events.messages.SuccessMessage;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.tool_cards.AbstractToolCard;
import it.polimi.se2018.view.comand_line.InputManager;

public class ChangeDieValueToolCard extends AbstractToolCard {

    //6 -> Pennello per pasta salda: Dopo aver scelto un dado, tira nuovamente quel dado
    //                              Se non puoi piazzarlo, riponilo nella Riserva
    //10 -> Tampone Diamantato: Dopo aver scelto un dado, giralo
    //                          sulla faccia opposta
    private static final String[] toolCardNames = {new ChangeDieValueToolCard(6).getName(), new ChangeDieValueToolCard(10).getName()};
    private int position;

    public ChangeDieValueToolCard(int toolCardNumber) {
        super(toolCardNumber);
    }

    @Override
    public void activateToolCard(String username, String toolCardName, String values, Model model) {
        if(toolCardName.equals(toolCardNames[1])){
            int diePosition = Integer.parseInt(values);
            model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].getDiceList().get(diePosition).roll();
            Dice modifiedDie = model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].getDiceList().get(diePosition);
            Message requestMessage = new RequestMessage("server", username, toolCardName, InputManager.INPUT_PLACE_DIE);
            model.setChanged();
            String toolCardResults = "This is the dice you rolled: " + modifiedDie.toString();
            model.notifyObservers(new SuccessMessage("server", username, toolCardResults));
            model.setChanged();
            model.notifyObservers(requestMessage);
        }
        if(toolCardName.equals(toolCardNames[2])){
            int diePosition = Integer.parseInt(values);
            model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].getDiceList().get(diePosition).turnDieFace();
            String toolCardResults = "This is the current draft pool: " + model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].toString();
            model.setChanged();
            model.notifyObservers(new SuccessMessage("server", username, toolCardResults));
        }
    }

    @Override
    public InputManager getInputManager(String name) {
        return InputManager.INPUT_TOOL_CARD_CHANGE_DICE_VALUE_POSITIONS;
    }

    public void setDiePosition(int position){
        this.position = position;
    }
}

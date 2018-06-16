package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.ToolCardErrorMessage;
import it.polimi.se2018.model.exceptions.FullCellException;
import it.polimi.se2018.model.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.view.comand_line.InputManager;

public class MoveDieOnWindow implements TCEffectInterface {

    private boolean isDone;
    private Player activePlayer;
    private String username;
    private Dice dieToBeMoved;
    private Model model;
    private String toolCardName;
    private int newDieRow;
    private int newDieCol;
    private int oldDieRow;
    private int oldDieCol;

    public MoveDieOnWindow(){
        isDone = false;
    }

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) {
        String words[] = values.split(" ");
        this.model = model;
        this.toolCardName = toolCardName;
        oldDieRow = -1;
        oldDieCol = -1;
        newDieRow = -1;
        newDieCol = -1;
        int draftPoolPosition = -1;
        for(int i=0; i < words.length; i++){
            if(words[i].trim().equalsIgnoreCase("OldDieRow: ")){
                oldDieRow = Integer.parseInt(words[i+1]);
            }
            if(words[i].trim().equalsIgnoreCase("OldDieCol: ")){
                oldDieCol = Integer.parseInt(words[i+1]);
            }
            if(words[i].trim().equalsIgnoreCase("newDieRow: ")){
                newDieRow = Integer.parseInt(words[i+1]);
            }
            if(words[i].trim().equalsIgnoreCase("newDieCol: ")){
                newDieCol = Integer.parseInt(words[i+1]);
            }
            if(words[i].trim().equalsIgnoreCase("draftPoolPosition: ")){
                draftPoolPosition = Integer.parseInt(words[i+1]);
            }
        }
        for(Player player: model.getParticipants()){
            if(player.getName().equals(username)){
                activePlayer = player;
            }
        }
        dieToBeMoved = activePlayer.getSchemaCard().getCell(oldDieRow, oldDieCol).removeDieFromCell();
        if(toolCardName.equals(new ToolCard(2).getName())){
            placeDieWithToolCard(true, false, false);
        }
        if(toolCardName.equals(new ToolCard(3).getName())){
            placeDieWithToolCard(false, true, false);
        }
        if(toolCardName.equals(new ToolCard(4).getName())){
            placeDieWithToolCard(false, false, false);
        }
        if(toolCardName.equals(new ToolCard(9).getName())){
            int currentRound = model.getRoundNumber();
            dieToBeMoved = model.getGameBoard().getRoundDice()[currentRound].getDice(draftPoolPosition);
            placeDieWithToolCard(false, false, true);
        }
        isDone = true;
    }

    private void placeDieWithToolCard(boolean avoidColorRestrictions, boolean avoidValueRestrictions,
                                    boolean avoidNearnessRestrictions) {
        try {
            activePlayer.getSchemaCard().placeDie(dieToBeMoved, newDieRow, newDieCol,
                    avoidColorRestrictions, avoidValueRestrictions, avoidNearnessRestrictions);
            model.updateGameboard();
        } catch (RestrictionsNotRespectedException e) {
            try {
                activePlayer.getSchemaCard().placeDie(dieToBeMoved, oldDieRow, oldDieCol,
                        true, true, true);
            } catch (RestrictionsNotRespectedException | FullCellException e1) {
                System.out.println("Something has gone completely wrong!");
            }
            model.setChanged();
            model.notifyObservers(new ToolCardErrorMessage("server", username,
                    toolCardName, "ValueRestrictionNotRespected", InputManager.INPUT_CHOOSE_DIE));
        } catch (FullCellException e){
            try {
                activePlayer.getSchemaCard().placeDie(dieToBeMoved, oldDieRow, oldDieCol,
                        true, true, true);
            } catch (RestrictionsNotRespectedException | FullCellException e1) {
                System.out.println("Something has gone completely wrong!");
            }
            model.setChanged();
            model.notifyObservers(new ToolCardErrorMessage("server", username,
                    toolCardName, "FullCell", InputManager.INPUT_CHOOSE_DIE));
        }
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void setDone(boolean b) {
        isDone = b;
    }
}
package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.exceptions.ExecutingEffectException;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.ToolCardErrorMessage;
import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.view.comand_line.InputManager;

public class MoveDieOnWindow implements TCEffectInterface {

    private boolean isDone;
    private Player activePlayer;
    private Dice dieToBeMoved;
    private Model model;
    private String toolCardName;
    private int newDieRow;
    private int newDieCol;
    private int oldDieRow;
    private int oldDieCol;
    private String username;
    private int roundNumber;
    private int draftPoolPosition;
    private int roundTrackDiePosition;
    private boolean usingTaglierinaManuale;

    public MoveDieOnWindow(){
        isDone = false;
    }

    @Override
    public void doYourJob(String username, String toolCardName, String values, Model model) throws ExecutingEffectException {
        System.out.println("MoveDieOnWindow is working");
        String words[] = values.split(" ");
        this.username = username;
        this.model = model;
        this.toolCardName = toolCardName;
        oldDieRow = -1;
        oldDieCol = -1;
        newDieRow = -1;
        newDieCol = -1;
        draftPoolPosition = -1;
        roundTrackDiePosition = -1;
        for(int i=0; i < words.length; i++){
            if(words[i].trim().equalsIgnoreCase("OldDieRow:")){
                oldDieRow = Integer.parseInt(words[i+1]);
                System.out.println("read oldDieRow: " + oldDieRow);
            }
            if(words[i].trim().equalsIgnoreCase("OldDieCol:")){
                oldDieCol = Integer.parseInt(words[i+1]);
                System.out.println("read oldDieCol: " + oldDieCol);
            }
            if(words[i].trim().equalsIgnoreCase("newDieRow:")){
                newDieRow = Integer.parseInt(words[i+1]);
                System.out.println("read newDieRow: " + newDieRow );
            }
            if(words[i].trim().equalsIgnoreCase("newDieCol:")){
                newDieCol = Integer.parseInt(words[i+1]);
                System.out.println("read newDieCol: " + newDieCol);
            }
            if(words[i].trim().equalsIgnoreCase("draftPoolPosition:")){
                draftPoolPosition = Integer.parseInt(words[i+1]);
                System.out.println("read newDieCol: " + newDieCol);
                if(draftPoolPosition> model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList().size()){
                    System.out.println("Error");
                    throw new ExecutingEffectException();
                }
            }
            if(words[i].trim().equalsIgnoreCase("roundTrackDiePosition:")){
                roundTrackDiePosition = Integer.parseInt(words[i+1]);
                System.out.println("read roundTrackDiePosition: " + roundTrackDiePosition);
                if(roundTrackDiePosition>model.getGameBoard().getRoundDice()[roundNumber].getDiceList().size()){
                    System.out.println("Error");
                    throw new ExecutingEffectException();
                }
            }
            if(words[i].trim().equalsIgnoreCase("roundNumber:")){
                roundNumber = Integer.parseInt(words[i + 1]);
                System.out.println("read roundNumber: " + roundNumber);
                if(roundNumber> model.getRoundNumber()){
                    throw new ExecutingEffectException();
                }
            }
            if(words[i].trim().equalsIgnoreCase("TaglierinaManuale:")){
                usingTaglierinaManuale = true;
            }
        }
        for(Player player: model.getParticipants()){
            if(player.getName().equals(username)){
                activePlayer = player;
            }
        }
        dieToBeMoved = activePlayer.getSchemaCard().getCell(oldDieRow, oldDieCol).removeDieFromCell();
        if(toolCardName.equals(ToolCard.searchNameByNumber(2))){
            placeDieWithToolCard(true, false, false);
        }
        if(toolCardName.equals(ToolCard.searchNameByNumber(3))){
            placeDieWithToolCard(false, true, false);
        }
        if(toolCardName.equals(ToolCard.searchNameByNumber(4))){
            placeDieWithToolCard(false, false, false);
        }
        if(toolCardName.equals(ToolCard.searchNameByNumber(9))){
            int currentRound = model.getRoundNumber();
            dieToBeMoved = model.getGameBoard().getRoundDice()[currentRound].getDice(draftPoolPosition);
            placeDieWithToolCard(false, false, true);
        }
        if(toolCardName.equals(ToolCard.searchNameByNumber(12))){
            if(usingTaglierinaManuale) {
                if (checkSameColorDice()) {
                    placeDieWithToolCard(false, false, false);
                    usingTaglierinaManuale = false;
                }
            } else{
                placeDieWithToolCard(false, false, false);
                usingTaglierinaManuale = true;
            }
        }
        isDone = true;
    }

    private boolean checkSameColorDice() {
        Dice activePlayerDie = activePlayer.getSchemaCard().getCell(oldDieRow, oldDieCol).getAssignedDice();
        Dice roundTrackDie = model.getGameBoard().getRoundDice()[roundNumber].getDice(roundTrackDiePosition);
        return (activePlayerDie.getDiceColor() == roundTrackDie.getDiceColor());
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
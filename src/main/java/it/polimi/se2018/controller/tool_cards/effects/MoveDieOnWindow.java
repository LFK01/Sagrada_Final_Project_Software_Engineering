package it.polimi.se2018.controller.tool_cards.effects;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.exceptions.ExecutingEffectException;
import it.polimi.se2018.exceptions.InvalidCellPositionException;
import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.RoundDice;
import it.polimi.se2018.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class MoveDieOnWindow implements TCEffectInterface {

    private boolean isDone;
    private Player activePlayer;
    private Dice dieToBeMoved;
    private Model model;
    private ArrayList<Integer> backupRows = new ArrayList<>();
    private ArrayList<Integer> backupColumns = new ArrayList<>();
    private int newDieRow;
    private int newDieCol;
    private int oldDieRow;
    private int oldDieCol;
    private int roundNumber;
    private int roundTrackDiePosition;
    private Color firstDieMovingColor;

    public MoveDieOnWindow(){
        isDone = false;
    }

    @Override
    public void doYourJob(String username, String effectParameter, String values, Model model)
            throws ExecutingEffectException {
        System.out.println("MoveDieOnWindow is working");
        String words[] = values.split(" ");
        System.out.println("words lenght: " + words.length);
        this.model = model;
        int draftPoolPosition;
        oldDieRow = -1;
        oldDieCol = -1;
        newDieRow = -1;
        newDieCol = -1;
        roundTrackDiePosition = -1;
        for(int i=0; i < words.length; i++){
            if(words[i].trim().equalsIgnoreCase("OldDieRow:")){
                oldDieRow = Integer.parseInt(words[i+1]);
                backupRows.add(oldDieRow);
                System.out.println("read oldDieRow: " + oldDieRow);
            }
            if(words[i].trim().equalsIgnoreCase("OldDieCol:")){
                oldDieCol = Integer.parseInt(words[i+1]);
                backupColumns.add(oldDieCol);
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
                if(draftPoolPosition > model.getGameBoard().getRoundDice()[model.getRoundNumber()].getDiceList().size()){
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
        }
        for(Player player: model.getParticipants()){
            if(player.getName().equals(username)){
                activePlayer = player;
            }
        }
        try {
            dieToBeMoved = activePlayer.getSchemaCard().getCell(oldDieRow, oldDieCol).removeDieFromCell();
        } catch (InvalidCellPositionException e) {
            throw new ExecutingEffectException();
        }
        if(effectParameter.equals("NoColorRestrictions")){
            placeDieWithToolCard(true, false, false);
        }
        if(effectParameter.equals("NoValueRestrictions")){
            placeDieWithToolCard(false, true, false);
        }
        if(effectParameter.equals("AllRestrictions")){
            FileParser parser = new FileParser();
            parser.writeLathekinPositions(Model.FOLDER_ADDRESS_TOOL_CARDS,
                    oldDieRow, oldDieCol, newDieRow, newDieCol);
            placeDieWithToolCard(false, false, false);
        }

        if(effectParameter.equals("SameColorDice")){
            FileParser parser = new FileParser();
            if(parser.getTapWheelUsingValue(Model.FOLDER_ADDRESS_TOOL_CARDS)) {
                if (checkSameColorDice()) {
                    placeDieWithToolCard(false, false, false);
                } else {
                    throw new ExecutingEffectException();
                }
            } else{
                firstDieMovingColor = dieToBeMoved.getDiceColor();
                boolean foundSameColor = false;
                for(RoundDice roundDice: model.getGameBoard().getRoundDice()){
                    if(Arrays.asList(model.getGameBoard().getRoundDice()).indexOf(roundDice)<model.getRoundNumber()){
                        for (Dice die: roundDice.getDiceList()){
                            if(die.getDiceColor().equals(firstDieMovingColor)){
                                foundSameColor = true;
                            }
                        }
                    }
                }
                if(!foundSameColor){
                    throw new ExecutingEffectException();
                } else {
                    parser.writeTapWheelUsingValue(Model.FOLDER_ADDRESS_TOOL_CARDS, true);
                    parser.writeTapWheelFirstColor(Model.FOLDER_ADDRESS_TOOL_CARDS, firstDieMovingColor);
                    placeDieWithToolCard(false, false, false);
                }
            }
        }
        isDone = true;
    }

    private boolean checkSameColorDice() {
        FileParser parser = new FileParser();
        firstDieMovingColor = parser.getTapWheelFirstColor(Model.FOLDER_ADDRESS_TOOL_CARDS);
        return (dieToBeMoved.getDiceColor().equals(firstDieMovingColor));
    }

    private void placeDieWithToolCard(boolean avoidColorRestrictions, boolean avoidValueRestrictions,
                                    boolean avoidNearnessRestrictions) throws ExecutingEffectException {
        try {
            activePlayer.getSchemaCard().placeDie(dieToBeMoved, newDieRow, newDieCol,
                    avoidColorRestrictions, avoidValueRestrictions, avoidNearnessRestrictions);
        } catch (RestrictionsNotRespectedException | FullCellException e) {
            try {
                activePlayer.getSchemaCard().placeDie(dieToBeMoved, oldDieRow, oldDieCol,
                        true, true, true);
                throw  new ExecutingEffectException();
            } catch (RestrictionsNotRespectedException | FullCellException e1) {
                System.out.println("Something has gone completely wrong on MoveDieOnWindow.placeDieWithToolCard()");
            }
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

    public void backupTwoDicePositions(String username){
        FileParser parser = new FileParser();
        int[] oldPositions;
        int[] newPositions;
        for(Player player: model.getParticipants()){
            if(player.getName().equals(username)){
                activePlayer = player;
            }
        }
        System.out.println("doing backup for: " + activePlayer.getName());
        oldPositions = parser.getLathekinOldDiePositions(Model.FOLDER_ADDRESS_TOOL_CARDS);
        System.out.println("read old positions: " + oldPositions[0] + oldPositions[1]);
        newPositions = parser.getLathekinNewDiePositions(Model.FOLDER_ADDRESS_TOOL_CARDS);
        System.out.println("read new positions: " + newPositions[0] + newPositions[1]);
        try {
            dieToBeMoved = activePlayer.getSchemaCard().getCell(newPositions[0], newPositions[1]).removeDieFromCell();
            System.out.println("dieToBeMoved: " + dieToBeMoved.toString());
        } catch (InvalidCellPositionException e) {
            System.out.println("Something has gone completely wrong on MoveDieOnWindow.backupTwoDicePositions");
        }
        try {
            activePlayer.getSchemaCard().placeDie(dieToBeMoved, oldPositions[0], oldPositions[1],
                    true, true, true);
            System.out.println("die on old position");
        } catch (RestrictionsNotRespectedException | FullCellException e) {
            System.out.println("Something has gone completely wrong on MoveDieOnWindow.backupTwoDicePositions");
        }
        parser.writeLathekinPositions(Model.FOLDER_ADDRESS_TOOL_CARDS,
                -1, -1, -1, -1);
    }
}
package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.RequestMessage;
import it.polimi.se2018.model.events.messages.SuccessMessage;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.view.comand_line.InputManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Luciano
 */
public class ToolCard {

    private static final String[] toolCardNames = {"Pinza/Sgrossatrice",
            "Pennello/per/Eglomise",
            "Alesatore/per/lamina/di/rame",
            "Lathekin",
            "Taglierina/circolare",
            "Pennello/per/Pasta/Salda",
            "Martelletto",
            "Tenaglia/a/Rotelle",
            "Riga/in/Sughero",
            "Tampone/Diamantato",
            "Diluente/per/Pasta/Salda",
            "Taglierina/Manuale"};
    private String name;
    private String description;
    private ArrayList<InputManager> inputManagerList = new ArrayList<>();
    private ArrayList<EffectInterface> effectsList = new ArrayList<>();
    private EffectsFactory effectsFactory = new EffectsFactory();
    private boolean firstUsage;

    /**
     * Constructor method for ToolCard. Reads toolcard information from ToolCard.txt and initializes a new
     * ToolCard instance.
     * @param toolCardNumber Determines which tool card to initialize.
     */
    public ToolCard(int toolCardNumber){
        /**/
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream("src\\main\\java\\it\\polimi\\se2018\\controller\\tool_cards\\ToolCards.txt"));
            String line = "";
            boolean hasNextLine = true;
            boolean cardFound = false;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine){
                System.out.println("reading: " + line);
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length){
                    if(words[i].trim().equalsIgnoreCase("number:")){
                        if(toolCardNumber == Integer.parseInt(words[i+1])){
                            System.out.println("found card: " + toolCardNumber);
                            cardFound = true;
                            i++;
                        }
                    }
                    if(cardFound){
                        if(words[i].trim().equalsIgnoreCase("name:")){
                            name = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("description:")){
                            description = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("effects:")){
                            String[] effectsNamesList = words[i+1].split("/");
                            for(String effectName: effectsNamesList){
                                effectsList.add(effectsFactory.getThisEffect(effectName));
                            }
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("inputManager:")){
                            String[] inputsList = words[i+1].split("/");
                            for(String inputName: inputsList) {
                                inputManagerList.add(InputManager.valueOf(inputName));
                            }
                            hasNextLine = false;
                        }
                    }
                    i++;
                }
                try{
                    line = inputFile.nextLine();
                } catch (NoSuchElementException e){
                    hasNextLine = false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputFile.close();
        }
        firstUsage = true;
    }

    /**
     *
     * @return Name of the tool card
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return description of the tool card
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return true if the tool card has never been used
     */
    public boolean isFirstUsage() {
        return firstUsage;
    }

    /**
     * sets the firstUsage value to false when the tool
     * is being used for the first time
     */
    private void isBeingUsedForTheFirstTime(){
        if(firstUsage){
            firstUsage = false;
        }
    }

    /**
     * Performs all the action required from the tool card. At the end decreases
     * player favor tokens and updates the player moves
     * @param username player username of whom activates the tool card
     * @param toolCardName name of the activated tool card
     * @param values String of values that carries user input information
     * @param model model reference to perform modification
     */
    public void activateToolCard(String username, String toolCardName, String values, Model model){
        boolean executingEffect = false;
        EffectInterface effectToExecute = effectsList.get(0);
        while (!executingEffect) {
            /*searches for an undone effect*/
            if (!effectToExecute.isDone()) {
                /*executes effect*/
                effectToExecute.doYourJob(username, toolCardName, values, model);
                effectToExecute.setDone(true);
                executingEffect = true;
                if (effectsList.indexOf(effectToExecute) == effectsList.size() - 1) {
                    /*has done all effects in effectsList, resets all the effects and decreases player
                    * favor tokens as player has successfully used the tool card*/
                    setAllEffectsNotDone();
                    decreasePlayerFavorTokens(username, model);
                    /*updates player moves*/
                    setPlayerToolMoveDone(username, model);
                    if(toolCardName.equals(toolCardNames[8-1].replace("/", " "))){
                        /*after placing two consecutive dice player can't place another die*/
                        setNextRoundDieMoveDone(username, model);
                    }
                    /* player gets notified of the results of the move and
                    * every player gets an update of the gameboard*/
                    model.setChanged();
                    model.notifyObservers(new SuccessMessage("server", username, "SuccessfulMove"));
                    model.updateGameboard();
                } else{
                    if(effectsList.indexOf(effectToExecute) != 0) {
                        model.updateGameboard();
                        model.setChanged();
                        model.notifyObservers(new RequestMessage("server", username, toolCardName,
                                inputManagerList.get(effectsList.indexOf(effectToExecute))));
                    }
                }
            } else {
                if (effectsList.indexOf(effectToExecute) < effectsList.size() - 1) {
                    /*gets another effect to execute*/
                    effectToExecute = effectsList.get(effectsList.indexOf(effectToExecute)+1);
                } else {
                    /*terminates while*/
                    executingEffect = true;
                }
            }
        }
    }

    private void setNextRoundDieMoveDone(String username, Model model) {
        Player activePlayer = null;
        for(Player player: model.getParticipants()){
            if(player.getName().equals(username)){
                activePlayer = player;
            }
        }
        activePlayer.getPlayerTurns()[model.getRoundNumber()].getTurn2().getDieMove().setBeenUsed(true);
    }

    /**
     * Checks if a player can use a tool card.
     * @param activePlayer player reference of whom wants to activate the tool card
     * @param toolCardName name of the tool card that is being activated
     * @param roundNumber roundNumber to access player moves
     * @param isFirstDraftOfDice boolean value to know which game phase is on
     * @return
     */
    public boolean checkPlayerAbilityToUseTool(Player activePlayer, String toolCardName, int roundNumber,
                                                    boolean isFirstDraftOfDice){
        boolean playerAbleToActivateCard;
        if(isFirstDraftOfDice){
            if(activePlayer.getPlayerTurns()[roundNumber].getTurn1().getToolMove().isBeenUsed()){
                /*player has already used a tool card in this turn*/
                playerAbleToActivateCard = false;
            } else {
                if(activePlayer.getPlayerTurns()[roundNumber].getTurn1().getDieMove().isBeenUsed()){
                    /*player has already placed a die in this turn*/
                    playerAbleToActivateCard = checkSpecificCard(activePlayer, toolCardName);
                } else{
                    if(toolCardName.equals(toolCardNames[8-1].replace("/", " "))){
                        /*player has to place a die before using this card*/
                        playerAbleToActivateCard = false;
                    } else {
                        playerAbleToActivateCard = true;
                    }
                }
            }
            if(toolCardName.equals(toolCardNames[7-1].replace("/", " "))){
                /*this toolcard can't be activated on the first draft of dice*/
                playerAbleToActivateCard = false;
            }
        }else{
            if(activePlayer.getPlayerTurns()[roundNumber].getTurn2().getToolMove().isBeenUsed()){
                /*player has already used a tool card in this turn*/
                playerAbleToActivateCard = false;
            } else {
                if(activePlayer.getPlayerTurns()[roundNumber].getTurn2().getDieMove().isBeenUsed()){
                    /*player has already placed a die in this turn*/
                    playerAbleToActivateCard = checkSpecificCard(activePlayer, toolCardName);
                } else{
                    playerAbleToActivateCard = true;
                }
                if(toolCardName.equals(toolCardNames[8-1].replace("/", " "))){
                    /*this tool card can be used only on the first turn*/
                    playerAbleToActivateCard = false;
                }
            }
        }
        return playerAbleToActivateCard;
    }

    private boolean checkSpecificCard(Player activePlayer, String toolCardName){
        boolean playerAbleToActivateCard;
        if(toolCardName.equals(toolCardNames[6-1].replace("/", " ")) ||
                toolCardName.equals(toolCardNames[9-1].replace("/", " ")) ||
                toolCardName.equals(toolCardNames[11-1].replace("/", " "))){
            /*these toolcards can't be used if a die has already been placed*/
            playerAbleToActivateCard = false;
        } else {
            if(activePlayer.getSchemaCard().isEmpty()){
                if(toolCardName.equals(toolCardNames[2-1].replace("/", " ")) ||
                        toolCardName.equals(toolCardNames[3-1].replace("/", " ")) ||
                        toolCardName.equals(toolCardNames[4-1].replace("/", " ")) ||
                        toolCardName.equals(toolCardNames[12-1].replace("/", " "))){
                    /*these toolcards can't be activated with an empty window*/
                    playerAbleToActivateCard = false;
                } else {
                    playerAbleToActivateCard = true;
                }
            }else {
                if (activePlayer.getSchemaCard().hasLessThanTwoDie() && (toolCardName.equals(toolCardNames[4-1].replace("/", " ")) ||
                        toolCardName.equals(toolCardNames[12-1].replace("/", " ")))){
                    /*these schema card can't be activated whit less than two dice*/
                    playerAbleToActivateCard = false;
                } else{
                    playerAbleToActivateCard = true;
                }
            }
        }
        return playerAbleToActivateCard;
    }
    private void setPlayerToolMoveDone(String username, Model model) {
        for(Player player: model.getParticipants()){
            if(player.getName().equals(username)){
                int currentRound = model.getRoundNumber();
                if(model.isFirstDraftOfDice()){
                    player.getPlayerTurns()[currentRound].getTurn1().getToolMove().setBeenUsed(true);
                } else {
                    player.getPlayerTurns()[currentRound].getTurn2().getToolMove().setBeenUsed(true);
                }
            }
        }
    }


    private void decreasePlayerFavorTokens(String username, Model model) {
        for(Player player: model.getParticipants()){
            if(player.getName().equals(username)){
                if(isFirstUsage()){
                    isBeingUsedForTheFirstTime();
                    player.decreaseFavorTokens(false);
                } else {
                    player.decreaseFavorTokens(true);
                }
            }
        }
    }

    private void setAllEffectsNotDone() {
        for(EffectInterface effect: effectsList){
            effect.setDone(false);
        }
    }

    public ArrayList<InputManager> getInputManagerList(){
        return inputManagerList;
    }

}
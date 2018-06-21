package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.exceptions.ExecutingEffectException;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.events.messages.RequestMessage;
import it.polimi.se2018.model.events.messages.SuccessMessage;
import it.polimi.se2018.model.events.messages.ToolCardErrorMessage;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.view.comand_line.InputManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Luciano
 */
public class ToolCard {

    private String name;
    private String description;
    private ArrayList<InputManager> inputManagerList = new ArrayList<>();
    private ArrayList<TCEffectInterface> effectsList = new ArrayList<>();
    private EffectsFactory effectsFactory = new EffectsFactory();
    private boolean firstUsage;
    private int draftPoolDiePosition;

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
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length){
                    if(words[i].trim().equalsIgnoreCase("number:")){
                        if(toolCardNumber == Integer.parseInt(words[i+1])){
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

    public static String searchNameByNumber(int toolCardNumber){
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
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length){
                    if(words[i].trim().equalsIgnoreCase("number:")){
                        if(toolCardNumber == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                    }
                    if(cardFound){
                        if(words[i].trim().equalsIgnoreCase("name:")){
                            return words[i+1].replace('/', ' ');
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
        return "";
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
        readValues(values);
        TCEffectInterface effectToExecute = effectsList.get(0);
        while (!executingEffect) {
            /*searches for an undone effect*/
            if (!effectToExecute.isDone()) {
                /*executes effect*/
                System.out.println("executing effect: " + effectToExecute.toString());
                try {
                    effectToExecute.doYourJob(username, toolCardName, values, model);
                    executingEffect = true;
                    if (effectsList.indexOf(effectToExecute) == effectsList.size() - 1) {
                        /*has done all effects in effectsList, resets all the effects and decreases player
                         * favor tokens as player has successfully used the tool card*/
                        System.out.println("finished using the toolcard");
                        setAllEffectsNotDone();
                        decreasePlayerFavorTokens(username, model);
                        System.out.println("decreased player favor tokens");
                        /*updates player moves*/
                        setPlayerToolMoveDone(username, model);
                        if (toolCardName.equals(ToolCard.searchNameByNumber(8))) {
                            /*after placing two consecutive dice player can't place another die*/
                            setNextRoundDieMoveDone(username, model);
                        }
                        /* player gets notified of the results of the move and
                         * every player gets an update of the gameboard*/
                        model.setChanged();
                        model.notifyObservers(new SuccessMessage("server", username, "SuccessfulMove"));
                        System.out.println("sent success messsages");
                        model.updateGameboard();
                    }
                    else{
                        model.updateGameboardToolCard();
                        model.setChanged();
                        System.out.println("sending new request message");
                        StringBuilder builder = new StringBuilder();
                        builder.append("ToolCardName: ").append(toolCardName).append(" ");
                        builder.append("DraftPoolDiePosition: ").append(draftPoolDiePosition).append(" ");
                        model.notifyObservers(new RequestMessage("server", username, builder.toString(),
                                inputManagerList.get(effectsList.indexOf(effectToExecute)+1)));
                    }
                } catch (ExecutingEffectException e){
                    model.setChanged();
                    model.notifyObservers(new ToolCardErrorMessage("server", username, toolCardName,
                            "WrongInputParameters",
                            inputManagerList.get(effectsList.indexOf(effectToExecute)+1)));
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

    private void readValues(String values) {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(values.split(" ")));
        for(String word: words){
            if(word.equalsIgnoreCase("DraftPoolDiePosition:")){
                draftPoolDiePosition = Integer.parseInt(words.get(words.indexOf(word)+1));
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
            /*players are picking their first die*/
            if(activePlayer.getPlayerTurns()[roundNumber].getTurn1().getToolMove().isBeenUsed()){
                /*player has already used a tool card in this turn*/
                playerAbleToActivateCard = false;
            } else {
                if(activePlayer.getPlayerTurns()[roundNumber].getTurn1().getDieMove().isBeenUsed()){
                    /*player has already placed a die in this turn*/
                    playerAbleToActivateCard = checkSpecificCardsWithAPlacedDie(activePlayer, toolCardName);
                } else{
                    playerAbleToActivateCard = checkSpecificCardsWithoutAPlacedDie(activePlayer, toolCardName);
                }
            }
            if(toolCardName.equals(ToolCard.searchNameByNumber(7))){
                /*this toolcard can't be activated on the first draft of dice*/
                playerAbleToActivateCard = false;
            }
        }else{
            /*players are picking their second die*/
            if(activePlayer.getPlayerTurns()[roundNumber].getTurn2().getToolMove().isBeenUsed()){
                /*player has already used a tool card in this turn*/
                playerAbleToActivateCard = false;
            } else {
                if(activePlayer.getPlayerTurns()[roundNumber].getTurn2().getDieMove().isBeenUsed()){
                    /*player has already placed a die in this turn*/
                    playerAbleToActivateCard = checkSpecificCardsWithAPlacedDie(activePlayer, toolCardName);
                } else{
                    playerAbleToActivateCard = checkSpecificCardsWithoutAPlacedDie(activePlayer, toolCardName);
                }
                if(toolCardName.equals(ToolCard.searchNameByNumber(8))){
                    /*this tool card can be used only on the first draft of dice*/
                    playerAbleToActivateCard = false;
                }
            }
        }
        if(roundNumber<2 && (toolCardName.equalsIgnoreCase(ToolCard.searchNameByNumber(12))
                || toolCardName.equalsIgnoreCase(ToolCard.searchNameByNumber(5))
                )){
            /*these toolcards require at least a die placed on the roundTrack*/
            playerAbleToActivateCard = false;
        }
        return playerAbleToActivateCard;
    }

    private boolean checkSpecificCardsWithAPlacedDie(Player activePlayer, String toolCardName) {
        boolean playerAbleToActivateCard;
        if(toolCardName.equals(ToolCard.searchNameByNumber(6)) ||
                toolCardName.equals(ToolCard.searchNameByNumber(9)) ||
                toolCardName.equals(ToolCard.searchNameByNumber(11))){
            /*these toolcards can't be used if a die has already been placed*/
            playerAbleToActivateCard = false;
        } else{
            if (activePlayer.getSchemaCard().hasLessThanTwoDie()){
                if(toolCardName.equals(ToolCard.searchNameByNumber(4)) ||
                        toolCardName.equals(ToolCard.searchNameByNumber(12))){
                    /*these schema card can't be activated whit less than two dice*/
                    playerAbleToActivateCard = false;
                } else {
                    playerAbleToActivateCard = true;
                }
            } else{
                playerAbleToActivateCard = true;
            }
        }
        return playerAbleToActivateCard;
    }

    private boolean checkSpecificCardsWithoutAPlacedDie(Player activePlayer, String toolCardName){
        boolean playerAbleToActivateCard = false;
        if(toolCardName.equals(ToolCard.searchNameByNumber(8))){
            /*player has to place a die before using this card*/
            playerAbleToActivateCard = false;
        } else {
            if(activePlayer.getSchemaCard().isEmpty()){
                if(toolCardName.equals(ToolCard.searchNameByNumber(2)) ||
                        toolCardName.equals(ToolCard.searchNameByNumber(3)) ||
                        toolCardName.equals(ToolCard.searchNameByNumber(4)) ||
                        toolCardName.equals(ToolCard.searchNameByNumber(12))){
                    /*these toolcards can't be activated with an empty window*/
                    playerAbleToActivateCard = false;
                } else {
                    playerAbleToActivateCard = true;
                }
            } else {
                if (activePlayer.getSchemaCard().hasLessThanTwoDie()){
                    if(toolCardName.equals(ToolCard.searchNameByNumber(4)) ||
                            toolCardName.equals(ToolCard.searchNameByNumber(12))){
                        /*these schema card can't be activated whit less than two dice*/
                        playerAbleToActivateCard = false;
                    } else {
                        playerAbleToActivateCard = true;
                    }
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
        for(TCEffectInterface effect: effectsList){
            effect.setDone(false);
        }
    }

    public ArrayList<InputManager> getInputManagerList(){
        return inputManagerList;
    }

}
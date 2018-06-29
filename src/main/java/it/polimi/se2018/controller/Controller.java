package it.polimi.se2018.controller;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.controller.tool_cards.effects.MoveDieOnWindow;
import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.model.game_equipment.RoundDice;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.ToolCardMove;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;
import it.polimi.se2018.view.comand_line.InputManager;

import java.util.*;

/**
 * Controller class
 * @author Luciano, Giovanni
 */

public class Controller extends ProjectObservable implements ProjectObserver {

    private static final String TOOL_CARD_FILE_ADDRESS = "src\\main\\java\\it\\polimi\\se2018\\controller\\tool_cards\\ToolCards.txt";
    private Model model;
    private int time;
    private Timer timer;
    private int playerNumberDoneSelecting = 0;
    private boolean matchStarted;

    /**
     * Class constructor
     */
    public Controller() {
        this.model = new Model();
        this.timer = new Timer();
    }

    @Override
    public void update(Message message) {
    }

    public void update(ChooseDiceMove message) {
        timer.cancel();
        if(model.isFirstDraftOfDice()){
            if(model.getParticipants().get(model.getTurnOfTheRound()).getPlayerTurns()
                    [model.getRoundNumber()].getTurn1().getDieMove().isBeenUsed()){
                setChanged();
                notifyObservers(new ErrorMessage("server", model.getParticipants().
                        get(model.getTurnOfTheRound()).getName(), "DiceMoveAlreadyUsed"));
            } else {
                String draftPoolDiePosition = "DraftPoolDiePosition: " + message.getDraftPoolPos();
                setChanged();
                notifyObservers(new RequestMessage("server", message.getSender(), draftPoolDiePosition,
                        InputManager.INPUT_PLACE_DIE));
            }
        } else {
            if((model.getParticipants().get(model.getTurnOfTheRound()).getPlayerTurns()
                    [model.getRoundNumber()].getTurn2().getDieMove().isBeenUsed())) {
                setChanged();
                notifyObservers(new ErrorMessage("server", model.getParticipants().
                        get(model.getTurnOfTheRound()).getName(), "DiceMoveAlreadyUsed"));
            } else {
                String draftPoolDiePosition = "DraftPoolDiePosition: " + message.getDraftPoolPos();
                setChanged();
                notifyObservers(new RequestMessage("server", message.getSender(), draftPoolDiePosition,
                        InputManager.INPUT_PLACE_DIE));
            }
        }
        waitMoves();
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        /*should never be called here*/
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        /*should never be called here*/
    }

    public void update(ComebackSocketMessage message){
        setChanged();
        notifyObservers(new SuccessCreatePlayerMessage("server", message.getSender()));
    }

    public void update(CreatePlayerMessage message){
        model.addPlayer(message.getPlayerName());
        if(model.getParticipants().size()>1){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(model.getParticipants().size()>1){
                        model.sendPrivateObjectiveCard();
                        waitSchemaCards();
                    }
                    if(model.getParticipants().size()<2){
                        timer.cancel();
                    }
                }
            }, 1000L*time);
        }
        if(model.getParticipants().size()==4){
            timer.cancel();
            waitSchemaCards();
            model.sendPrivateObjectiveCard();
        }
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        timer.cancel();
        String[] words = diePlacementMessage.getValues().split(" ");
        int row=-1;
        int col = -1;
        int draftPoolPosition=-1;
        System.out.println("è ARRIVATO QUESTO VALORE: " + diePlacementMessage);
        for(int i =0;i<words.length;i++){
            if(words[i].trim().equalsIgnoreCase("row:")){
                row = Integer.parseInt(words[i+1]);
            }
            if(words[i].trim().equalsIgnoreCase("col:")){
                col = Integer.parseInt(words[i+1]);
            }
            if(words[i].trim().equalsIgnoreCase("DraftPoolDiePosition:")){
                draftPoolPosition=Integer.parseInt(words[i+1]);
                System.out.println("è ARRIVATO QUESTO VALORE: " + draftPoolPosition);
            }
        }
        System.out.println(draftPoolPosition + " " + row + " "+ col);
        model.doDiceMove(draftPoolPosition,row,col);
        waitMoves();
    }

    @Override
    public void update(ErrorMessage errorMessage) {

    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {

    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {

    }

    public void update(SelectedSchemaMessage message) {
        if(!matchStarted) {
            for (int playerPos = 0; playerPos < model.getParticipants().size(); playerPos++) {
                if (model.getParticipants().get(playerPos).getName().equals(message.getSender())) {
                    model.setSchemaCardPlayer(playerPos, message.getSchemaCardName());
                    playerNumberDoneSelecting++;
                }
            }
        }
        if(playerNumberDoneSelecting == model.getParticipantsNumber()){
            matchStarted = true;
            timer.cancel();
            model.extractPublicObjectiveCards();
            model.extractToolCards();
            model.extractRoundTrack();
            model.updateGameboard();
            waitMoves();
        }
    }

    public void update(NoActionMove message){
        timer.cancel();
        model.updateTurnOfTheRound();
        if(model.getTurnOfTheRound()<0){
            model.updateRound();
            if(model.getRoundNumber()==10){
                model.countPoints();
            }
            else
            model.updateGameboard();
        }
        else model.updateGameboard();
        waitMoves();
    }

    @Override
    public void update(RequestMessage requestMessage) {

    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {

    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {

    }

    @Override
    public void update(SuccessMessage successMessage) {

    }

    @Override
    public void update(SuccessMoveMessage successMoveMessage) {

    }

    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
        timer.cancel();
        System.out.println("toolCardActivaton: " + toolCardActivationMessage.getToolCardID()
        + "\nvalues: " + toolCardActivationMessage.getValues());
        System.out.println("toolcard name: " + toolCardActivationMessage.getToolCardID());
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            if(toolCard.getIdentificationName().equals(toolCardActivationMessage.getToolCardID())){
                /*finds active tool card*/
                System.out.println("found tool card");
                for (Player player: model.getParticipants()) {
                    if(player.getName().equals(toolCardActivationMessage.getSender())){
                        /*finds active player*/
                        String values = toolCardActivationMessage.getValues();
                        System.out.println("activating tc: " + toolCard.getName() +
                        "\nvalues: " + values);
                        toolCard.activateToolCard(player.getName(), toolCard.getIdentificationName(),
                                values, model);
                    }
                }
            }
        }
        waitMoves();
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            if(toolCard.getIdentificationName().equals(toolCardErrorMessage.getToolCardID())){
                /*finds active tool card*/
                for (Player player: model.getParticipants()) {
                    if(player.getName().equals(toolCardErrorMessage.getSender())){
                        /*finds active player*/
                        if(toolCard.getIdentificationName().equals(
                                new FileParser().searchIDByNumber(TOOL_CARD_FILE_ADDRESS, 4)
                        )){
                            MoveDieOnWindow backupEffect = (MoveDieOnWindow) toolCard.getEffectsList().get(0);
                            backupEffect.backupTwoDicePositions();
                        } else{
                            if(toolCard.isThereAnyEffectDone()){
                                /*tool cards has been partially used so
                                 * player tokens will be decreased anyway*/
                                player.decreaseFavorTokens(toolCard.isFirstUsage());
                                ToolCardMove toolCardMove;
                                if(model.isFirstDraftOfDice()){
                                    toolCardMove = player.getPlayerTurns()[model.getRoundNumber()]
                                            .getTurn1().getToolMove();
                                } else {
                                    toolCardMove = player.getPlayerTurns()[model.getRoundNumber()]
                                            .getTurn2().getToolMove();
                                }
                                toolCardMove.setBeenUsed(true);
                            }
                            toolCard.setAllEffectsNotDone();
                            model.updateGameboard();
                            waitMoves();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        timer.cancel();
        StringBuilder valuesBuilder = new StringBuilder();
        ToolCard activeToolCard = null;
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            if(useToolCardMove.getToolCardID().equals(toolCard.getIdentificationName())){
                activeToolCard = toolCard;
            }
        }
        Player activePlayer = null;
        for(Player player: model.getParticipants()) {
            if (player.getName().equals(useToolCardMove.getSender())) {
                activePlayer = player;
            }
        }
        if(activeToolCard.isFirstUsage()){
            if(activePlayer.getFavorTokens()>=1){
                if(activeToolCard.checkPlayerAbilityToUseTool(activePlayer, activeToolCard.getIdentificationName(),
                        model.getRoundNumber(), model.isFirstDraftOfDice())) {
                    System.out.println("player can use tool card");
                    valuesBuilder.append("ToolCardName: ")
                            .append(activeToolCard.getIdentificationName())
                            .append(" ");
                    if(activeToolCard.getIdentificationName().equals(
                            new FileParser().searchIDByNumber(TOOL_CARD_FILE_ADDRESS, 5)) ||
                            activeToolCard.getIdentificationName().equals(
                            new FileParser().searchIDByNumber(TOOL_CARD_FILE_ADDRESS, 12))
                            ){
                        valuesBuilder.append("RoundTrack: ");
                        for(int i=0; i<model.getRoundNumber(); i++){
                            valuesBuilder.append(model.getGameBoard().getRoundDice()[i].toString())
                                    .append(" ");
                        }
                        valuesBuilder.append("DiceStop ");
                    }
                    setChanged();
                    System.out.println("inputManager: " + activeToolCard.getInputManagerList().get(0));
                    notifyObservers(new RequestMessage("server", useToolCardMove.getSender(),
                            valuesBuilder.toString(),
                            activeToolCard.getInputManagerList().get(0)));
                } else {
                    setChanged();
                    notifyObservers(new ErrorMessage("server", activePlayer.getName(),
                            "PlayerUnableToUseToolCard"));
                }
            } else {
                System.out.println("player favor tokens: " + activePlayer.getFavorTokens() +
                        "\nneeds at least 1 favor tokens");
                setChanged();
                notifyObservers(new ErrorMessage("server", activePlayer.getName(),"NotEnoughFavorTokens"));
            }
        } else {
            if(activePlayer.getFavorTokens()>=2){
                if(activeToolCard.checkPlayerAbilityToUseTool(activePlayer, activeToolCard.getIdentificationName(),
                        model.getRoundNumber(), model.isFirstDraftOfDice())){
                    setChanged();
                    notifyObservers(new RequestMessage("server", useToolCardMove.getSender(),
                            "ToolCardName: " + activeToolCard.getIdentificationName(),
                            activeToolCard.getInputManagerList().get(0)));
                } else {
                    setChanged();
                    notifyObservers(new ErrorMessage("server", activePlayer.getName(), "PlayerUnableToUseToolCard"));
                }
            }
            else{
                setChanged();
                notifyObservers(new ErrorMessage("server", activePlayer.getName(), "NotEnoughFavorTokens"));
            }
        }
        waitMoves();
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {

    }

    private void waitSchemaCards(){
        timer = new Timer();
        System.out.println("timer started");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                matchStarted = true;
                System.out.println("match starting, player can't select new schema card");
                model.extractPublicObjectiveCards();
                model.extractToolCards();
                model.extractRoundTrack();
                model.updateGameboard();
                waitMoves();
            }
        }, time*1000L);
    }

    private void waitMoves() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setChanged();
                Player activePlayer = model.getPlayer(model.getTurnOfTheRound());
                notifyObservers(new ErrorMessage("server", activePlayer.getName(), "TimeElapsed"));
                activePlayer.setConnected(false);
                model.updatePlayerDisconnected(activePlayer);
                waitMoves();
            }
        }, time*1000L);
    }

    public void addObserverToModel(ProjectObserver observer){
        model.addObserver(observer);
    }

    public void setTimer(int time){
        this.time = time;
    }
}
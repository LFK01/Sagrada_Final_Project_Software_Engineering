package it.polimi.se2018.controller;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.objective_cards.private_objective_cards.*;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;
import it.polimi.se2018.view.comand_line.InputManager;

import java.util.*;

/**
 * Controller class
 * @author Luciano, Giovanni
 */

public class Controller extends ProjectObservable implements ProjectObserver {

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

    }

    @Override
    public void update(ComebackMessage comebackMessage) {

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
        for(int i =0;i<words.length;i++){
            if(words[i].trim().equalsIgnoreCase("row:")){
                row = Integer.parseInt(words[i+1]);
            }
            if(words[i].trim().equalsIgnoreCase("col:")){
                col = Integer.parseInt(words[i+1]);
            }
            if(words[i].trim().equalsIgnoreCase("DraftPoolDiePosition:")){
                draftPoolPosition=Integer.parseInt(words[i+1]);
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
                //model.countPoints
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
        System.out.println("toolCardActivaton: " + toolCardActivationMessage.getToolCardName()
        + "\nvalues: " + toolCardActivationMessage.getValues());
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            if(toolCard.getName().equals(toolCardActivationMessage.getToolCardName().replace("/", " "))){
                /*finds active tool card*/
                System.out.println("found tool card");
                for (Player player: model.getParticipants()) {
                    if(player.getName().equals(toolCardActivationMessage.getSender())){
                        /*finds active player*/
                        String values = toolCardActivationMessage.getValues();
                        System.out.println("activating tc: " + toolCard.getName() +
                        "\nvalues: " + values);
                        toolCard.activateToolCard(player.getName(), toolCard.getName(), values, model);
                    }
                }
            }
        }
        waitMoves();
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        /*shouldn't be called here*/
    }

    @Override
    public void update(UseToolCardMove useToolCardMove) {
        timer.cancel();
        ToolCard activeToolCard = null;
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            if(useToolCardMove.getToolCardName().replace("/", " ").equals(toolCard.getName())){
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
                if(activeToolCard.checkPlayerAbilityToUseTool(activePlayer, activeToolCard.getName(),
                        model.getRoundNumber(), model.isFirstDraftOfDice())) {
                    System.out.println("player can use tool card");
                    setChanged();
                    System.out.println("inputManager: " + activeToolCard.getInputManagerList().get(0));
                    notifyObservers(new RequestMessage("server", useToolCardMove.getSender(),
                            "ToolCardName: " + activeToolCard.getName().replace(" ", "/"),
                            activeToolCard.getInputManagerList().get(0)));
                } else {
                    setChanged();
                    notifyObservers(new ErrorMessage("server", activePlayer.getName(),
                            "PlayerUnableToUseToolCard"));
                }
            } else {
                setChanged();
                notifyObservers(new ErrorMessage("server", activePlayer.getName(),"NotEnoughFavorTokens"));
            }
        } else {
            if(activePlayer.getFavorTokens()>=2){
                if(activeToolCard.checkPlayerAbilityToUseTool(activePlayer, activeToolCard.getName(),
                        model.getRoundNumber(), model.isFirstDraftOfDice())){
                    setChanged();
                    notifyObservers(new RequestMessage("server", useToolCardMove.getSender(),
                            activeToolCard.getName().replace(" ", "/"),
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
                update(new NoActionMove(model.getParticipants().get(model.getTurnOfTheRound()).getName(),
                        "server"));
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
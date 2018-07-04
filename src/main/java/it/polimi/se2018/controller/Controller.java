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
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.player.ToolCardMove;
import it.polimi.se2018.utils.ProjectObservable;
import it.polimi.se2018.utils.ProjectObserver;
import it.polimi.se2018.utils.TimerLobbyTask;
import it.polimi.se2018.utils.TimerMoves;
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
    private TimerLobbyTask timerLobbyTask;
    private TimerMoves waitMovesTask;

    /**
     * Class constructor
     */
    public Controller() {
        this.model = new Model();
        this.timer = new Timer();
        timerLobbyTask = new TimerLobbyTask(this, model);
    }

    /*placing die controller*/
    @Override
    public void update(ChooseDiceMove message) {
        if(message.getDraftPoolPos()>model.getGameBoard().getRoundTrack().getRoundDice()[model.getRoundNumber()].getDiceList().size()-1){
            setChanged();
            notifyObservers(new ErrorMessage("server", model.getParticipants().
                    get(model.getTurnOfTheRound()).getName(), "NotValidDraftPoolPosition"));
        }else {
            if (model.isFirstDraftOfDice()) {
                if (model.getParticipants().get(model.getTurnOfTheRound()).getPlayerTurns()
                        [model.getRoundNumber()].getTurn1().getDieMove().isBeenUsed()) {
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
                if ((model.getParticipants().get(model.getTurnOfTheRound()).getPlayerTurns()
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
        }
    }

    @Override
    public void update(ChooseSchemaMessage chooseSchemaMessage) {
        /*should never be called here*/
    }

    @Override
    public void update(ComebackMessage comebackMessage) {
        if(model.getParticipants().stream().filter(
                player -> player.getName().equalsIgnoreCase(comebackMessage.getUsername())
        ).count()>0) {
            unblockPlayer(comebackMessage.getUsername());
        } else {
            setChanged();
            notifyObservers(new ErrorMessage("server",
                    comebackMessage.getSender(), "OldUsernameNotFound"));
        }
    }

    @Override
    public void update(CreatePlayerMessage createPlayerMessage){
        if(!timerLobbyTask.isStarted()) {
            if (countConnectedPlayer() > 1) {
                timerLobbyTask.setStarted(true);
                System.out.println("lobby timer started");
                timer.schedule(timerLobbyTask, 1000L * time);
            }
        }
        if(!timerLobbyTask.isEnded()) {
            model.addPlayer(createPlayerMessage.getPlayerName());
            if (countConnectedPlayer() == 4) {
                timer.cancel();
                model.sendPrivateObjectiveCard();
                waitSchemaCards();
            }
        } else {
            setChanged();
            notifyObservers(new ErrorMessage("server", createPlayerMessage.getPlayerName(),
                    "LobbyTimeEnded"));
        }
    }

    /*placing die controller*/
    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
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
    }

    @Override
    public void update(ErrorMessage errorMessage) {

    }

    @Override
    public void update(SendGameboardMessage sendGameboardMessage) {
        /*this method should never be called*/
    }

    @Override
    public void update(SelectedSchemaMessage message) {
        if(!matchStarted) {
            model.getParticipants().stream().filter(
                    p -> p.getName().equals(message.getSender())
            ).forEach(
                    p -> {
                        model.setSchemaCardPlayer(model.getParticipants().indexOf(p),
                                message.getSchemaCardName());
                        playerNumberDoneSelecting++;
                    }
            );
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

    @Override
    public void update(NoActionMove message){
        if(!waitMovesTask.hasEnded()){
            timer.cancel();
        }
        model.getParticipants().stream().filter(
                p -> p.getName().equals(message.getSender())
        ).forEach(
                p -> {
                    if(model.isFirstDraftOfDice()) {
                        p.getPlayerTurns()[model.getRoundNumber()].getTurn1().getDieMove().setBeenUsed(true);
                        p.getPlayerTurns()[model.getRoundNumber()].getTurn1().getToolMove().setBeenUsed(true);
                    } else {
                        p.getPlayerTurns()[model.getRoundNumber()].getTurn2().getDieMove().setBeenUsed(true);
                        p.getPlayerTurns()[model.getRoundNumber()].getTurn2().getToolMove().setBeenUsed(true);
                    }
                }
        );
        model.updateTurnOfTheRound();
        if(model.getRoundNumber()<Model.MAXIMUM_ROUND_NUMBER-1 && countConnectedPlayer()>1) {
            if(!model.getParticipants().get(model.getTurnOfTheRound()).isConnected()){
                update(new NoActionMove(model.getParticipants().get(model.getTurnOfTheRound()).getName(), "server"));
            }
            model.updateGameboard();
            waitMoves();
        }
    }

    @Override
    public void update(RequestMessage requestMessage) {
        /*this method should never be called*/
    }

    @Override
    public void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage) {
        /*this method should never be called*/
    }

    @Override
    public void update(SuccessCreatePlayerMessage successCreatePlayerMessage) {
        /*this method should never be called*/
    }

    /*toolcard controller*/
    @Override
    public void update(ToolCardActivationMessage toolCardActivationMessage) {
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
    }

    /*toolcard controller*/
    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        for (ToolCard toolCard : model.getGameBoard().getToolCards()) {
            if (toolCard.getIdentificationName().equals(toolCardErrorMessage.getToolCardID())) {
                /*finds active tool card*/
                for (Player player : model.getParticipants()) {
                    if (player.getName().equals(toolCardErrorMessage.getSender())) {
                        /*finds active player*/
                        if (this.checkLathekinBackup(toolCard, player)) {
                            toolCard.setAllEffectsNotDone();
                            model.updateGameboard();
                        } else {
                            if (toolCard.isThereAnyEffectDone()) {
                                /*tool cards has been partially used so
                                 * player tokens will be decreased anyway*/
                                player.decreaseFavorTokens(toolCard.isFirstUsage());
                                ToolCardMove toolCardMove;
                                if (model.isFirstDraftOfDice()) {
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
                        }
                    }
                }
            }
        }
    }

    /*toolcard controller*/
    @Override
    public void update(UseToolCardMove useToolCardMove) {
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
                            new FileParser().searchIDByNumber(Model.FOLDER_ADDRESS_TOOL_CARDS, 5)) ||
                            activeToolCard.getIdentificationName().equals(
                            new FileParser().searchIDByNumber(Model.FOLDER_ADDRESS_TOOL_CARDS, 12))
                            ){
                        valuesBuilder.append("RoundTrack: ");
                        for(int i=0; i<model.getRoundNumber(); i++){
                            valuesBuilder.append(model.getGameBoard().getRoundDice()[i].toString())
                                    .append(" \n ");
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
                    System.out.println("player can use tool card");
                    valuesBuilder.append("ToolCardName: ")
                            .append(activeToolCard.getIdentificationName())
                            .append(" ");
                    if(activeToolCard.getIdentificationName().equals(
                            new FileParser().searchIDByNumber(Model.FOLDER_ADDRESS_TOOL_CARDS, 5)) ||
                            activeToolCard.getIdentificationName().equals(
                                    new FileParser().searchIDByNumber(Model.FOLDER_ADDRESS_TOOL_CARDS, 12))
                            ){
                        valuesBuilder.append("RoundTrack: ");
                        for(int i=0; i<model.getRoundNumber(); i++){
                            valuesBuilder.append(model.getGameBoard().getRoundDice()[i].toString())
                                    .append(" \n ");
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
                    notifyObservers(new ErrorMessage("server", activePlayer.getName(), "PlayerUnableToUseToolCard"));
                }
            }
            else{
                setChanged();
                notifyObservers(new ErrorMessage("server", activePlayer.getName(), "NotEnoughFavorTokens"));
            }
        }
    }

    @Override
    public void update(SendWinnerMessage sendWinnerMessage) {
        /*should never be called here*/
    }

    public void waitSchemaCards(){
        timer = new Timer();
        System.out.println("timer schema card started");
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
        waitMovesTask = new TimerMoves(this, model);
        System.out.println("timer moves started");
        timer.schedule(
                waitMovesTask, time*1000L);
    }

    public void addObserverToModel(ProjectObserver observer){
        model.addObserver(observer);
    }

    public void removeObserverFromModel(ProjectObserver observer){
        model.removeObserver(observer);
    }

    public void setTimer(int time){
        this.time = time;
    }

    /*toolcard controller*/
    private boolean checkLathekinBackup(ToolCard toolCard, Player player){
        FileParser parser = new FileParser();
        if(toolCard.getIdentificationName().equals(
                new FileParser().searchIDByNumber(Model.FOLDER_ADDRESS_TOOL_CARDS, 4)
        )) {
            System.out.println("lathekin quit");
            /*lathekin has been quitted*/
            if(parser.getLathekinOldDiePositions(Model.FOLDER_ADDRESS_TOOL_CARDS)[0] != -1) {
                /*lathekin has been quitted during half of it*/
                System.out.println("has been quitted during half");
                MoveDieOnWindow backupEffect = (MoveDieOnWindow) toolCard.getEffectsList().get(0);
                backupEffect.backupTwoDicePositions(player.getName());
            }
            return true;
        } else {
            return false;
        }
    }

    public void blockPlayer(String username) {
        model.getParticipants().stream().filter(
                p -> p.getName().equals(username)
        ).forEach(
                p -> {
                    p.setConnected(false);
                    System.out.println("setted player not connected: " + p.getName());
                }
        );
        if(countConnectedPlayer()<2){
            model.getParticipants().stream().filter(
                    Player::isConnected
            ).forEach(
                    p -> {
                        System.out.println("players still connected: " + p.getName());
                        model.singlePlayerWinning(p);
                    }
            );
        } else {
            model.getParticipants().stream().filter(
                    p -> p.getName().equals(username)
            ).forEach(
                    player -> update(new NoActionMove(player.getName(), "server"))
            );
        }
    }

    public void unblockPlayer(String username) {
        model.getParticipants().stream().filter(
                p -> p.getName().equals(username)
        ).forEach(
                p -> p.setConnected(true)
        );
        setChanged();
        notifyObservers(new ErrorMessage("server", username, "OldUsernameFound"));
    }

    public long countConnectedPlayer(){
        return model.getParticipants().stream().filter(
                Player::isConnected
        ).count();
    }
}
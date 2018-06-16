package it.polimi.se2018.controller;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.DiceBag;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.game_equipment.RoundDice;
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
    private boolean timerStarted;
    private Timer t;
    private boolean enoughPlayers;
    private int playerNumberDoneSelecting = 0;
    private int allPlayersReady =0;

    /**
     * Class constructor
     */
    public Controller() {
        this.model = new Model();
        timerStarted = false;
        t = new Timer();
        enoughPlayers = false;
    }

    /**
     * Method that randomly extracts game cards
     */
    private void cardsExtraction() {
        model.extractToolCards();
        model.extractPublicObjectiveCards();
    }

    /**
     * Method that randomly extracts 3 public objective cards
     */
    /**
     * Method to roll one single dice, assigning a random int value
     * @param dice dice chosen by the player
     * @throws NullPointerException in case the dice parameter is null
     */
    private void rollSingleDice(Dice dice) {
        dice.setValue((int)Math.ceil(Math.random()*6));
    }

    /**
     * Method to throw the dice at the beginning of a round. It creates a new RoundDice instance for every round and associates it
     * to its position in the RoundTrack array (RoundTrack[round]).
     * @param diceBag the match diceBag already shuffled
     * @param round round number
     * @param participants number of players
     * @throws NullPointerException in case diceBag is null
     */
    private void rollRoundDice(DiceBag diceBag, int round, int participants) {
        RoundDice roundDice = new RoundDice(participants, diceBag, round);
        model.getGameBoard().getRoundTrack().setRoundDice(roundDice, round);
    }


    @Override
    public void update(Message message) {
    }

    public void update(ChooseDiceMove message) {
        System.out.println("STO STAMPANDO SE IL GIOCATORE PUò FARE LA MOSSA");
        System.out.println(model.getParticipants().get(model.getTurnOfTheRound()).
                getPlayerTurns()[model.getRoundNumber()].getTurn1().getDieMove().isBeenUsed());
        if(model.getParticipants().get(model.getTurnOfTheRound()).getPlayerTurns()
                [model.getRoundNumber()].getTurn1().getDieMove().isBeenUsed()
                && model.isFirstDraftOfDice()){
            setChanged();
            notifyObservers(new ErrorMessage("model",model.getParticipants().get(model.getTurnOfTheRound()).getName(),"You have already used all your moves in this round"));
        }
        else if((model.getParticipants().get(model.getTurnOfTheRound()).getPlayerTurns()
                [model.getRoundNumber()].getTurn2().getDieMove().isBeenUsed())){
            setChanged();
            notifyObservers(new ErrorMessage("model",model.getParticipants().get(model.getTurnOfTheRound()).getName(),"You have already used all your moves in this round"));
        }
        else {
            System.out.println(message.getDraftPoolPos() + " ");
            String draftPoolDiePosition = "DraftPoolDiePosition: " + message.getDraftPoolPos();
            setChanged();
            notifyObservers(new RequestMessage("controller", message.getSender(), draftPoolDiePosition, InputManager.INPUT_PLACE_DIE));
        }
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
        if(!timerStarted){
            timerStarted = true;
            System.out.println("timer inizializzato");
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    timerStarted = false;
                    if(!enoughPlayers) {
                        if(model.getParticipants().size()>=2) {
                            /*enough player to start a match*/
                            System.out.println("Time's up, match starting with " + model.getParticipants().size() + " players");
                            model.sendPrivateObjectiveCard();
                        }
                        else {
                            /*Not enough player*/
                            System.out.println("Time's up, minimun player number not reached!");
                            for(Player player: model.getParticipants()){
                                setChanged();
                                notifyObservers(new ErrorMessage("model", player.getName(),"NotEnoughPlayer"));
                            }
                        }
                    }
                }
            }, 1000L*time);
        }
        model.addPlayer(message.getPlayerName());
        if(model.getParticipants().size()==4){
            System.out.println("Maximum player number reached");
            enoughPlayers = true;
            model.sendPrivateObjectiveCard();
        }
    }

    @Override
    public void update(DiePlacementMessage diePlacementMessage) {
        String[] words = diePlacementMessage.getValues().split(" ");
        int row=-1;
        int col = -1;
        int draftPoolPosition=-1;
        //System.out.println(words[0]);
        for(int i =0;i<words.length;i++){
            System.out.println("SONO ENTRATO NEL CICLO");
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

    }

    @Override
    public void update(ErrorMessage errorMessage) {

    }

    @Override
    public void update(GameInitializationMessage gameInitializationMessage) {

    }

    @Override
    public void update(NewRoundMessage newRoundMessage) {

    }

    public void update(SelectedSchemaMessage message) {
        for (int playerPos = 0; playerPos < model.getParticipants().size(); playerPos++) {
            if (model.getParticipants().get(playerPos).getName().equals(message.getSender())) {
                model.setSchemaCardPlayer(playerPos, message.getSchemaCardName());
                playerNumberDoneSelecting++;
            }
        }
        if(playerNumberDoneSelecting == model.getParticipantsNumber()){
            model.extractPublicObjectiveCards();
            model.extractToolCards();
            model.extractRoundTrack();
            model.updateGameboard();
        }
    }

    public void update(NoActionMove message){
        System.out.println(model.getTurnOfTheRound());
        model.updateTurnOfTheRound();
        System.out.println(model.getTurnOfTheRound());
        if(model.getTurnOfTheRound()<0){
            model.updateRound();
            if(model.getRoundNumber()==10){
                //model.countPoints
            }
            else
            model.updateGameboard();
        }
        else model.updateGameboard();
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
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            if(toolCard.getName().equals(toolCardActivationMessage.getToolCardName())){
                /*finds active tool card*/
                for (Player player: model.getParticipants()) {
                    if(player.getName().equals(toolCardActivationMessage.getSender())){
                        /*finds active player*/
                        String values = toolCardActivationMessage.getValues();
                        toolCard.activateToolCard(player.getName(), toolCard.getName(), values, model);
                    }
                }
            }
        }
    }

    @Override
    public void update(ToolCardErrorMessage toolCardErrorMessage) {
        /*shouldn't be called here*/
    }

    @Override
    public void update(UpdateTurnMessage updateTurnMessage) {
        /*shouldn't be called here*/
    }


    @Override
    public void update(UseToolCardMove useToolCardMove) {
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            if(useToolCardMove.getToolCardName().equals(toolCard.getName())){
                /*finds tool card*/
                System.out.println("found card: " + toolCard.getName());
                if(toolCard.isFirstUsage()){
                    System.out.println("first usage");
                    for(Player player: model.getParticipants()){
                        if(player.getName().equals(useToolCardMove.getSender())){
                            System.out.println("found player: " + player.getName());
                            if(player.getFavorTokens()>=1){
                                if(toolCard.checkPlayerAbilityToUseTool(player, toolCard.getName(),
                                        model.getRoundNumber(), model.isFirstDraftOfDice())) {
                                    System.out.println("player can use tool card");
                                    setChanged();
                                    String name = useToolCardMove.getToolCardName();
                                    System.out.println("inputManager: " + toolCard.getInputManagerList().get(0));
                                    notifyObservers(new RequestMessage("server", useToolCardMove.getSender(),
                                            name, toolCard.getInputManagerList().get(0)));
                                } else {
                                    setChanged();
                                    notifyObservers(new ErrorMessage("server", player.getName(),"PlayerUnableToUseToolCard"));
                                }
                            } else {
                                setChanged();
                                notifyObservers(new ErrorMessage("server", player.getName(),"NotEnoughFavorTokens"));
                            }
                        }
                    }
                } else {
                    for(Player player: model.getParticipants()){
                        if(player.getName().equals(useToolCardMove.getSender())){
                            if(player.getFavorTokens()>=2 &&
                                    toolCard.checkPlayerAbilityToUseTool(player, toolCard.getName(),
                                            model.getRoundNumber(), model.isFirstDraftOfDice())){
                                setChanged();
                                String name = useToolCardMove.getToolCardName();
                                notifyObservers(new RequestMessage("server", useToolCardMove.getSender(), name,
                                        toolCard.getInputManagerList().get(0)));
                            }
                            else{
                                setChanged();
                                notifyObservers(new ErrorMessage("server", player.getName(), "NotEnoughFavorTokens"));
                            }
                        }
                    }
                }
            }
        }
    }

    public Model getModel(){
        return model;
    }

    public void setTimer(int time){
        this.time = time;
    }

}
package it.polimi.se2018.model.game_equipment;

import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;

import java.util.ArrayList;

/**
 * Class containing all the gaming strumentation and the logic to handle
 * the modification
 * @author Luciano
 */

public class GameBoard {

    private static final int PUBLIC_OBJECTIVE_CARDS_EXTRACT_NUMBER = 3;
    private static final int TOOL_CARDS_EXTRACT_NUMBER = 3;
    private String matchId;
    private DiceBag diceBag;
    private ObjectiveCard publicObjectiveCards[];
    private ArrayList<ObjectiveCard> privateObjectiveCards;
    private ToolCard toolCards[];
    private RoundTrack roundTrack;
    private PointTrack pointTrack;

    public GameBoard() {
        this.diceBag = new DiceBag();
        this.publicObjectiveCards = new ObjectiveCard[PUBLIC_OBJECTIVE_CARDS_EXTRACT_NUMBER];
        this.privateObjectiveCards = new ArrayList<>();
        this.toolCards = new ToolCard[TOOL_CARDS_EXTRACT_NUMBER];
        this.roundTrack = new RoundTrack();
    }

    public RoundDice[] getRoundDice() {
        return roundTrack.getRoundDice();
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public String getPublicObjectiveCardName(int index) {
        return publicObjectiveCards[index].getName();
    }

    public String getPublicObjectiveCardDescription(int index) {
        return publicObjectiveCards[index].getDescription();
    }

    public String getPrivateObjectiveCardName(int index) {
        return privateObjectiveCards.get(index).getName();
    }

    public String getPrivateObjectiveCardDescription(int index) {
        return privateObjectiveCards.get(index).getDescription();
    }

    public String getToolCardName(int index) {
        return toolCards[index].getName();
    }

    public String getToolCardDescription(int index) {
        return toolCards[index].getDescription();
    }

    public ToolCard[] getToolCards(){
        return toolCards;
    }

    public int getPublicObjectiveCardPoints(int index) {
        return publicObjectiveCards[index].getPoints();
    }

    public String getMatchId() {
        return matchId;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public PointTrack getPointTrack() {
        return pointTrack;
    }

    public void setPublicObjectiveCards (ObjectiveCard publicObjectiveCards, int index) {
        this.publicObjectiveCards[index] = publicObjectiveCards;
    }

    public ObjectiveCard[] getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void setToolCards(ToolCard toolCard, int index) {
        this.toolCards[index] = toolCard;
    }

}

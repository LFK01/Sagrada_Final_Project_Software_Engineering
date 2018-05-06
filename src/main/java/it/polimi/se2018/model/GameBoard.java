package it.polimi.se2018.model;
//Luciano
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;
import it.polimi.se2018.model.objective_cards.InterfaceObjectiveCard;
import it.polimi.se2018.model.tool_cards.AbstractToolCard;
import it.polimi.se2018.model.tool_cards.InterfaceToolCard;

import java.awt.*;
import java.util.ArrayList;

public class GameBoard {

    private String matchId;
    private DiceBag diceBag;
    private InterfaceObjectiveCard publicObjectiveCards[] = new InterfaceObjectiveCard[3];
    private ArrayList<InterfaceObjectiveCard> privateObjectiveCards = new ArrayList<InterfaceObjectiveCard>();
    private InterfaceToolCard toolCards[] = new InterfaceToolCard[3];
    private RoundTrack roundTrack;
    private PointTrack pointTrack;

    public GameBoard(InterfaceObjectiveCard[] publicObjectiveCards, ArrayList<InterfaceObjectiveCard> privateObjectiveCards, InterfaceToolCard[] toolCards) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.privateObjectiveCards = privateObjectiveCards;
        this.toolCards = toolCards;
        this.diceBag = new DiceBag();
    }

    public RoundDice[] getRoundDice() {
        return roundTrack.getRound();
    }

    public String getPublicObjectiveCardName(int index) {
        return publicObjectiveCards[index].getInstance().getName();
    }

    public String getPublicObjectiveCardDescription(int index) {
        return publicObjectiveCards[index].getInstance().getDescription();
    }

    public String getPrivateObjectiveCardName(int index) {
        return privateObjectiveCards.get(index).getInstance().getName();
    }

    public String getPrivateObjectiveCardDescription(int index) {
        return privateObjectiveCards.get(index).getInstance().getDescription();
    }

    public String getToolCardName(int index) {
        return toolCards[index].getInstance().getName();
    }

    public String getToolCardDescription(int index) {
        return toolCards[index].getInstance().getDescription();
    }

    public int getPublicObjectiveCardPoints(int index) {
        return publicObjectiveCards[index].getInstance().getPoints();
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

}

package it.polimi.se2018.model;
//Luciano
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;
import it.polimi.se2018.model.tool_cards.AbstractToolCard;
import it.polimi.se2018.model.tool_cards.InterfaceToolCard;

import java.util.ArrayList;

public class GameBoard {

    private String matchId;
    private DiceBag diceBag;
    private AbstractObjectiveCard publicObjectiveCards[] = new AbstractObjectiveCard[3];
    private ArrayList<AbstractObjectiveCard> privateObjectiveCards = new ArrayList<AbstractObjectiveCard>();
    private AbstractToolCard toolCards[] = new AbstractToolCard[3];
    private RoundTrack roundTrack;
    private PointTrack pointTrack;

    public GameBoard(AbstractObjectiveCard[] publicObjectiveCards, ArrayList<AbstractObjectiveCard> privateObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.privateObjectiveCards = privateObjectiveCards;
        this.diceBag = new DiceBag();
    }

    public RoundDice[] getRoundDice() {
        return roundTrack.getRound();
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

    public String getPublicObjectiveCardPoints(int index) {
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

    public void setToolCards(AbstractToolCard[] toolCards) {
        this.toolCards = toolCards;
    }
}

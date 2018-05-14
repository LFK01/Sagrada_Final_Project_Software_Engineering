package it.polimi.se2018.model;

import it.polimi.se2018.model.exceptions.InvalidColorException;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;
import it.polimi.se2018.controller.tool_cards.*;
import java.util.ArrayList;

/**
 * Class containing all the gaming strumentation and the logic to handle
 * the modification
 * @author Luciano
 */

public class GameBoard {

    private String matchId;
    private DiceBag diceBag;
    private AbstractObjectiveCard publicObjectiveCards[] = new AbstractObjectiveCard[3];
    private ArrayList<AbstractObjectiveCard> privateObjectiveCards = new ArrayList<AbstractObjectiveCard>();
    private AbstractToolCard toolCards[] = new AbstractToolCard[3];
    private RoundTrack roundTrack;
    private PointTrack pointTrack;

    public GameBoard() throws InvalidColorException {
        this.diceBag = new DiceBag();
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

    public AbstractToolCard getToolCard(int index){     //creato da giovanni per model
        return toolCards[index];
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

    public void setPublicObjectiveCards (AbstractObjectiveCard publicObjectiveCards, int index) {
        this.publicObjectiveCards[index] = publicObjectiveCards;
    }

    public void setToolCards(AbstractToolCard[] toolCards) {
        this.toolCards = toolCards;
    }
}

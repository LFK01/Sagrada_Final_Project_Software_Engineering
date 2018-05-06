package it.polimi.se2018.model;
//Luciano
import it.polimi.se2018.model.objective_cards.InterfaceObjectiveCard;
import it.polimi.se2018.model.tool_cards.InterfaceToolCard;

import java.util.ArrayList;

public class GameBoard {

    String matchId;
    DiceBag diceBag;
    InterfaceObjectiveCard publicObjectiveCards[] = new InterfaceObjectiveCard[3];
    ArrayList<InterfaceObjectiveCard> privateObjectiveCards = new ArrayList<InterfaceObjectiveCard>();
    InterfaceToolCard toolCards[] = new InterfaceToolCard[3];
    RoundTrack roundTrack;

    public GameBoard(InterfaceObjectiveCard[] publicObjectiveCards, ArrayList<InterfaceObjectiveCard> privateObjectiveCards, InterfaceToolCard[] toolCards) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.privateObjectiveCards = privateObjectiveCards;
        this.toolCards = toolCards;
    }

    public String getMatchId() {
        return matchId;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }
}

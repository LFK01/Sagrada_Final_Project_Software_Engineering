package it.polimi.se2018.model;
import java.util.*;
//Giorgia

//FUNZIONANTE

public class RoundDice {

    private ArrayList<Dice> diceList = new ArrayList<Dice>();
    private int turn;

    //vengono passati al costruttore il numero del turno e un ArrayList degli eventuali dadi non utilizzati al termine del turno
    public RoundDice(DiceBag diceBag, int currentTurn) {

        for(int i = (currentTurn-1)*(4*2+1) ; i < (currentTurn*(4*2+1)); i++)
                diceList.add(diceBag.getDice(i));

        this.turn = currentTurn;

    }

    public ArrayList<Dice> getDiceList() {

        return diceList;

    }

    public int getTurn() {

        return turn;

    }

    public Dice getDice(int index) {

        return diceList.get(index);

    }

}

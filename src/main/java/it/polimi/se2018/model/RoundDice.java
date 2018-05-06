package it.polimi.se2018.model;
import java.util.*;
//Giorgia

//FUNZIONANTE

public class RoundDice {

    private ArrayList<Dice> diceList = new ArrayList<>();
    private int participantsNumber;
    private int turn;

    //si passano al costruttore il numero dei partecipanti e il numero del turno per calcolare i dadi da pescare, più il sacchetto
    public RoundDice(int participants, DiceBag diceBag, int currentTurn) {

        this.participantsNumber = participants;

        for(int i = (currentTurn-1)*(participantsNumber*2+1) ; i < (currentTurn*(participantsNumber*2+1)); i++)
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

    public int getParticipantsNumber() {
        return participantsNumber;
    }

}

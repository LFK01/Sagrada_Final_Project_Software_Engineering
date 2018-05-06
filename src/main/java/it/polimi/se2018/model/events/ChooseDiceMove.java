package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Dice;

//Giorgia
public class ChooseDiceMove extends PlayerMove {

    //i riferimenti alla View sono momentaneamente commentati in attesa dell'implementazione della classe corrispondente

    private int pos;            //indice di posizione in cui posizionare il dado
    private Dice dice;          //dado da posizionare scelto nel controller

    public ChooseDiceMove(int pos, Dice dice, Player p/*, View v*/) {
        super(p/*, v*/);
        this.dice = dice;
        this.pos = pos;
    }

    public int getPos() {
      return pos;
    }

    public Dice getDice() {
        return dice;
    }

    @Override
    public boolean isDiceMove() {
        return true;
    }

}
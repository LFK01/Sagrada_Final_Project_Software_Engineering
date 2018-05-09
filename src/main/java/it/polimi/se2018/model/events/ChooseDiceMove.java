package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Dice;

/**
 * @author Giorgia
 */
public class ChooseDiceMove extends PlayerMove {

    private int schemaPos;            //indice di posizione in cui posizionare il dado
    private int row;                //dado da posizionare scelto nel controller
    private int col;

    public ChooseDiceMove(int schemaPos, int row, int col, Player player) {
        super(player);
        this.row=row;
        this.col=col;
        this.schemaPos = schemaPos;
    }

    public int getPos() {
      return schemaPos;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean isDiceMove() {
        return true;
    }

}
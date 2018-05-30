package it.polimi.se2018.model.events.moves;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.messages.Message;

/**
 * @author Giorgia
 * edited Giovanni
 */
public class ChooseDiceMove extends Message {

    private int draftPoolPos;
    private int row;                //dado da posizionare scelto nel controller
    private int col;

    /**
     * Initializes ChooseDiceMove with reference of player, die posizione and new die position
     * @param draftPoolPos
     * @param row
     * @param col
     *
     */
    public ChooseDiceMove(String sender,String recipient,int draftPoolPos , int row, int col) {
        super(sender, recipient);
        this.draftPoolPos = draftPoolPos;
        this.row=row;
        this.col=col;
    }

    public int getDraftPoolPos() {
        return draftPoolPos;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    public boolean isDiceMove() {
        return true;
    }


    public boolean isNoActionMove() {
        return false;
    }

}
package it.polimi.se2018.model.events.moves;

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
     */
    public ChooseDiceMove(String sender,String recipient,int draftPoolPos ) {
        super(sender, recipient);
        this.draftPoolPos = draftPoolPos;
    }

    public int getDraftPoolPos() {
        return draftPoolPos;
    }


    public boolean isDiceMove() {
        return true;
    }


    public boolean isNoActionMove() {
        return false;
    }

}
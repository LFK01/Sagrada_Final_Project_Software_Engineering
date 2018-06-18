package it.polimi.se2018.exceptions;

/**Exception thrown when player try to use a toolCard without having enough favorTokens
 * @author Giovanni
 */

public class NotEnoughFavorTokensException extends Exception {
    public NotEnoughFavorTokensException(){
        super("Non hai abbastanza segnalini favore");
    }
}

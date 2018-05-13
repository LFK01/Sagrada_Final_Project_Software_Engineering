package it.polimi.se2018.model;
import static org.junit.Assert.*;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.controller.exceptions.NotEnoughFavorTokensException;
import org.junit.Test;
import java.util.ArrayList;

/**
 * @author Giovanni
 */

public class TestModelClass {
    /**
     *
     */
    @Test
    public void testModelInitialization(){
        GameBoard gameBoard = null;
        ArrayList<Player> partecipants = new ArrayList();
        Model model = null;
        try{
            model = new Model();

        }
        catch(NullPointerException e){
            fail();
        }

    }

    @Test
    public void testHasADieNear(){
        GameBoard gameboard = null;
        ArrayList<Player> partecipants = new ArrayList();
        partecipants.add(new Player("luca"));
        partecipants.add(new Player("marco"));

        Model model = new Model();

        try{
            model.getGameBoard();
        }
        catch(NullPointerException e) {
            fail();
        }
    }
    @Test
    public void testUpdateFavorTokens(){
        Model model = null;
        int a=0,b=0;
        model.addPlayer("luca");
        model.addPlayer("lucio");
        try{
            model.updateFavorTokens(a,b);
        }
        catch(NotEnoughFavorTokensException e){
            System.err.println(e);
        }
    }



}

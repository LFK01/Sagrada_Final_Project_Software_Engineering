package it.polimi.se2018;
import static org.junit.Assert.*;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Player;
import org.junit.Test;
import java.util.ArrayList;


public class TestModelClass {

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
        catch(NullPointerException e){
            fail();
        }



    }



}

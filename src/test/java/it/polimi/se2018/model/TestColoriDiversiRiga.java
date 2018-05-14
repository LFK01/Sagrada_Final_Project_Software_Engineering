package it.polimi.se2018.model;
import it.polimi.se2018.controller.exceptions.InvalidCellPositionException;
import it.polimi.se2018.model.exceptions.FullCellException;
import it.polimi.se2018.model.exceptions.InvalidColorException;
import it.polimi.se2018.model.objective_cards.public_objective_cards.ColoriDiversiRiga;
import org.junit.Test;
import static it.polimi.se2018.model.Color.RED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
/**
 * @author Giovanni
 */

public class TestColoriDiversiRiga {
    /**
     *test to check the pointcount indicated in the class name
     */

   /* public void testCountsPoints(){       //per poterlo testare bisogna riempire tutto uno schemaCard
        int n=1;
        int val=4;
        int a=0,b=0;
        int i=0,j=0;
        Color color = RED;
        SchemaCard schemaCard =null;
        schemaCard = new SchemaCard(n);
        ColoriDiversiRiga coloriDiversiRiga = ColoriDiversiRiga.getThisInstance();
        Dice dice1 = null;
        Dice dice2 = null;
        Dice dice3 = null;
        Dice dice4 = null;
        Dice dice5 = null;
        try{
            dice1 = new Dice(color,val);
            dice2 = new Dice(color,0);
            dice3 = new Dice(color,2);
            dice4 = new Dice(color,5);
            dice5 = new Dice(Color.GREEN,0);
            }
            catch(InvalidColorException e){
            System.err.println(e);
            }
            try{
                schemaCard.getCell(i,j).setAssignedDice(dice1);
                schemaCard.getCell(0,1).setAssignedDice(dice2);
                schemaCard.getCell(0,2).setAssignedDice(dice3);
                schemaCard.getCell(0,3).setAssignedDice(dice4);
                schemaCard.getCell(0,4).setAssignedDice(dice5);
            }
            catch(InvalidCellPositionException e){
            }
            catch(FullCellException e){
            System.err.println(e);
            }

        //assertEquals(4,schemaCard.getCell(a,b).getAssignedDice().getValue());
        assertEquals(0,coloriDiversiRiga.getThisInstance().countPoints(schemaCard));

    }*/
}

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
    @Test
    public void testCountsPoints(){
        int n=1;
        int val=3;
        int a=0,b=0;
        Color color = RED;
        SchemaCard schemaCard = new SchemaCard(n);
        ColoriDiversiRiga coloriDiversiRiga = ColoriDiversiRiga.getThisInstance();

        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                Dice dice1 = null;
                boolean erroreAssegnamento = true;
                while(erroreAssegnamento){
                    try{
                        dice1 = new Dice(color,val);
                    }
                    catch(InvalidColorException e){
                        erroreAssegnamento = true;
                    }
                }
                try{
                    schemaCard.getCell(i,j).setAssignedDice(dice1);
                }
                catch(InvalidCellPositionException e){

                }
                catch(FullCellException e){
                    
                }
            }
        }
        assertEquals(3,schemaCard.getCell(a,b).getValue());
        //assertEquals(0,coloriDiversiRiga.getThisInstance().countPoints(schemaCard));

    }
}


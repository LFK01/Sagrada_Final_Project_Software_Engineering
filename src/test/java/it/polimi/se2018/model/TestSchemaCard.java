package it.polimi.se2018.model;
import static org.junit.Assert.*;
/**
 * @author giovanni
 */

import it.polimi.se2018.model.exceptions.FullCellException;

import it.polimi.se2018.model.exceptions.RestrictionsNotRespectedException;
import jdk.nashorn.internal.ir.CatchNode;
import org.junit.Test;


public class TestSchemaCard {
    @Test
    public void testSchemaCard() {
        int n = 1;
        int a = 0;
        int b = 0;
        SchemaCard schemaCard = null;
        try {
            schemaCard = new SchemaCard(n);
        } catch (NullPointerException e) {
            System.out.println("ciao Luciano");
        }
        //fail();
        assertEquals(4, schemaCard.getCell(a, b).getValue());


    }

   @Test  //errore nel metodo di piazzamwnto del dado
    public void testPlaceDie() {
        SchemaCard schemaCard = new SchemaCard(1);
        Dice dice1 = null;
        Dice dice2 = null;
        Dice dice3 = null;
        Dice dice4 = null;
        Dice dice5 = null;
        Dice dice6 = null;
        Dice dice7 = null;
        Dice dice8 = null;
        Dice dice9 = null;
        Dice dice = null;

        dice1 = new Dice(Color.BLUE, 4);
        dice2 = new Dice(Color.BLUE,0);
        dice3 = new Dice(Color.BLUE,2);
        dice4 = new Dice(Color.BLUE,5);
        dice5 = new Dice(Color.GREEN,0);
        dice6 = new Dice(Color.BLUE,0);
        dice7 = new Dice(Color.BLUE,6);
        dice8 = new Dice(Color.BLUE,6);
        dice9 = new Dice(Color.GREEN,0);
        //dice = new Dice(Color.GREEN, 4);

        try {
            schemaCard.placeDie(dice1, 0, 0);
            schemaCard.placeDie(dice2,0,1);
            schemaCard.placeDie(dice3,0,2);
            schemaCard.placeDie(dice4,0,3);
            schemaCard.placeDie(dice5,1,0);
            schemaCard.placeDie(dice6,1,1);
            schemaCard.placeDie(dice7,1,2);
            schemaCard.placeDie(dice8,1,3);
            schemaCard.placeDie(dice9,2,0);


        }
         catch(RestrictionsNotRespectedException e)

            {
                fail();
            }
        catch(FullCellException i)

            {
                fail();
            }


     /*  try{
            schemaCard.placeDie(dice, 0, 0);

    }
        catch(RestrictionsNotRespectedException e)

    {
        fail();
    }
        catch(FullCellException l)

    {
        fail();
    }*/

      assertEquals(Color.BLUE,schemaCard.getCell(0,0).getAssignedDice().getDiceColor());
    }

}

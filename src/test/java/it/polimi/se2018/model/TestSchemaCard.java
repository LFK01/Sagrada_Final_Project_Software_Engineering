package it.polimi.se2018.model;
import static org.junit.Assert.*;
/**
 * @author giovanni
 */

import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import org.junit.Test;


public class TestSchemaCard {

    @Test
    public void initializationTest(){
        for(int i=2; i<3; i++){
            SchemaCard schemaCard = new SchemaCard(i+1);
            assertNotNull(schemaCard);
        }
    }

    @Test
    public void testSchemaCard() {/*
        int n = 1;
        int a = 0;
        int b = 0;
        SchemaCard schemaCard = null;
        try {
            schemaCard = new SchemaCard(n);
        } catch (NullPointerException e) {
            fail();
        }
        assertEquals(4, schemaCard.getCell(a, b).getValue());
    */}

   @Test
    public void testPlaceDie() throws RestrictionsNotRespectedException, FullCellException {
        /*SchemaCard schemaCard = new SchemaCard(3);
        Dice dice1 = new Dice(Color.RED,5);
        Dice dice2 = new Dice(Color.RED,5);
        schemaCard.placeDie(dice1,0,1,false,false,false);
        assertEquals(5,schemaCard.getCell(0,1).getAssignedDice().getValue());
        schemaCard.placeDie(dice2,0,0,false,false,false);
        //assertEquals(false,schemaCard.getCell(0,1).isFull());
       */

        SchemaCard schemaCard = new SchemaCard(19);
        Dice dice1 = new Dice(Color.RED,5);
        Dice dice2 = new Dice(Color.RED,4);
        Dice dice3 = new Dice(Color.YELLOW,3);
        Dice dice4 = new Dice(Color.BLUE,5);

        schemaCard.placeDie(dice1,0,0,false,false,false);
        //schemaCard.placeDie(dice4,1,2,false,false,false);
        schemaCard.placeDie(dice2,0,1,false,false,false);
        //schemaCard.placeDie(dice2,2,2,false,false,false);




   }

   @Test
    public void schemaTest(){
        SchemaCard schema = new SchemaCard(1);

        for (int i =0; i<4;i++){
            for(int j=0; j<5;j++){
               if(schema.getCell(i,j).getValue()!=0) {
                   System.out.print("[" + schema.getCell(i, j).getValue() + "]");
               }
               else System.out.print("[ ]");
            }
            System.out.println();
        }

   }



}

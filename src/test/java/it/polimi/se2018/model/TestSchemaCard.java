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

        SchemaCard schemaCard = new SchemaCard(1);
        Dice dice1 = new Dice(Color.YELLOW,1);
        Dice dice2 = new Dice(Color.GREEN,4);
        Dice dice3 = new Dice(Color.BLUE,3);
        Dice dice4 = new Dice(Color.YELLOW,5);
        Dice dice5 = new Dice(Color.YELLOW,3);
        Dice dice6 = new Dice(Color.BLUE,6);
        Dice dice7 = new Dice(Color.BLUE,2);
        Dice dice8 = new Dice(Color.YELLOW,3);
    try {
        schemaCard.placeDie(dice1, 0, 0, false, false, false);
    }
    catch(RestrictionsNotRespectedException e){
        fail();
    }
    assertEquals(1,schemaCard.getCell(0,0).getAssignedDice().getValue());
    assertEquals(Color.YELLOW,schemaCard.getCell(0,0).getAssignedDice().getDiceColor());
    try{
        schemaCard.placeDie(dice2,1,0,false,false,false);
    }catch (RestrictionsNotRespectedException e){
        fail();
    }
    assertEquals(4,schemaCard.getCell(1,0).getAssignedDice().getValue());

    try{
        schemaCard.placeDie(dice3,0,1,false,false,false);
    }catch (RestrictionsNotRespectedException e){
        fail();
    }
    try{
        schemaCard.placeDie(dice4,1,1,false,false,false);
    }catch (RestrictionsNotRespectedException e){
        fail();
    }
    try{
        schemaCard.placeDie(dice5,2,0,false,false,false);

    }catch (RestrictionsNotRespectedException e){
        fail();
    }
    try{
        schemaCard.placeDie(dice6,2,1,false,false,false);
    }catch (RestrictionsNotRespectedException e){
        fail();
    }
    try{
        schemaCard.placeDie(dice7,3,0,false,false,false);

    }catch (RestrictionsNotRespectedException e){
        fail();
    }
    try {
        schemaCard.placeDie(dice8,3,1,false,false,false);

    }catch (RestrictionsNotRespectedException e){
        fail();
    }
    }

    @Test
    public void placeDieRightTest() throws RestrictionsNotRespectedException, FullCellException{
        SchemaCard schemaCard = new SchemaCard(1);

        Dice dice1 = new Dice(Color.YELLOW,6);
        Dice dice2 = new Dice(Color.GREEN,2);
        Dice dice3 = new Dice(Color.BLUE,2);
        Dice dice4 = new Dice(Color.YELLOW,6);

        try {
            schemaCard.placeDie(dice1, 3, 4, false, false, false);
        }
        catch (FullCellException e) {
            e.printStackTrace();
        }
        try {
            schemaCard.placeDie(dice2,2,4,false,false,false);
        }catch (RestrictionsNotRespectedException e){
            fail();
        }
        try{
            schemaCard.placeDie(dice3,3,3,false,false,false);

        }catch (RestrictionsNotRespectedException e){
            fail();
        }
        try {
            schemaCard.placeDie(dice4,2,3,false,false,false);
        }catch (RestrictionsNotRespectedException e){
            fail();
        }




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

package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.exceptions.FullCellException;
import it.polimi.se2018.model.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColoriDiversiColonnaTest {

    @Test
    public void countPoints() throws RestrictionsNotRespectedException, FullCellException {
       Model model = new Model();
       model.addPlayer("p1");
       model.addPlayer("p2");
       model.addPlayer("p3");
       SchemaCard schemaCard1 = new SchemaCard(1);
       System.out.println(schemaCard1.toString());
       model.getParticipants().get(0).setSchemaCard(schemaCard1);
       Dice dice1 = new Dice(Color.BLUE,5);
       Dice dice2 = new Dice(Color.GREEN,5);
       Dice dice3 = new Dice(Color.BLUE,3);
       Dice dice4 = new Dice(Color.YELLOW,5);

        Dice dice5 = new Dice(Color.BLUE,5);
        Dice dice6 = new Dice(Color.GREEN,5);
        Dice dice7 = new Dice(Color.RED,3);
        Dice dice8 = new Dice(Color.YELLOW,5);

       schemaCard1.getCell(0,1).setAssignedDice(dice1,false,false);
       schemaCard1.getCell(1,1).setAssignedDice(dice2,false,false);
       schemaCard1.getCell(2,1).setAssignedDice(dice3,false,false);
       schemaCard1.getCell(3,1).setAssignedDice(dice4,false,false);
       schemaCard1.getCell(0,2).setAssignedDice(dice5,false,false);
       schemaCard1.getCell(1,2).setAssignedDice(dice6,false,false);
       schemaCard1.getCell(2,2).setAssignedDice(dice7,false,false);
       schemaCard1.getCell(3,2).setAssignedDice(dice8,false,false);



        //model.getGameBoard().setPublicObjectiveCards(ColoriDiversiColonna.getThisInstance(),0);
        int s = model.getGameBoard().getPublicObjectiveCards()[0].countPoints(model.getParticipants().get(0).getSchemaCard());
        assertEquals(10,s);



    }
}
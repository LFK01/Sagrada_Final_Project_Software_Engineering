package it.polimi.se2018.model.objective_cards.public_objective_cards;

import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class SfumatureChiareTest {

    @Test
    public void countPoints() throws RestrictionsNotRespectedException, FullCellException {
        Model model = new Model();
        model.addPlayer("p1");
        model.addPlayer("p2");
        SchemaCard schemaCard1 = new SchemaCard(1);
        System.out.println(schemaCard1.toString());
        model.getParticipants().get(0).setSchemaCard(schemaCard1);
        Dice dice1 = new Dice(Color.YELLOW,1);
        Dice dice2 = new Dice(Color.GREEN,1);
        Dice dice3 = new Dice(Color.YELLOW,2);


        schemaCard1.getCell(0,0).setAssignedDice(dice1,false,false);
        schemaCard1.getCell(0,2).setAssignedDice(dice2,false,false);
        schemaCard1.getCell(3,4).setAssignedDice(dice3,false,false);

        //model.getGameBoard().setPublicObjectiveCards(SfumatureChiare.getThisInstance(),0);
        int s =  model.getGameBoard().getPublicObjectiveCards()[0].countPoints(model.getParticipants().get(0).getSchemaCard());
        assertEquals(2,s);
    }
}
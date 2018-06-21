package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColumnCardsCSTest {
    @Test
    public void countPointsTest() throws RestrictionsNotRespectedException, FullCellException, RestrictionsNotRespectedException, FullCellException {
        Model model = new Model();
        model.addPlayer("p1");

        Dice dice1 = new Dice(Color.BLUE,4);
        Dice dice2 = new Dice(Color.RED,3);
        Dice dice3 = new Dice(Color.YELLOW,6);
        Dice dice4 = new Dice(Color.GREEN,1);
        Dice dice5 = new Dice(Color.YELLOW,2);
        SchemaCard schemaCard = new SchemaCard(1);

        schemaCard.placeDie(dice5,0,0,false,false,false);
        schemaCard.placeDie(dice1,0,1,false,false,false);
        schemaCard.placeDie(dice2,1,1,false,false,false);
        schemaCard.placeDie(dice3,2,1,false,false,false);
        schemaCard.placeDie(dice4,3,1,false,false,false);
        model.getParticipants().get(0).setSchemaCard(schemaCard);


        ObjectiveCard card1 = new ObjectiveCard(false,1);
        card1.countPoints(model,card1.getName(),card1.getPoints());

        assertEquals(5,model.getParticipants().get(0).getPoints());

    }

}
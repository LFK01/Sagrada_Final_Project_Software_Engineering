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

public class ShadesTest {

    @Test
    public void countPoints() throws RestrictionsNotRespectedException, FullCellException {
        Model model = new Model();
        SchemaCard schemaCard1 = new SchemaCard(1);
        model.addPlayer("p1");
        Dice dice1 = new Dice(Color.YELLOW,6);
        Dice dice2 = new Dice(Color.BLUE,5);
        Dice dice3 = new Dice(Color.GREEN,6);
        Dice dice4 = new Dice(Color.RED,5);
        schemaCard1.placeDie(dice1,0,0,false,false,false);
        schemaCard1.placeDie(dice2,0,1,false,false,false);
        schemaCard1.placeDie(dice3,1,1,false,false,false);
        schemaCard1.placeDie(dice4,2,2,false,false,false);
        model.getParticipants().get(0).setSchemaCard(schemaCard1);
        ObjectiveCard card1 = new ObjectiveCard(false,9);
        card1.countPoints(model,card1.getName(),card1.getPoints());
        assertEquals(4,model.getParticipants().get(0).getPoints());
    }

}
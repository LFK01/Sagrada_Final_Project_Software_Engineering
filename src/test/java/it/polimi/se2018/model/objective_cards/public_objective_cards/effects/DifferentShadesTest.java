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

public class DifferentShadesTest {

    @Test
    public void countPoints() throws RestrictionsNotRespectedException, FullCellException {

        Model model = new Model();
        model.addPlayer("p1");

        SchemaCard schemaCard = new SchemaCard(1);

        Dice dice1 = new Dice(Color.RED,2);
        Dice dice2 = new Dice(Color.PURPLE,1);
        Dice dice3 = new Dice(Color.RED,3);
        Dice dice4 = new Dice(Color.BLUE,4);
        Dice dice5 = new Dice(Color.YELLOW,6);
        Dice dice6 = new Dice(Color.YELLOW,3);
        Dice dice7 = new Dice(Color.GREEN,5);

        schemaCard.placeDie(dice1,3,0,false,false,false);
        schemaCard.placeDie(dice2,3,1,false,false,false);
        schemaCard.placeDie(dice3,3,2,false,false,false);
        schemaCard.placeDie(dice4,3,3,false,false,false);
        schemaCard.placeDie(dice5,3,4,false,false,false);
        schemaCard.placeDie(dice6,2,0,false,false,false);
        schemaCard.placeDie(dice7,2,4,false,false,false);

        model.getParticipants().get(0).setSchemaCard(schemaCard);
        ObjectiveCard card = new ObjectiveCard(false,5);
        card.countPoints(model,card.getName(),card.getPoints());
        assertEquals(5,model.getParticipants().get(0).getPoints());
    }
}
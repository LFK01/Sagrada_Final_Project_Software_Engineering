package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;
import org.junit.Test;

import javax.xml.validation.Schema;

import static org.junit.Assert.*;

public class ColoredDiagonalsTest {

    @Test
    public void countPoints() throws RestrictionsNotRespectedException, FullCellException {
        Model model = new Model();
        model.addPlayer("p1");
        SchemaCard schemaCard = new SchemaCard(14);

        Dice dice1 = new Dice(Color.YELLOW,1);
        Dice dice2 = new Dice(Color.GREEN,2);
        Dice dice3 = new Dice(Color.YELLOW,3);
        Dice dice4 = new Dice(Color.YELLOW,3);
        Dice dice5 = new Dice(Color.YELLOW,3);
        Dice dice6 = new Dice(Color.PURPLE,6);
        Dice dice7 = new Dice(Color.RED,2);
        Dice dice8 = new Dice(Color.YELLOW,1);
        Dice dice9 = new Dice(Color.YELLOW,4);
        Dice dice10 = new Dice(Color.GREEN,6);
        Dice dice11 = new Dice(Color.YELLOW,4);
        Dice dice12 = new Dice(Color.GREEN,3);
        Dice dice13 = new Dice(Color.GREEN,1);

        schemaCard.placeDie(dice1,0,0,false,false,false);
        schemaCard.placeDie(dice6,0,1,false,false,false);
        schemaCard.placeDie(dice7,0,2,false,false,false);
        schemaCard.placeDie(dice8,0,3,false,false,false);
        schemaCard.placeDie(dice2,1,0,false,false,false);
        schemaCard.placeDie(dice3,2,0,false,false,false);
        schemaCard.placeDie(dice11,1,1,false,false,false);
        schemaCard.placeDie(dice4,2,2,false,false,false);
        schemaCard.placeDie(dice5,3,3,false,false,false);
        schemaCard.placeDie(dice9,1,4,false,false,false);
        schemaCard.placeDie(dice10,1,2,false,false,false);
        schemaCard.placeDie(dice12,2,1,false,false,false);
        schemaCard.placeDie(dice13,3,0,false,false,false);
        model.getParticipants().get(0).setSchemaCard(schemaCard);
        ObjectiveCard card = new ObjectiveCard(false,3);
        card.countPoints(model,card.getName(),card.getPoints());
        System.out.println(schemaCard);
        assertEquals(13,model.getParticipants().get(0).getPoints());

    }
}
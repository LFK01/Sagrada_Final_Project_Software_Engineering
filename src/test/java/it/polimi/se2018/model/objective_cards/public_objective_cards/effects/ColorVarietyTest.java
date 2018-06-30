package it.polimi.se2018.model.objective_cards.public_objective_cards.effects;

import it.polimi.se2018.exceptions.FullCellException;
import it.polimi.se2018.exceptions.RestrictionsNotRespectedException;
import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.Dice;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColorVarietyTest {

    @Test
    public void countPoints() throws RestrictionsNotRespectedException, FullCellException {
        FileParser parser = new FileParser();
        Model model = new Model();
        model.addPlayer("p1");

        SchemaCard schemaCard = new SchemaCard(1);
        Dice dice1 = new Dice(Color.YELLOW,1);
        Dice dice2 = new Dice(Color.GREEN,2);
        Dice dice3 = new Dice(Color.PURPLE,3);
        Dice dice4 = new Dice(Color.RED,2);
        Dice dice5 = new Dice(Color.BLUE,5);
        Dice dice6 = new Dice(Color.PURPLE,6);
        Dice dice7 = new Dice(Color.RED,3);
        Dice dice8 = new Dice(Color.YELLOW,1);
        Dice dice9 = new Dice(Color.BLUE,4);
        Dice dice10 = new Dice(Color.GREEN,6);

        model.getParticipants().get(0).setSchemaCard(schemaCard);
        schemaCard.placeDie(dice1,0,0,false,false,false);
        schemaCard.placeDie(dice2,1,0,false,false,false);
        schemaCard.placeDie(dice3,2,0,false,false,false);
        schemaCard.placeDie(dice4,3,0,false,false,false);
        schemaCard.placeDie(dice5,0,1,false,false,false);
        schemaCard.placeDie(dice6,0,2,false,false,false);
        schemaCard.placeDie(dice7,0,3,false,false,false);
        schemaCard.placeDie(dice8,0,4,false,false,false);
        schemaCard.placeDie(dice9,1,4,false,false,false);
        schemaCard.placeDie(dice10,2,4,false,false,false);


        ObjectiveCard objectiveCard = parser.createObjectiveCard(false,10);
        objectiveCard.countPoints(model,objectiveCard.getName(),objectiveCard.getPoints());
        assertEquals(8,model.getParticipants().get(0).getPoints());
    }
}
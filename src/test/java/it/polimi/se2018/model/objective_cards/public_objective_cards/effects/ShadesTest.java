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

public class ShadesTest {

    @Test
    public void countPoints() throws RestrictionsNotRespectedException, FullCellException {
        Model model = new Model();
        FileParser parser = new FileParser();
        SchemaCard schemaCard1 = parser.createSchemaCardByNumber(Model.FILE_ADDRESS_SCHEMA_CARDS, 1);
        SchemaCard schemaCard2 = parser.createSchemaCardByNumber(Model.FILE_ADDRESS_SCHEMA_CARDS, 1);
        model.addPlayer("p1");
        model.addPlayer("p2");
        Dice dice1 = new Dice(Color.YELLOW,6);
        Dice dice2 = new Dice(Color.BLUE,5);
        Dice dice3 = new Dice(Color.GREEN,6);
        Dice dice4 = new Dice(Color.RED,5);
        Dice dice5 = new Dice(Color.YELLOW,6);
        Dice dice6 = new Dice(Color.BLUE,5);
        Dice dice7 = new Dice(Color.YELLOW,6);

        Dice dice8 = new Dice(Color.YELLOW,6);
        Dice dice9 = new Dice(Color.BLUE,5);
        Dice dice10 = new Dice(Color.GREEN,6);
        Dice dice11 = new Dice(Color.RED,5);
        Dice dice12 = new Dice(Color.YELLOW,6);
        Dice dice13 = new Dice(Color.BLUE,5);
        Dice dice14 = new Dice(Color.YELLOW,6);

        schemaCard1.placeDie(dice1,0,0,false,false,false);
        schemaCard1.placeDie(dice2,0,1,false,false,false);
        schemaCard1.placeDie(dice5,0,2,false,false,false);
        schemaCard1.placeDie(dice6,0,3,false,false,false);
        schemaCard1.placeDie(dice7,1,3,false,false,false);
        schemaCard1.placeDie(dice3,1,1,false,false,false);
        schemaCard1.placeDie(dice4,2,2,false,false,false);

        schemaCard2.placeDie(dice8,0,0,false,false,false);
        schemaCard2.placeDie(dice9,0,1,false,false,false);
        schemaCard2.placeDie(dice12,0,2,false,false,false);
        schemaCard2.placeDie(dice13,0,3,false,false,false);
        schemaCard2.placeDie(dice14,1,3,false,false,false);
        schemaCard2.placeDie(dice10,1,1,false,false,false);
        schemaCard2.placeDie(dice11,2,2,false,false,false);

        model.getParticipants().get(0).setSchemaCard(schemaCard1);
        model.getParticipants().get(1).setSchemaCard(schemaCard2);

        ObjectiveCard card1 = parser.createObjectiveCard(false,9);
        card1.countPoints(model,card1.getName(),card1.getPoints());
        assertEquals(6,model.getParticipants().get(0).getPoints());
        assertEquals(6,model.getParticipants().get(1).getPoints());
    }

}
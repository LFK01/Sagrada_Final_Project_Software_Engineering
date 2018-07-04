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

public class ColoredDiagonalsTest {

    @Test
    public void countPoints() throws RestrictionsNotRespectedException, FullCellException {
        FileParser parser = new FileParser();
        Model model = new Model();
        model.addPlayer("p1");
        SchemaCard schemaCard = parser.createSchemaCardByNumber(Model.FOLDER_ADDRESS_SCHEMA_CARDS, 1);
        Dice dice1 = new Dice(Color.YELLOW,1);
        Dice dice2 = new Dice(Color.GREEN,2);
        Dice dice3 = new Dice(Color.YELLOW,3);
        Dice dice4 = new Dice(Color.RED,3);
        Dice dice5 = new Dice(Color.BLUE,3);
        Dice dice6 = new Dice(Color.BLUE,6);
        Dice dice7 = new Dice(Color.RED,2);
        Dice dice8 = new Dice(Color.YELLOW,1);
        Dice dice9 = new Dice(Color.YELLOW,4);
        Dice dice10 = new Dice(Color.GREEN,5);
        Dice dice11 = new Dice(Color.YELLOW,4);
        Dice dice12 = new Dice(Color.GREEN,5);
        Dice dice13 = new Dice(Color.GREEN,2);

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
        ObjectiveCard card = parser.createObjectiveCard(false,3);
        card.countPoints(model,card.getName(),card.getPoints());
        System.out.println(schemaCard);
        assertEquals(11,model.getParticipants().get(0).getPoints());
    }

    @Test
    public void countPointsDifferentSchema() throws RestrictionsNotRespectedException, FullCellException {
        FileParser parser = new FileParser();
        Model model = new Model();
        model.addPlayer("p1");
        SchemaCard schemaCard = parser.createSchemaCardByNumber(Model.FOLDER_ADDRESS_SCHEMA_CARDS,24);

        Dice dice1 = new Dice(Color.PURPLE,1);
        Dice dice2 = new Dice(Color.PURPLE,1);
        Dice dice3 = new Dice(Color.PURPLE,6);
        Dice dice4 = new Dice(Color.PURPLE,6);
        Dice dice5 = new Dice(Color.GREEN,6);
        Dice dice6 = new Dice(Color.GREEN,5);
        Dice dice7 = new Dice(Color.PURPLE,5);
        Dice dice8 = new Dice(Color.PURPLE,4);
        Dice dice9 = new Dice(Color.GREEN,2);
        Dice dice10 = new Dice(Color.GREEN,3);
        Dice dice11 = new Dice(Color.GREEN,2);
        Dice dice12 = new Dice(Color.GREEN,6);
        Dice dice13 = new Dice(Color.PURPLE,3);
        Dice dice14 = new Dice(Color.PURPLE,6);
        Dice dice15 = new Dice(Color.PURPLE,1);
        Dice dice16 = new Dice(Color.BLUE,2);
        Dice dice17 = new Dice(Color.BLUE,1);
        Dice dice18 = new Dice(Color.BLUE,5);
        Dice dice19 = new Dice(Color.PURPLE,6);
        Dice dice20 = new Dice(Color.BLUE,4);


        schemaCard.placeDie(dice1,0,0,false,false,false);
        schemaCard.placeDie(dice2,1,1,false,false,false);
        schemaCard.placeDie(dice3,2,2,false,false,false);
        schemaCard.placeDie(dice4,3,3,false,false,false);
        schemaCard.placeDie(dice5,0,1,false,false,false);
        schemaCard.placeDie(dice6,1,0,false,false,false);
        schemaCard.placeDie(dice7,0,2,false,false,false);
        schemaCard.placeDie(dice8,2,0,false,false,false);
        schemaCard.placeDie(dice9,0,3,false,false,false);
        schemaCard.placeDie(dice10,1,2,false,false,false);
        schemaCard.placeDie(dice11,2,1,false,false,false);
        schemaCard.placeDie(dice12,3,0,false,false,false);
        schemaCard.placeDie(dice13,0,4,false,false,false);
        schemaCard.placeDie(dice14,1,3,false,false,false);
        schemaCard.placeDie(dice15,3,1,false,false,false);
        schemaCard.placeDie(dice16,1,4,false,false,false);
        schemaCard.placeDie(dice17,2,3,false,false,false);
        schemaCard.placeDie(dice18,3,2,false,false,false);
        schemaCard.placeDie(dice19,2,4,false,false,false);
        schemaCard.placeDie(dice20,3,4,false,false,false);

        model.getParticipants().get(0).setSchemaCard(schemaCard);
        ObjectiveCard card = parser.createObjectiveCard(false,3);
        card.countPoints(model,card.getName(),card.getPoints());
        System.out.println(schemaCard);
        assertEquals(33,model.getParticipants().get(0).getPoints());
    }
}
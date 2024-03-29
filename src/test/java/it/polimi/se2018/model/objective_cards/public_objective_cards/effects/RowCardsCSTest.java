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

public class RowCardsCSTest {

    @Test
    public void countPointsTest() throws RestrictionsNotRespectedException, FullCellException {
        Model model = new Model();
        model.addPlayer("p1");

        FileParser parser = new FileParser();
        Dice dice1 = new Dice(Color.BLUE,4);
        Dice dice2 = new Dice(Color.RED,5);
        Dice dice3 = new Dice(Color.YELLOW,6);
        Dice dice4 = new Dice(Color.GREEN,4);
        Dice dice5 = new Dice(Color.PURPLE,3);
        SchemaCard schemaCard = parser.createSchemaCardByNumber(Model.FOLDER_ADDRESS_SCHEMA_CARDS, 1);

        schemaCard.placeDie(dice5,2,0,false,false,false);
        schemaCard.placeDie(dice1,2,1,false,false,false);
        schemaCard.placeDie(dice2,2,2,false,false,false);
        schemaCard.placeDie(dice3,2,3,false,false,false);
        schemaCard.placeDie(dice4,2,4,false,false,false);
        model.getParticipants().get(0).setSchemaCard(schemaCard);


        ObjectiveCard card1 = parser.createObjectiveCard(Model.OBJECTIVE_CARD_FILE_ADDRESS, false,7);
        card1.countPoints(model,card1.getName(),card1.getPoints());

        assertEquals(5,model.getParticipants().get(0).getPoints());

    }

}
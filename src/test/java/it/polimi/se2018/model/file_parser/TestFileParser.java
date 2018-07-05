package it.polimi.se2018.model.file_parser;

import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;
import org.junit.Test;

public class TestFileParser {

    @Test
    public void objectiveCard(){
        FileParser parser = new FileParser();
        for (int i=1; i<=Model.PUBLIC_OBJECTIVE_CARDS_NUMBER; i++){
            ObjectiveCard card = parser.createObjectiveCard(Model.OBJECTIVE_CARD_FILE_ADDRESS, false, i);
            System.out.println("card: " + card.toString());
            System.out.println("description: " + card.getDescription());
        }
    }

    @Test
    public void constructorToolCardTest(){
        for(int i = 1; i<=12; i++){
            String fileAddress = Model.FOLDER_ADDRESS_TOOL_CARDS;
            FileParser parser = new FileParser();
            ToolCard toolCard = parser.createToolCard(fileAddress, i);
        }
    }

    @Test
    public void constructorSchemaCardTest(){
        for(int i=1; i<=Model.SCHEMA_CARDS_NUMBER+1; i++){
            FileParser parser = new FileParser();
            SchemaCard nameSchema = parser.createSchemaCardByNumber(Model.FOLDER_ADDRESS_SCHEMA_CARDS, i);
            SchemaCard testSchema = parser.createSchemaCardByName(Model.FOLDER_ADDRESS_SCHEMA_CARDS, nameSchema.getName());
            System.out.println(testSchema.toString());
        }
    }

    @Test
    public void searchIDByNumber(){
        FileParser parser = new FileParser();
        for(int i=1; i<=Model.TOOL_CARDS_NUMBER; i++){
            System.out.println("Toolcard #" + i + " :" + parser.searchIDByNumber(Model.FOLDER_ADDRESS_TOOL_CARDS, i));
        }
    }

    @Test
    public void getTapWheelUsingValue(){
        FileParser parser = new FileParser();
        System.out.println("UsingValue: " + parser.getTapWheelUsingValue(Model.FOLDER_ADDRESS_TOOL_CARDS));
    }

    @Test
    public void getTapWheelFirstColor(){
        FileParser parser = new FileParser();
        System.out.println("Color: " + parser.getTapWheelFirstColor(Model.FOLDER_ADDRESS_TOOL_CARDS));
    }

    @Test
    public void writeTapWheelFirstColorTest(){
        FileParser parser = new FileParser();
        parser.writeTapWheelFirstColor(Model.FOLDER_ADDRESS_TOOL_CARDS, Color.RED.toString());
    }

    @Test
    public void writeTapWheelUsingValueTest(){
        FileParser parser = new FileParser();
        parser.writeTapWheelUsingValue(Model.FOLDER_ADDRESS_TOOL_CARDS, false);
    }

    @Test
    public void getLathekinOldDiePositions(){
        FileParser parser = new FileParser();
        int oldPositions[];
        oldPositions = parser.getLathekinOldDiePositions(Model.FOLDER_ADDRESS_TOOL_CARDS);
        System.out.println("old 1: " + oldPositions[0]);
        System.out.println("old 2: " + oldPositions[1]);
    }

    @Test
    public void getLathekinNewDiePositions(){
        FileParser parser = new FileParser();
        int newPositions[];
        newPositions = parser.getLathekinNewDiePositions(Model.FOLDER_ADDRESS_TOOL_CARDS);
        System.out.println("new 1: " + newPositions[0]);
        System.out.println("new 2: " + newPositions[1]);
    }

    @Test
    public void writeLathekinPositions(){
        FileParser parser = new FileParser();
        int[] randomValues = {7,8,9,10};
        parser.writeLathekinPositions(Model.FOLDER_ADDRESS_TOOL_CARDS,
                randomValues[0], randomValues[1], randomValues[2], randomValues[3]);
    }
}

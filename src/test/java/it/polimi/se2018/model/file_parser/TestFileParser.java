package it.polimi.se2018.model.file_parser;

import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Color;
import org.junit.Test;

public class TestFileParser {

    @Test
    public void searchIDByNumber(){
        FileParser parser = new FileParser();
        for(int i=1; i<=Model.TOOL_CARDS_NUMBER; i++){
            System.out.println("Toolcard #" + i + " :" + parser.searchIDByNumber(Model.FILE_ADDRESS_TOOL_CARDS, i));
        }
    }

    @Test
    public void getTapWheelUsingValue(){
        FileParser parser = new FileParser();
        System.out.println("UsingValue: " + parser.getTapWheelUsingValue(Model.FILE_ADDRESS_TOOL_CARDS));
    }

    @Test
    public void getTapWheelFirstColor(){
        FileParser parser = new FileParser();
        System.out.println("Color: " + parser.getTapWheelFirstColor(Model.FILE_ADDRESS_TOOL_CARDS));
    }

    @Test
    public void writeTapWheelFirstColorTest(){
        FileParser parser = new FileParser();
        parser.writeTapWheelFirstColor(Model.FILE_ADDRESS_TOOL_CARDS, Color.RED);
    }

    @Test
    public void writeTapWheelUsingValueTest(){
        FileParser parser = new FileParser();
        parser.writeTapWheelUsingValue(Model.FILE_ADDRESS_TOOL_CARDS, false);
    }
}

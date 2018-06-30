package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.file_parser.FileParser;
import org.junit.Test;

public class TestToolCard {

    @Test
    public void constructorToolCardTest(){
        for(int i = 1; i<=12; i++){
            String fileAddress = "src\\main\\java\\it\\polimi\\se2018\\controller\\tool_cards\\resources";
            FileParser parser = new FileParser();
            ToolCard toolCard = parser.createToolCard(fileAddress, i);
            /*
            System.out.println(toolCard.getName());
            System.out.println(toolCard.getDescription());
            System.out.println(toolCard.getInputManagerList().get(0));*/
        }
    }
}

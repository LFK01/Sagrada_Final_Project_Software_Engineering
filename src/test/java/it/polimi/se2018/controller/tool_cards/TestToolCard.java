package it.polimi.se2018.controller.tool_cards;

import org.junit.Test;

public class TestToolCard {

    @Test
    public void constructorToolCardTest(){
        for(int i = 1; i<=12; i++){
            ToolCard toolCard = new ToolCard(i);
            System.out.println(toolCard.getName());
            System.out.println(toolCard.getDescription());
            System.out.println(toolCard.getInputManagerList().get(0));
        }
    }
}

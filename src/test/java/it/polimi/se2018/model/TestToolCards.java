package it.polimi.se2018.model;

import it.polimi.se2018.controller.tool_cards.ToolCard;
import org.junit.Test;

public class TestToolCards {

    @Test
    public void toolCardExtractionTest(){
        Model model = new Model();
        model.extractToolCards();
        for(ToolCard toolCard: model.getGameBoard().getToolCards()){
            System.out.println(toolCard.getName());
            System.out.println(toolCard.getDescription());
            System.out.println(toolCard.getInputManagerList().get(0));
        }
    }
}
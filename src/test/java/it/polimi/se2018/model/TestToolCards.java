package it.polimi.se2018.model;

import it.polimi.se2018.model.events.messages.Message;
import it.polimi.se2018.model.tool_cards.AbstractToolCard;
import it.polimi.se2018.view.comand_line.InputManager;
import org.junit.Test;

public class TestToolCards {

    private static final int TOOL_CARDS_NUMBER=12;
    @Test
    public void toolCardsTest(){
        for(int i=1; i<=TOOL_CARDS_NUMBER; i++){
            ConcreteToolCard concreteToolCard = new ConcreteToolCard(i);
            System.out.println(concreteToolCard.getName());
            System.out.println(concreteToolCard.getDescription());
            System.out.println(concreteToolCard.isFirstUsage());
        }
    }
}


class ConcreteToolCard extends AbstractToolCard{

    public ConcreteToolCard(int number) {
        super(number);
    }

    @Override
    public void activateToolCard(String username, String name, String values, Model model) {

    }

    @Override
    public InputManager getInputManager(String name) {
        return null;
    }

}
package it.polimi.se2018.controller.tool_cards;
//Luciano
import it.polimi.se2018.model.Player;

public interface InterfaceToolCard {

    void activateCard(Player player);
    AbstractToolCard getInstance();

}

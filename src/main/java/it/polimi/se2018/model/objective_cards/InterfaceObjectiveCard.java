package it.polimi.se2018.model.objective_cards;
//Luciano
import it.polimi.se2018.model.Player;

public interface InterfaceObjectiveCard {

    void countPoints(Player player);
    AbstractObjectiveCard getInstance();

}

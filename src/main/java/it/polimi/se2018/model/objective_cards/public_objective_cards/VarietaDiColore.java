package it.polimi.se2018.model.objective_cards.public_objective_cards;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.SchemaCard;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;

/**
 * @author Giovanni
 */

public class VarietaDiColore extends AbstractObjectiveCard{

    private static VarietaDiColore thisInstance;

    private VarietaDiColore(){
        super("varietaDicolore","Set di dadi di ogni colore ovunque", "#",false );

    }

    public static VarietaDiColore getThisInstance(){
    if(thisInstance==null){
        thisInstance= new VarietaDiColore();
        }
    return thisInstance;
    }

    @Override
    public int countPoints(SchemaCard schemaCard){
        return 0;
    }
}


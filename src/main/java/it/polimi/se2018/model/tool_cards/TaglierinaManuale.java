package it.polimi.se2018.model.tool_cards;

/**
 * @author Luciano
 */

public class TaglierinaManuale extends AbstractToolCard {

    private static TaglierinaManuale thisInstance;

    private TaglierinaManuale() {
        super("Taglierina Manuale", "Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round. Devi rispettare tutte le restrizioni di piazzamento", true);
    }

    public static synchronized TaglierinaManuale getThisInstance(){
        if(thisInstance==null){
            thisInstance = new TaglierinaManuale();
        }
        return thisInstance;
    }

}

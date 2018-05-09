package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Player;

public class TaglierinaCircolare extends AbstractToolCard {

    private static TaglierinaCircolare thisInstance;

    public TaglierinaCircolare() {
        super("Taglierina circolare", "Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round", true);
    }

    public static TaglierinaCircolare getThisInstance(){
        if(thisInstance==null){
            thisInstance = new TaglierinaCircolare();
        }
        return thisInstance;
    }

    @Override
    public void activateCard(Player player) {

    }
}

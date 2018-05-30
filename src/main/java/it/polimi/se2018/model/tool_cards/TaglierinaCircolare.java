package it.polimi.se2018.model.tool_cards;

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


}

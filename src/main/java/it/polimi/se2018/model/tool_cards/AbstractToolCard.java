package it.polimi.se2018.model.tool_cards;

/**
 * @author Luciano
 */
public abstract class AbstractToolCard {

    protected String name;
    protected String description;
    protected boolean firstUsage;

    public AbstractToolCard(String name, String description, boolean firstUsage) {
        this.name = name;
        this.description = description;
        this.firstUsage = firstUsage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFirstUsage() {
        return firstUsage;
    }

    public void isBeingUsedForTheFirstTime(){
        if(firstUsage){
            firstUsage = false;
        }
        else{
            //throw new ToolCardAlreadyBeenUsedException();
        }
    }
}

package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Player;

/**
 * @author Luciano
 */
abstract public class AbstractToolCard {

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

    public abstract void activateCard(Player player);
}

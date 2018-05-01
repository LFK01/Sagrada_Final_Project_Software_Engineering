package it.polimi.se2018.model;

abstract public class AbstractToolCard {

    private String name;
    private String description;
    private boolean firstUsage;

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
}

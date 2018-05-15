package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.view.View;

import java.util.*;

/**
 * Class to handle the game initialization (cards extraction and dealing)
 * @author Giorgia
 */
public class GameInitialization implements Observer {

    private Model model;
    private View view;

    public GameInitialization(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    private void cardsExtraction() {
        model.extractToolCards();
        model.extractPublicObjectiveCards();
    }

    @Override
    public void update(Observable object, Object message) {

        /*quando tutti i giocatori hanno scelto la propria carta schema */
    }

}

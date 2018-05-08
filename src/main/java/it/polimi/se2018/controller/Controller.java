package it.polimi.se2018.controller;
import it.polimi.se2018.controller.tool_cards.*;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.ChooseDiceMove;
import it.polimi.se2018.model.events.PlayerMove;
import it.polimi.se2018.model.events.UseToolCardMove;
import it.polimi.se2018.model.objective_cards.AbstractObjectiveCard;
import it.polimi.se2018.model.objective_cards.public_objective_cards.SfumatureDiverseRiga;

import java.util.*;

//Giorgia

public class Controller {

    private Model model;
    private View view;
    private AbstractToolCard[] toolCards;
    private AbstractObjectiveCard[] publicObjectiveCards;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void extractToolCards() {

        ArrayList<Integer> cardIndex = new ArrayList<>(12);

        for(int i = 1; i <= 12; i++)
            cardIndex.add(i);

        Collections.shuffle(cardIndex);

        for(int i = 0; i < 3; i++) {

            switch (cardIndex.get(i)) {

                case 1:
                    toolCards[i] = PinzaSgrossatrice.getThisIstance();
                    break;

                case 2:
                    toolCards[i] = PennelloPerEglomise.getThisIstance();
                    break;

                case 3:
                    toolCards[i] = AlesatorePerLaminaDiRame.getThisInstance();
                    break;

                case 4:
                    toolCards[i] = Lathekin.getThisInstance();
                    break;

                case 5:
                    toolCards[i] = TaglierinaCircolare.getThisInstance();
                    break;

                case 6:
                    toolCards[i] = PennelloPerPastaSalda.getThisInstance();
                    break;

                case 7:
                    toolCards[i] = Martelletto.getThisInstance();
                    break;

                case 8:
                    toolCards[i] = TenagliaARotelle.getThisInstance();
                    break;

                case 9:
                    toolCards[i] = RigaInSughero.getThisInstance();
                    break;

                case 10:
                    toolCards[i] = TamponeDiamantato.getThisInstance();
                    break;

                case 11:
                    toolCards[i] = DiluentePerPastaSalda.getThisInstance();
                    break;

                case 12:
                    toolCards[i] = TaglierinaManuale.getThisInstance();
                    break;

            }

        }

        model.getGameBoard().setToolCards(toolCards);
        //messaggi
    }

    public void dealPrivateObjectiveCards() {

        ArrayList<Integer> cardIndex = new ArrayList<>(12);

        for(int i = 1; i <= 5; i++)
            cardIndex.add(i);

        Collections.shuffle(cardIndex);

        for(int i = 0; i < model.getParticipants(); i++) {

            switch(cardIndex.get(i)) {

                case 1:
                    model.getPlayer(i).setPrivateObjectiveCard(new SfumatureRosse().getThisInstance());
                    break;

                case 2:
                    model.getPlayer(i).setPrivateObjectiveCard(new SfumatureGialle().getThisInstance());
                    break;

                case 3:
                    model.getPlayer(i).setPrivateObjectiveCard(new SfumatureVerdi().getThisInstance());
                    break;

                case 4:
                    model.getPlayer(i).setPrivateObjectiveCard(new SfumatureBlu().getThisInstance());
                    break;

                case 5:
                    model.getPlayer(i).setPrivateObjectiveCard(new SfumatureViola().getThisInstance());
                    break;

            }

        }
        //messaggi

    }

    public void extractPublicObjectiveCards() {

        ArrayList<Integer> cardIndex = new ArrayList<>(12);

        for(int i = 1; i <= 10; i++)
            cardIndex.add(i);

        Collections.shuffle(cardIndex);

        for(int i = 0; i < 3; i++) {

            switch (cardIndex.get(i)) {

                case 1:
                    publicObjectiveCards[i] = new ColoriDiversiRiga().getInstance();
                    break;

                case 2:
                    publicObjectiveCards[i] = new ColoriDiversiColonna().getInstance();
                    break;

                case 3:
                    publicObjectiveCards[i] = new SfumatureDiverseRiga().getInstance();
                    break;

                case 4:
                    publicObjectiveCards[i] = new SfumatureDiverseColonna().getInstance();
                    break;

                case 5:
                    publicObjectiveCards[i] = new SfumatureChiare().getInstance();
                    break;

                case 6:
                    publicObjectiveCards[i] = new
                    break;

                case 7:
                    break;

                case 8:
                    break;

                case 9:
                    break;

                case 10:
                    break;


            }

        }



    }

    public void rollSingleDice(Dice dice) {
        dice.setValue((int)Math.ceil(Math.random()*6));
        //messaggi
    }

    public void rollRoundDice(DiceBag diceBag, int turn, int participants) {
        RoundDice roundDice = new RoundDice(participants, diceBag, turn);
        //messaggi
    }

    public void performMove(PlayerMove move) {

        if (move.isDiceMove()) {
            //scelta e piazzamento dado
            move = (ChooseDiceMove) move;
            move.getPlayer().getSchemaCard().getCell(((ChooseDiceMove) move).getPos()).setAssignedDice(((ChooseDiceMove) move).getDice());
            //messaggi
        }

        else {
            //attivazione tool card
            move = (UseToolCardMove) move;
            ((UseToolCardMove) move).getToolCard().activateCard( move.getPlayer());
            //messaggi
        }

    }

}

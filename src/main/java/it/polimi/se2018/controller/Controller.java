package it.polimi.se2018.controller;
import it.polimi.se2018.controller.tool_cards.*;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.ChooseDiceMove;
import it.polimi.se2018.model.events.PlayerMove;
import it.polimi.se2018.model.events.UseToolCardMove;
import it.polimi.se2018.model.objective_cards.public_objective_cards.*;
import it.polimi.se2018.view.*;

import java.util.*;

//Giorgia

public class Controller {

    private Model model;
    private View view;
    private AbstractToolCard[] toolCards;

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
                    toolCards[i] = PinzaSgrossatrice.getThisInstance();
                    break;

                case 2:
                    toolCards[i] = PennelloPerEglomise.getThisInstance();
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
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureRosse().getThisInstance());
                    break;

                case 2:
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureGialle().getThisInstance());
                    break;

                case 3:
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureVerdi().getThisInstance());
                    break;

                case 4:
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureBlu().getThisInstance());
                    break;

                case 5:
                    model.getPlayer(i).setPrivateObjectiveCard(SfumatureViola().getThisInstance());
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
                    model.getGameBoard().setPublicObjectiveCards(ColoriDiversiRiga.getThisInstance(), i);
                    break;

                case 2:
                    model.getGameBoard().setPublicObjectiveCards(ColoriDiversiColonna.getThisInstance(), i);
                    break;

                case 3:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureDiverseRiga.getThisInstance(), i);
                    break;

                case 4:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureDiverseRiga.getThisInstance(), i);
                    break;

                case 5:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureChiare.getThisInstance(), i);
                    break;

                case 6:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureMedie.getThisInstance(), i);
                    break;

                case 7:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureScure.getThisInstance(), i);
                    break;

                case 8:
                    model.getGameBoard().setPublicObjectiveCards(SfumatureDiverse.getThisInstance(), i);
                    break;

                case 9:
                    model.getGameBoard().setPublicObjectiveCards(DiagonaliColorate.getThisInstance(), i);
                    break;

                case 10:
                    model.getGameBoard().setPublicObjectiveCards(VarietaDiColore.getThisInstance(), i);
                    break;

            }

        }
        //messaggi
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

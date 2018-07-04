package it.polimi.se2018.model.game_equipment;

import java.util.*;

/**
 * Dice bag class containing ninety dice
 * @author giovanni
 */

public class DiceBag {          //Oggetto composto da 90 dadi suddivisi per colore a cui viene associato un valore randomico da 1 a 6

    private ArrayList<Dice> diceBag = new ArrayList<>(90);

    /**
     * Class constructor. It generates 18 dice per color assigning a random value to every single instance.
     * It then shuffles the ArrayList in order to obtain 90 dice with random color and value.
     */
    public DiceBag() {

        for(int i = 0; i < 18; i++)
            diceBag.add(new Dice(Color.RED, (int)Math.ceil(Math.random()*6)));

        for(int i = 18; i < 36; i++)
            diceBag.add(new Dice(Color.YELLOW, (int)Math.ceil(Math.random()*6)));

        for(int i = 36; i < 54; i++)
            diceBag.add(new Dice(Color.GREEN, (int)Math.ceil(Math.random()*6)));

        for(int i = 54; i < 72; i++)
            diceBag.add(new Dice(Color.BLUE, (int)Math.ceil(Math.random()*6)));

        for(int i = 72; i < 90; i++)
            diceBag.add(new Dice(Color.PURPLE, (int)Math.ceil(Math.random()*6)));

        Collections.shuffle(diceBag);

    }

    /**
     * Getter that returns a specific dice
     * @param index index of the chosen dice
     * @return the chosen dice
     */
    public Dice getDice(int index) {

        return diceBag.get(index);

    }

    /**
     * DiceBag getter
     * @return the diceBag instance
     */
    public ArrayList<Dice> getDiceList() {
        return diceBag;
    }

}
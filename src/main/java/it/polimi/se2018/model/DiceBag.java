package it.polimi.se2018.model;
import java.util.*;
//Giorgia

//FUNZIONANTE

public class DiceBag {          //Oggetto composto da 90 dadi suddivisi per colore a cui viene associato un valore randomico da 1 a 6

    private ArrayList<Dice> diceBag = new ArrayList<Dice>(90);

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

    public Dice getDice(int index) {

        return diceBag.get(index);

    }

}
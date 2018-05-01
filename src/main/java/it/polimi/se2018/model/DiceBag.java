package it.polimi.se2018.model;
import java.util.*;

public class DiceBag {

    private ArrayList<Dice> diceBag[];

    public DiceBag() {

        for(int i = 0; i < 18; i++)
            diceBag[i].add(new Dice(Color.RED, (int)Math.random()*6));

        for(int i = 18; i < 36; i++)
            diceBag[i].add(new Dice(Color.YELLOW, (int)Math.random()*6));

        for(int i = 36; i < 54; i++)
            diceBag[i].add(new Dice(Color.GREEN, (int)Math.random()*6));

        for(int i = 54; i < 72; i++)
            diceBag[i].add(new Dice(Color.BLUE, (int)Math.random()*6));

        for(int i = 72; i < 90; i++)
            diceBag[i].add(new Dice(Color.PURPLE, (int)Math.random()*6));

        Collections.shuffle(Arrays.asList(diceBag));

    }

}
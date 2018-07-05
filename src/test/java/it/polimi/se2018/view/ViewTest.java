package it.polimi.se2018.view;

import it.polimi.se2018.model.events.messages.SendWinnerMessage;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ViewTest {

    @Test
    public void update() {
        View view = new View();
        System.out.println(view.toString());
        ArrayList<String> ranking = new ArrayList<>();
        ArrayList<Integer> score = new ArrayList<>();
        ranking.add("p1");
        ranking.add("p2");
        score.add(1);
        score.add(100);
        SendWinnerMessage sendWinnerMessage = new SendWinnerMessage("all","all",ranking,score);
        view.update(sendWinnerMessage);
    }
}
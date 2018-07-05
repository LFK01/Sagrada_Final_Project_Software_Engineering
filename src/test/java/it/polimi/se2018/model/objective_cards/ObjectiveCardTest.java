package it.polimi.se2018.model.objective_cards;

import com.sun.org.apache.xpath.internal.operations.Mod;
import it.polimi.se2018.file_parser.FileParser;
import it.polimi.se2018.model.Model;
import org.junit.Test;

import javax.swing.text.html.parser.Parser;

import static org.junit.Assert.*;

public class ObjectiveCardTest {

    @Test
    public void initializationTest(){
        FileParser parser = new FileParser();
        for(int i =1;i<11;i++){
          ObjectiveCard publicCard = parser.createObjectiveCard(Model.OBJECTIVE_CARD_FILE_ADDRESS,false,i);
          System.out.println("CARTA NUMERO"+ " "+ i);
          System.out.println("Nome:"+ " "+ publicCard.getName());
          System.out.println("Descrizione"+ " "+ publicCard.getDescription());
          System.out.println("Punti"+ " "+ publicCard.getPoints());
          System.out.println("Effetto"+ " "+ publicCard.getEffect());

      }
        for(int j =1;j<6;j++){
            ObjectiveCard privateCard = parser.createObjectiveCard(Model.OBJECTIVE_CARD_FILE_ADDRESS, true,j);
            System.out.println("CARTA NUMERO"+ " "+ j);
            System.out.println("Nome:"+ " "+ privateCard.getName());
            System.out.println("Descrizione"+ " "+ privateCard.getDescription());
            System.out.println("Punti"+ " "+ privateCard.getPoints());
            System.out.println("Effetto"+ " "+ privateCard.getEffect());

        }
    }
}
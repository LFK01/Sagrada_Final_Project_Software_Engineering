package it.polimi.se2018.model.objective_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.private_objective_cards.OCEffectFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Giovanni
 */

public class ObjectiveCard {

    private boolean isPrivate;
    private String name = null;
    private String description = null;
    private int points;
    private ObjectiveCardEffectInterface effect;
    OCEffectFactory factory = new OCEffectFactory();


    public ObjectiveCard(boolean isPrivate,int cardNumber){
        this.isPrivate=isPrivate;
        Scanner inputFile = null;

        try{
            inputFile = new Scanner(new FileInputStream("src\\main\\java\\it\\polimi\\se2018\\model\\objective_cards\\ObjectiveCards"));
            String line = "";
            boolean hasNextLine = true;
            boolean readingPrivateOjective = false;
            boolean cardFound = false;
            try{
                line = inputFile.nextLine();
            }catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine){
                //System.out.println("reading: " + line);
                String[] words = line.split(" ");
                int i=0;
                while(i<words.length){
                    if(isPrivate){
                        if(words[i].trim().equalsIgnoreCase("PrivateObjectiveCards:")){
                            //System.out.println("HO TROVATO LE CARTE");
                            while (hasNextLine){
                                i=0;
                                words = line.split(" ");
                                if (words[i].trim().equalsIgnoreCase("Number:")){
                                    if(cardNumber == Integer.parseInt(words[i+1])){
                                        cardFound = true;
                                        i++;
                                    }
                                }
                                if(cardFound){
                                    //System.out.println("CARTA FOUND");
                                    if(words[i].trim().equalsIgnoreCase("Name:")){
                                        name = words[i+1].replace('/', ' ');
                                        System.out.println("Nome: " + name );
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Description:")){
                                        description = words[i+1].replace('/',' ');
                                        System.out.println("decrizione: " + description );
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase(("points:"))){
                                        points = Integer.parseInt(words[i+1].trim());
                                        System.out.println("punti: " + points );
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Effects:")){
                                        effect = factory.assigneEffect(words[i+1].replace('/',' '));
                                        System.out.println("found eff");
                                        i++;
                                        hasNextLine = false;
                                    }
                                }
                                i++;
                                try{
                                    line = inputFile.nextLine();
                                } catch (NoSuchElementException e){
                                    hasNextLine = false;
                                }
                            }
                            try{
                                line = inputFile.nextLine();
                            } catch (NoSuchElementException e){
                                hasNextLine = false;
                            }
                        }
                    }
                    if(!isPrivate) {
                        System.out.println("searching public");
                        if(words[i].trim().equalsIgnoreCase("PublicObjectiveCards:")){
                            System.out.println("found public");
                            while (hasNextLine){
                                System.out.println("reading: " + line);
                                i = 0;
                                words = line.split(" ");
                                if (words[i].trim().equalsIgnoreCase("Number:")){
                                    System.out.println("found number");
                                    if(cardNumber == Integer.parseInt(words[i+1])){
                                        cardFound = true;
                                        i++;
                                    }
                                }
                                if(cardFound){
                                    if(words[i].trim().equalsIgnoreCase("Name:")){
                                        name = words[i+1].replace('/', ' ');
                                        System.out.println("found name");
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Description:")){
                                        description = words[i+1].replace('/',' ');
                                        System.out.println("found desc");
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase(("Points:"))){
                                        points = Integer.parseInt(words[i+1].trim());
                                        System.out.println("found point");
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Effects:")){
                                        effect = factory.assigneEffect(words[i+1].replace('/',' '));
                                        System.out.println("found eff");
                                        i++;
                                        hasNextLine = false;
                                    }
                                }
                                i++;
                                try{
                                    line = inputFile.nextLine();
                                } catch (NoSuchElementException e){
                                    hasNextLine = false;
                                }
                            }
                        }
                    }
                    i++;
                    try{
                        line = inputFile.nextLine();
                    } catch (NoSuchElementException e){
                        hasNextLine = false;
                    }
                }

            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputFile.close();
        }
    }


    public boolean isPrivate(){
        return isPrivate;
    }

    public int countPoints(SchemaCard schemaCard) {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }

    public ObjectiveCardEffectInterface getEffect() {
        return effect;
    }
    public void countPoints(Model model,String name, int points){
        effect.countPoints(model,name,points);
    }

}

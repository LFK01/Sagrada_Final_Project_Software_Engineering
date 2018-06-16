package it.polimi.se2018.model.objective_cards;

import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
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
                System.out.println("reading: " + line);
                String[] words = line.split(" ");
                int i=0;
                while(i<words.length){
                    if(isPrivate){
                        if(words[i].trim().equalsIgnoreCase("PrivateObjectiveCards:")){
                            while (hasNextLine){
                                words = line.split(" ");
                                if (words[i].trim().equalsIgnoreCase("Number:")){
                                    if(cardNumber == Integer.parseInt(words[i+1])){
                                        cardFound = true;
                                        i+=2;
                                    }
                                }
                                if(cardFound){
                                    if(words[i].trim().equalsIgnoreCase("Name:")){
                                        name = words[i+1].replace('/', ' ');
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Description")){
                                        description = words[i+1].replace('/',' ');
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase((""))){
                                        points = Integer.parseInt(words[i+1].trim());
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Effects")){
                                        effect = factory.assigneEffect(words[i+1].replace('/',' '));
                                        System.out.println("found eff");
                                        i++;
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
                    else {
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
                }
                try{
                    line = inputFile.nextLine();
                } catch (NoSuchElementException e){
                    hasNextLine = false;
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
}

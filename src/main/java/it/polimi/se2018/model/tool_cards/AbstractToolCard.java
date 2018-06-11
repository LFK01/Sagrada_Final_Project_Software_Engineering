package it.polimi.se2018.model.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.view.comand_line.InputManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Luciano
 */
public abstract class AbstractToolCard {

    private String name;
    private String description;
    private boolean firstUsage;

    public AbstractToolCard(int toolCardNumber){
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream("src\\main\\java\\it\\polimi\\se2018\\ToolCards.txt"));
            String line = "";
            boolean hasNextLine = true;
            boolean cardFound = false;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length){
                    if(words[i].trim().equals("Number:")){
                        if(toolCardNumber == Integer.parseInt(words[i+1])){
                            //System.out.println("found card");
                            cardFound = true;
                            i++;
                        }
                    }
                    if(cardFound){
                        if(words[i].trim().equals("Name:")){
                            //System.out.println("new name");
                            name = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equals("description:")){
                            //System.out.println("new description");
                            description = words[i+1].replace('/', ' ');
                            i++;
                            hasNextLine = false;
                        }
                    }
                    i++;
                }
                try{
                    line = inputFile.nextLine();
                } catch (NoSuchElementException e){
                    hasNextLine = false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputFile.close();
        }
        firstUsage = true;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFirstUsage() {
        return firstUsage;
    }

    public void isBeingUsedForTheFirstTime(){
        if(firstUsage){
            firstUsage = false;
        }
        else{
            //throw new ToolCardAlreadyBeenUsedException();
        }
    }

    public abstract void activateToolCard(String username, String name, String values, Model model);

    public abstract InputManager getInputManager(String name);

}
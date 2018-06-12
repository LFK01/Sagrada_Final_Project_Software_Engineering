package it.polimi.se2018.controller.tool_cards;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.view.comand_line.InputManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Luciano
 */
public class ToolCard {

    private String name;
    private String description;
    private InputManager inputManager;
    private ArrayList<EffectInterface> effectsList = new ArrayList<>();
    private EffectsFactory effectsFactory = new EffectsFactory();
    private boolean firstUsage;

    public ToolCard(int toolCardNumber){
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream("src\\main\\java\\it\\polimi\\se2018\\controller\\tool_cards\\ToolCards.txt"));
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
                    if(words[i].trim().equalsIgnoreCase("number:")){
                        if(toolCardNumber == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                    }
                    if(cardFound){
                        if(words[i].trim().equalsIgnoreCase("name:")){
                            //System.out.println("new name");
                            name = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("description:")){
                            //System.out.println("new description");
                            description = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("effects:")){
                            String[] effectsNamesList = words[i+1].split("/");
                            for(String effectName: effectsNamesList){
                                effectsList.add(effectsFactory.getThisEffect(effectName));
                            }
                        }
                        if(words[i].trim().equalsIgnoreCase("inputManager: ")){
                            inputManager = InputManager.valueOf(words[i+1]);
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

    public void activateToolCard(String username, String toolCardName, String values, Model model){
        for(EffectInterface effect: effectsList){
            if(!effect.isDone()){
                effect.doYourJob(username, toolCardName, values, model);
            }
        }
    }

    public InputManager getInputManager(String name){
        return inputManager;
    }

}
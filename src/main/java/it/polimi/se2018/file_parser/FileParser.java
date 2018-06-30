package it.polimi.se2018.file_parser;

import it.polimi.se2018.controller.tool_cards.EffectsFactory;
import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.model.objective_cards.OCEffectFactory;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;
import it.polimi.se2018.view.comand_line.InputManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Luciano
 */
public class FileParser {

    public int readPortSocket(String fileAddress) {
        int filePortSocket = 1099;
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream(fileAddress));
            String line = "";
            boolean hasNextLine = true;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length){
                    if(words[i].trim().equals("PortSocket:")){
                        filePortSocket = Integer.parseInt(words[i+1]);
                        hasNextLine = false;
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
            System.out.println("Impossible to find the file");
        } finally {
            inputFile.close();
        }
        return filePortSocket;
    }

    public int readPortRMI(String fileAddress){
        int filePort = -1;
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream(fileAddress));
            String line = "";
            boolean hasNextLine = true;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length){
                    if(words[i].trim().equals("PortRMI:")){
                        filePort = Integer.parseInt(words[i+1]);
                        hasNextLine = false;
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
        return filePort;
    }

    public String readServerIP(String fileAddress){
        String fileIP = "";
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream(fileAddress));
            String line = "";
            boolean hasNextLine = true;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length){
                    if(words[i].trim().equals("ServerIP:")){
                        fileIP = words[i+1];
                        hasNextLine = false;
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
            System.out.println("Impossible to find the file");
        } finally {
            inputFile.close();
        }
        return fileIP;
    }

    public int readTimer(String fileAddress){
        int fileTimer = -1;
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream(fileAddress));
            String line = "";
            boolean hasNextLine = true;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length){
                    if(words[i].trim().equals("Timer:")){
                        fileTimer = Integer.parseInt(words[i+1]);
                        hasNextLine = false;
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
        return fileTimer;
    }

    public ToolCard createToolCard(String fileAddress, int toolCardNumber){
        String name = "";
        String identificationName = "";
        String description = "";
        ArrayList<TCEffectInterface> effectsList = new ArrayList<>();
        ArrayList<InputManager> inputManagerList = new ArrayList<>();
        ArrayList<String> specificEffectsList = new ArrayList<>();
        EffectsFactory effectsFactory = new EffectsFactory();
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
                            name = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("ID:")){
                            identificationName = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("description:")){
                            description = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("effects:")){
                            String[] effectsNames = words[i+1].split("/");
                            for(String effectName: effectsNames){
                                effectsList.add(effectsFactory.getThisEffect(effectName));
                            }
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("parameters:")){
                            String[] parametersNames = words[i+1].split("/");
                            specificEffectsList.addAll(Arrays.asList(parametersNames));
                            i++;
                        }
                        if(words[i].trim().equalsIgnoreCase("inputManager:")){
                            String[] inputsList = words[i+1].split("/");
                            for(String inputName: inputsList) {
                                inputManagerList.add(InputManager.valueOf(inputName));
                            }
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
        System.out.println("about to create the toolcard. ID is: " + identificationName);
        return new ToolCard(name, identificationName, description, inputManagerList,
                effectsList, specificEffectsList, true);
    }
    public ObjectiveCard createObjectiveCard(boolean isPrivate, int cardNumber){
        String name = "";
        String description = "";
        int points =0;
        String effect = "";
        OCEffectFactory factory = new OCEffectFactory();
        ObjectiveCardEffectInterface effectOC = null;
        isPrivate=isPrivate;
        Scanner inputFile = null;

        try{
            inputFile = new Scanner(new FileInputStream("src\\main\\java\\it\\polimi\\se2018\\model\\objective_cards\\ObjectiveCards"));
            String line = "";
            boolean hasNextLine = true;
            boolean cardFound = false;
            try{
                line = inputFile.nextLine();
            }catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine){
                String[] words = line.split(" ");
                int i=0;
                while(i<words.length){
                    if(isPrivate){
                        if(words[i].trim().equalsIgnoreCase("PrivateObjectiveCards:")){
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
                                    if(words[i].trim().equalsIgnoreCase("Name:")){
                                        name = words[i+1].replace('/', ' ');
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Description:")){
                                        description = words[i+1].replace('/',' ');
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase(("points:"))){
                                        points = Integer.parseInt(words[i+1].trim());
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Effects:")){
                                        effectOC = factory.assigneEffect(words[i+1].replace('/',' '));
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
                        if(words[i].trim().equalsIgnoreCase("PublicObjectiveCards:")){
                            while (hasNextLine){
                                i = 0;
                                words = line.split(" ");
                                if (words[i].trim().equalsIgnoreCase("Number:")){
                                    if(cardNumber == Integer.parseInt(words[i+1])){
                                        cardFound = true;
                                        i++;
                                    }
                                }
                                if(cardFound){
                                    if(words[i].trim().equalsIgnoreCase("Name:")){
                                        name = words[i+1].replace('/', ' ');
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Description:")){
                                        description = words[i+1].replace('/',' ');
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase(("Points:"))){
                                        points = Integer.parseInt(words[i+1].trim());
                                        i++;
                                    }
                                    if(words[i].trim().equalsIgnoreCase("Effects:")){
                                        effectOC = factory.assigneEffect(words[i+1].replace('/',' '));
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
        System.out.println("about to create the ObjectiveCard with name is: " + name);
        return new ObjectiveCard(name, description, points,effectOC);

    }

    public String searchIDByNumber(String fileAddress, int toolCardNumber){
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(new FileInputStream(fileAddress));
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
                        if(words[i].trim().equalsIgnoreCase("ID:")){
                            return words[i+1];
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
        return "";
    }
}

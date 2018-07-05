package it.polimi.se2018.file_parser;

import it.polimi.se2018.controller.tool_cards.EffectsFactory;
import it.polimi.se2018.controller.tool_cards.TCEffectInterface;
import it.polimi.se2018.controller.tool_cards.ToolCard;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.game_equipment.Cell;
import it.polimi.se2018.model.game_equipment.Color;
import it.polimi.se2018.model.game_equipment.SchemaCard;
import it.polimi.se2018.model.objective_cards.OCEffectFactory;
import it.polimi.se2018.model.objective_cards.ObjectiveCard;
import it.polimi.se2018.model.objective_cards.ObjectiveCardEffectInterface;
import it.polimi.se2018.view.comand_line.InputManager;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luciano
 */
public class FileParser {

    private static final String FILE_NOT_FOUND = "Impossible to find the file, src directory might have been modified";
    private static final int LATHEKIN_NUMBER = 4;
    private static final int TAP_WHEEL_NUMBER = 12;

    public int readPortSocket(String fileAddress) {
        int filePortSocket = 1099;
        try (InputStream inputStream = new FileInputStream(fileAddress);
                Scanner inputFile = new Scanner(inputStream)) {
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
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        return filePortSocket;
    }

    public int readPortRMI(String fileAddress){
        int filePort = -1;
        try (InputStream inputStream = new FileInputStream(fileAddress);
                Scanner inputFile = new Scanner(inputStream)) {
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
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        return filePort;
    }

    public String readServerIP(String fileAddress){
        String readFromFileIP = "";
        try (InputStream inputStream = new FileInputStream(fileAddress);
                Scanner inputFile = new Scanner(inputStream)){
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
                        readFromFileIP = words[i+1];
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
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        return readFromFileIP;
    }

    public int readTimer(String fileAddress){
        int fileTimer = -1;
        try (InputStream inputStream = new FileInputStream(fileAddress);
                Scanner inputFile = new Scanner(inputStream)){
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
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        return fileTimer;
    }

    public ToolCard createToolCard(String folderAddress, int toolCardNumber){
        ToolCard newToolCard = null;
        File folder = new File(folderAddress);
        for (final File fileEntry : folder.listFiles()) {
            newToolCard = readSingleToolCard(fileEntry, toolCardNumber);
            if (!newToolCard.getIdentificationName().equals("")) {
                return newToolCard;
            }
        }
        return newToolCard;
    }

    private ToolCard readSingleToolCard(File file, int toolCardNumber){
        String name = "";
        String identificationName = "";
        String description = "";
        ArrayList<TCEffectInterface> effectsList = new ArrayList<>();
        ArrayList<InputManager> inputManagerList = new ArrayList<>();
        ArrayList<String> specificEffectsList = new ArrayList<>();
        EffectsFactory effectsFactory = new EffectsFactory();
        try (InputStream inputStream = new FileInputStream(Model.FOLDER_ADDRESS_TOOL_CARDS + "/" + file.getName());
                Scanner inputFile = new Scanner(inputStream)){
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
                    if(words[i].trim().equalsIgnoreCase("number:") &&
                            toolCardNumber == Integer.parseInt(words[i+1])){
                        cardFound = true;
                        i++;
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
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        return new ToolCard(name, identificationName, description, inputManagerList,
                effectsList, specificEffectsList, true);
    }

    public ObjectiveCard createObjectiveCard(String fileAddress, boolean isPrivate, int cardNumber){
        String name = "";
        String description = "";
        int points = 0;
        OCEffectFactory factory = new OCEffectFactory();
        ObjectiveCardEffectInterface effectOC = null;
        try (InputStream inputStream = new FileInputStream(fileAddress);
             Scanner inputFile = new Scanner(inputStream)){
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
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        return new ObjectiveCard(name, description, points,effectOC);
    }

    public SchemaCard createSchemaCardByNumber(String folderAddress, int schemaCardNumber){
        SchemaCard newSchemaCard = null;
        File folder = new File(folderAddress);
        for (final File fileEntry : folder.listFiles()) {
            newSchemaCard = readSingleSchemaCardByNumber(fileEntry, schemaCardNumber);
            if (!newSchemaCard.getName().equals("")) {
                return newSchemaCard;
            }
        }
        return newSchemaCard;
    }

    public SchemaCard createSchemaCardByName(String folderAddress, String schemaName){
        SchemaCard newSchemaCard = null;
        File folder = new File(folderAddress);
        for (final File fileEntry : folder.listFiles()) {
            newSchemaCard = readSingleSchemaCardByName(fileEntry, schemaName);
            if (!newSchemaCard.getName().equals("")) {
                return newSchemaCard;
            }
        }
        return newSchemaCard;
    }

    private SchemaCard readSingleSchemaCardByNumber(File file, int schemaCardNumber){
        Cell[][] cells = new Cell[4][5];
        String name = "";
        int difficultyLevel = -1;
        try (InputStream inputStream = new FileInputStream(Model.FOLDER_ADDRESS_SCHEMA_CARDS + "/" + file.getName());
                 Scanner inputFile = new Scanner(inputStream)){
            String line = "";
            boolean hasNextLine = true;
            boolean cardFound = false;
            boolean completedRows = false;
            boolean stopReading = false;
            int row=0;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine && !stopReading){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length && !completedRows){
                    if(words[i].trim().equals("Number:")){
                        if(schemaCardNumber == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                    }
                    if(cardFound){
                        if(words[i].trim().equals("name:")){
                            name = words[i+1].replace('/', ' ');
                            i++;
                        }
                        if(words[i].trim().equals("difficulty:")){
                            difficultyLevel = Integer.parseInt(words[i+1]);
                            i++;
                        }
                        if(words[i].startsWith("[")){
                            for(int col=0; col<words.length; col++){
                                cells[row][col] = analizeCell(words[i]);
                                i++;
                            }
                            row++;
                            if(row>3){
                                completedRows = true;
                                cardFound = false;
                                stopReading = true;
                            }
                        }
                    }
                    i=words.length;
                }
                try{
                    line = inputFile.nextLine();
                } catch (NoSuchElementException e){
                    hasNextLine = false;
                }
            }
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        return new SchemaCard(name, cells, difficultyLevel);
    }

    private SchemaCard readSingleSchemaCardByName(File file, String schemaName){
        Cell[][] cells = new Cell[4][5];
        String name = "";
        int difficultyLevel = -1;
        try (InputStream inputStream = new FileInputStream(Model.FOLDER_ADDRESS_SCHEMA_CARDS + "/" + file.getName());
             Scanner inputFile = new Scanner(inputStream)){
            String line = "";
            boolean hasNextLine = true;
            boolean cardFound = false;
            boolean completedRows = false;
            boolean stopReading = false;
            int row=0;
            try{
                line = inputFile.nextLine();
            } catch (NoSuchElementException e){
                hasNextLine = false;
            }
            while(hasNextLine && !stopReading){
                String[] words = line.split(" ");
                int i = 0;
                while(i<words.length && !completedRows){
                    if(words[i].trim().equals("name:")){
                        if(schemaName.replace(' ', '/').equals(words[i+1])){
                            cardFound = true;
                            name = words[i+1].replace('/', ' ');
                            i++;
                        }
                    }
                    if(cardFound){
                        if(words[i].trim().equals("difficulty:")){
                            difficultyLevel = Integer.parseInt(words[i+1]);
                            i++;
                        }
                        if(words[i].startsWith("[")){
                            for(int col=0; col<words.length; col++){
                                cells[row][col] = analizeCell(words[i]);
                                i++;
                            }
                            row++;
                            if(row>3){
                                completedRows = true;
                                cardFound = false;
                                stopReading = true;
                            }
                        }
                    }
                    i=words.length;
                }
                try{
                    line = inputFile.nextLine();
                } catch (NoSuchElementException e){
                    hasNextLine = false;
                }
            }
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        return new SchemaCard(name, cells, difficultyLevel);
    }
    
    private Cell analizeCell(String cell){
        switch(cell){
            case "[]":{
                return new Cell(null, 0);
            }
            case "[1]":{
                return new Cell(null, 1);
            }
            case "[2]":{
                return new Cell(null, 2);
            }
            case "[3]":{
                return new Cell(null, 3);
            }
            case "[4]":{
                return new Cell(null, 4);
            }
            case "[5]":{
                return new Cell(null, 5);
            }
            case "[6]":{
                return new Cell(null, 6);
            }
            case "[Y]":{
                return new Cell(Color.YELLOW, 0);
            }
            case "[R]":{
                return new Cell(Color.RED, 0);
            }
            case "[B]":{
                return new Cell(Color.BLUE, 0);
            }
            case "[G]":{
                return new Cell(Color.GREEN, 0);
            }
            case "[P]":{
                return new Cell(Color.PURPLE, 0);
            }
            default:{
                return new Cell(null, 0);
            }
        }
    }

    public int countExcessSchemaCards(String folderAddress){
        int actualSchemaCardNumber = 0;
        File folder = new File(folderAddress);
        for (File file : folder.listFiles()) {
            actualSchemaCardNumber++;
        }
        return actualSchemaCardNumber - Model.SCHEMA_CARDS_NUMBER;
    }

    public String searchIDByNumber(String folderAddress, int toolCardNumber){
        File folder = new File(folderAddress);
        for(final File fileEntry : folder.listFiles()){
            String foundID = getIdByNumberOnSingleFile(fileEntry, toolCardNumber);
            if(!foundID.equals("")) {
                return getIdByNumberOnSingleFile(fileEntry, toolCardNumber);
            }
        }
        return "";
    }

    private String getIdByNumberOnSingleFile(File file, int toolCardNumber){
        try (InputStream inputStream = new FileInputStream(Model.FOLDER_ADDRESS_TOOL_CARDS + "/" + file.getName());
                Scanner inputFile = new Scanner(inputStream)) {
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
                    if(words[i].trim().equalsIgnoreCase("number:") &&
                            toolCardNumber == Integer.parseInt(words[i+1])){
                        cardFound = true;
                        i++;
                    }
                    if(cardFound && words[i].trim().equalsIgnoreCase("ID:")){
                        return words[i+1];
                    }
                    i++;
                }
                try{
                    line = inputFile.nextLine();
                } catch (NoSuchElementException e){
                    hasNextLine = false;
                }
            }
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
        return "";
    }

    public boolean getTapWheelUsingValue(String folderAddress){
        File folder = new File(folderAddress);
        for(File fileEntry: folder.listFiles()){
            try (InputStream inputStream = new FileInputStream(folderAddress + "/" +  fileEntry.getName());
                 Scanner inputFile = new Scanner(inputStream)) {
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
                        if(words[i].trim().equalsIgnoreCase("number:") &&
                                TAP_WHEEL_NUMBER == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                        if(cardFound &&
                                words[i].trim().equalsIgnoreCase("isBeingUsed:")){
                            return Boolean.valueOf(words[i+1]);
                        }
                        i++;
                    }
                    try{
                        line = inputFile.nextLine();
                    } catch (NoSuchElementException e){
                        hasNextLine = false;
                    }
                }
            } catch (IOException e) {
                System.out.println(FILE_NOT_FOUND);
            }
        }
        return false;
    }

    public void writeTapWheelUsingValue(String folderAddress, boolean isBeingUsed){
        File folder = new File(folderAddress);
        StringBuilder fileBackup = new StringBuilder();
        for(File fileEntry: folder.listFiles()) {
            try (InputStream inputStream = new FileInputStream(folderAddress + "/" +  fileEntry.getName());
                 Scanner inputFile = new Scanner(inputStream)) {
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
                        if(words[i].trim().equalsIgnoreCase("number:") &&
                                TAP_WHEEL_NUMBER == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                        if(cardFound && words[i].trim().equalsIgnoreCase("isBeingUsed:")){
                            line = "isBeingUsed: " + Boolean.toString(isBeingUsed);
                        }
                        i++;
                    }
                    if(cardFound){
                        fileBackup.append(line).append("\n");
                    }
                    try{
                        line = inputFile.nextLine();
                    } catch (NoSuchElementException e){
                        hasNextLine = false;
                    }
                }
                if(cardFound) {
                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileEntry)))) {
                        writer.write(fileBackup.toString());
                    } catch (IOException e) {
                        System.out.println(FILE_NOT_FOUND);
                    }
                }
            } catch (IOException e) {
                System.out.println(FILE_NOT_FOUND);
            }
        }
    }

    public Color getTapWheelFirstColor(String folderAddress){
        File folder = new File(folderAddress);
        for(File fileEntry: folder.listFiles()) {
            try (InputStream inputStream = new FileInputStream(folderAddress + "/" +  fileEntry.getName());
                 Scanner inputFile = new Scanner(inputStream)) {
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
                        if(words[i].trim().equalsIgnoreCase("number:") &&
                                TAP_WHEEL_NUMBER == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                        if(cardFound && words[i].trim().equalsIgnoreCase("color:")){
                            try {
                                Color.valueOf(words[i+1]);
                            } catch (IllegalArgumentException e){
                                return Color.NO_COLOR;
                            }
                            return Color.valueOf(words[i+1]);
                        }
                        i++;
                    }
                    try{
                        line = inputFile.nextLine();
                    } catch (NoSuchElementException e){
                        hasNextLine = false;
                    }
                }
            } catch (IOException e) {
                System.out.println(FILE_NOT_FOUND);
            }
        }
        return null;
    }

    public void writeTapWheelFirstColor(String folderAddress, String firstDieMovingColor){
        File folder = new File(folderAddress);
        StringBuilder fileBackup = new StringBuilder();
        for(File fileEntry: folder.listFiles()) {
            try (InputStream inputStream = new FileInputStream(folderAddress + "/" +  fileEntry.getName());
                 Scanner inputFile = new Scanner(inputStream)) {
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
                        if(words[i].trim().equalsIgnoreCase("number:") &&
                                TAP_WHEEL_NUMBER == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                        if(cardFound && words[i].trim().equalsIgnoreCase("Color:")){
                            line = "Color: " + firstDieMovingColor;
                        }
                        i++;
                    }
                    if(cardFound){
                        fileBackup.append(line).append("\n");
                    }
                    try{
                        line = inputFile.nextLine();
                    } catch (NoSuchElementException e){
                        hasNextLine = false;
                    }
                }
                if(cardFound) {
                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileEntry)))) {
                        writer.write(fileBackup.toString());
                    } catch (IOException e) {
                        System.out.println(FILE_NOT_FOUND);
                    }
                }
            } catch (IOException e) {
                System.out.println(FILE_NOT_FOUND);
            }
        }
    }
    
    public int[] getLathekinOldDiePositions(String folderAddress){
        int[] positions = new int[2];
        File folder = new File(folderAddress);
        for(File fileEntry: folder.listFiles()){
            try (InputStream inputStream = new FileInputStream(folderAddress + "/" +  fileEntry.getName());
                 Scanner inputFile = new Scanner(inputStream)) {
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
                        if(words[i].trim().equalsIgnoreCase("number:") &&
                                LATHEKIN_NUMBER == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                        if(cardFound &&
                                words[i].trim().equalsIgnoreCase("firstPlacedDieOldRow:")){
                            positions[0] = Integer.parseInt(words[i+1]);
                        }
                        if(cardFound &&
                                words[i].trim().equalsIgnoreCase("firstPlacedDieOldCol:")){
                            positions[1] = Integer.parseInt(words[i+1]);
                        }
                        i++;
                    }
                    try{
                        line = inputFile.nextLine();
                    } catch (NoSuchElementException e){
                        hasNextLine = false;
                    }
                }
            } catch (IOException e) {
                System.out.println(FILE_NOT_FOUND);
            }
        }
        return positions;
    }
    
    public int[] getLathekinNewDiePositions(String folderAddress){
        int[] positions = new int[2];
        File folder = new File(folderAddress);
        for(File fileEntry: folder.listFiles()){
            try (InputStream inputStream = new FileInputStream(folderAddress + "/" +  fileEntry.getName());
                    Scanner inputFile = new Scanner(inputStream)) {
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
                        if(words[i].trim().equalsIgnoreCase("number:") &&
                                LATHEKIN_NUMBER == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                        if(cardFound &&
                                words[i].trim().equalsIgnoreCase("firstPlacedDieNewRow:")){
                            positions[0] = Integer.parseInt(words[i+1]);
                        }
                        if(cardFound &&
                                words[i].trim().equalsIgnoreCase("firstPlacedDieNewCol:")){
                            positions[1] = Integer.parseInt(words[i+1]);
                        }
                        i++;
                    }
                    try{
                        line = inputFile.nextLine();
                    } catch (NoSuchElementException e){
                        hasNextLine = false;
                    }
                }
            } catch (IOException e) {
                System.out.println(FILE_NOT_FOUND);
            }
        }
        return positions;
    }
    
    public void writeLathekinPositions(String folderAddress, int oldDieRow, int oldDieCol, 
                                       int newDieRow, int newDieCol){
        File folder = new File(folderAddress);
        StringBuilder fileBackup = new StringBuilder();
        for(File fileEntry: folder.listFiles()) {
            try (InputStream inputStream = new FileInputStream(folderAddress + "/" +  fileEntry.getName());
                 Scanner inputFile = new Scanner(inputStream)) {
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
                        if(words[i].trim().equalsIgnoreCase("number:") &&
                                LATHEKIN_NUMBER == Integer.parseInt(words[i+1])){
                            cardFound = true;
                            i++;
                        }
                        if(cardFound &&
                                words[i].trim().equalsIgnoreCase("firstPlacedDieOldRow:")){
                            line = "firstPlacedDieOldRow: " + oldDieRow;
                        }
                        if(cardFound &&
                                words[i].trim().equalsIgnoreCase("firstPlacedDieOldCol:")){
                            line = "firstPlacedDieOldCol: " + oldDieCol;
                        }
                        if(cardFound &&
                                words[i].trim().equalsIgnoreCase("firstPlacedDieNewRow:")){
                            line = "firstPlacedDieNewRow: " + newDieRow;
                        }
                        if(cardFound &&
                                words[i].trim().equalsIgnoreCase("firstPlacedDieNewCol:")){
                            line = "firstPlacedDieNewCol: " + newDieCol;
                        }
                        i++;
                    }
                    if(cardFound){
                        fileBackup.append(line).append("\n");
                    }
                    try{
                        line = inputFile.nextLine();
                    } catch (NoSuchElementException e){
                        hasNextLine = false;
                    }
                }
                if(cardFound) {
                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileEntry)))) {
                        writer.write(fileBackup.toString());
                    } catch (IOException e) {
                        System.out.println(FILE_NOT_FOUND);
                    }
                }
            } catch (IOException e) {
                System.out.println(FILE_NOT_FOUND);
            }
        }
    }

}

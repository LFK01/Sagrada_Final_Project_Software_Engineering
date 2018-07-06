package it.polimi.se2018.file_parser;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.network.server.Server;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileParserTest {

    @Test
    public void readPortSocket() {
        FileParser parser = new FileParser();
        parser.readPortSocket(Server.SERVER_INPUT_FILE_ADDRESS);
    }

    @Test
    public void readPortRMI() {
        FileParser parser = new FileParser();
        parser.readPortRMI(Server.SERVER_INPUT_FILE_ADDRESS);
    }

    @Test
    public void readServerIP() {
        FileParser parser = new FileParser();
        parser.readServerIP(Server.SERVER_INPUT_FILE_ADDRESS);
    }

    @Test
    public void readTimer() {
        FileParser parser = new FileParser();
        parser.readTimer(Server.SERVER_INPUT_FILE_ADDRESS);
    }
}
package commands;

import commands.servercommands.Exit;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ServerCommandsManager {

    Exit exit = new Exit();


    private void commandsManager(String command){
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            commandsMap.put(exit.toString(), () -> exit.exit());
        }
        commandsMap.get(command).execute();
    }

    public void executeCommand(String command) {
        //for testing
        System.out.println("command: " + command);

        try {
            commandsManager(command);
        } catch (NullPointerException e) {
            System.out.println("Please check if the command or its attribute is correct.");
            System.out.println("A list of available commands can be found by entering the help command.");
        }
    }
}

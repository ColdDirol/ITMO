package commands;

import commands.clientcommands.ExecuteScript;
import commands.clientcommands.Exit;
import commands.clientcommands.LogOut;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandManager {

    //Client commands:
    Exit exit = new Exit();
    LogOut logOut = new LogOut();
    ExecuteScript executeScript = new ExecuteScript();

    private void commandsManager(String command, Socket socket) {
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            commandsMap.put(exit.toString(), () -> {
                try {
                    exit.exit(socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            commandsMap.put(logOut.toString(), () -> {
            try {
                logOut.logOut(socket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            });
        }
        commandsMap.get(command).execute();
    }

    private void commandsManager(String command, String attribute, Socket socket) {
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            commandsMap.put(executeScript.toString(), () -> {
                try {
                    executeScript.executeScript(attribute, socket);
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        commandsMap.get(command).execute();
    }

    public void executeCommand(String fullCommand, Socket socket) throws ParserConfigurationException, TransformerException {
        String command = getCommand(fullCommand);
        String attribute = getAttribute(fullCommand);
        try {
            if (attribute == null || attribute.trim().isEmpty()) commandsManager(command, socket);
            else commandsManager(command, attribute, socket);
        } catch (NullPointerException exception) {
            System.out.println("Please check if the command or its attribute is correct.");
            System.out.println("A list of available commands can be found by entering the help command.");

        }
    }

    public String getCommand(String fullCommand) {

        String command = "";

        for (int i = 0; i < fullCommand.length(); i++) {
            if (fullCommand.charAt(i) != ' ') command += fullCommand.charAt(i);
            else break;
        }

        return command;
    }

    public String getAttribute(String fullCommand) {
        int cnt = 0;
        String attribute = "";

        for (int i = 0; i < fullCommand.length(); i++) {
            if (fullCommand.charAt(i) == ' ') cnt = i;
            if (cnt > 0 & i > cnt) attribute += fullCommand.charAt(i);
        }
        return attribute;
    }
}

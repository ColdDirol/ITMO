package commands;

import clientlogic.ConsoleOutputMode;
import commands.commands.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandsManager {
    //SimpleCommandType
    Exit exit = new Exit();

    //CommandType
    ExecuteScript executeScript = new ExecuteScript();
    Insert insert = new Insert();
    Update update = new Update();
    ReplaceIfGreater replaceIfGreater = new ReplaceIfGreater();
    ReplaceIfLowe replaceIfLowe = new ReplaceIfLowe();


    private void commandsManager(String command) {
        Map<String, SimpleCommandType> commandsMap = new LinkedHashMap<>();
        {
            //общие команды без атрибута
            commandsMap.put(exit.toString(), () -> exit.exit());
        }
        commandsMap.get(command).execute();
    }
    private void commandsManager(String command, String attribute, Socket socket) {
        Map<String, SimpleCommandType> commandsMap = new LinkedHashMap<>();
        {
            //общие команды без атрибута
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

    private ArrayList<String> commandsManager(String command, Scanner scanner, ConsoleOutputMode MODE) {
        Map<String, ClientCommandType> commandsMap = new LinkedHashMap<>();
        {
            //общие команды без атрибута
            commandsMap.put(insert.toString(), () -> insert.insert(scanner, MODE));
        }
        return commandsMap.get(command).execute();
    }

    private ArrayList<String> commandsManager(String command, String attribute, Scanner scanner, ConsoleOutputMode MODE) {
        Map<String, ClientCommandType> commandsMap = new LinkedHashMap<>();
        {
            //общие команды с атрибутом
            commandsMap.put(update.toString(), () -> update.update(scanner, MODE));
            commandsMap.put(replaceIfGreater.toString(), () -> replaceIfGreater.replaceIfGreater(scanner, MODE));
            commandsMap.put(replaceIfLowe.toString(), () -> replaceIfLowe.replaceIfLowe(scanner, MODE));
        }
        return commandsMap.get(command).execute();
    }


    public String executeCommand(String fullCommand, Socket socket) {
        String command = getCommand(fullCommand);
        String attribute = getAttribute(fullCommand);

        //for testing
        //System.out.println("command: " + command + ", attribute: " + attribute);

        try {
            if (attribute == null || attribute.trim().isEmpty()) {
                commandsManager(command);
            }
            else {
                if(executeScript.isSocketCommand(command)) commandsManager(command, attribute, socket);
                else{
                    System.out.println("Error. Try to repeat your request!");
                }
            }
        } catch (NullPointerException exception) {
            System.out.println("Please check if the command or its attribute is correct.");
            System.out.println("A list of available commands can be found by entering the help command.");
        }
        return null;
    }

    //CommandType
    public String executeCommand(String fullCommand, Scanner scanner, ConsoleOutputMode MODE) throws ParserConfigurationException, TransformerException {
        String command = getCommand(fullCommand);
        String attribute = getAttribute(fullCommand);

        ArrayList<String> elementsArrayList;

        //for testing
        //System.out.println("command: " + command + ", attribute: " + attribute);

        try {
            if (attribute == null || attribute.trim().isEmpty()) {
                elementsArrayList = commandsManager(command, scanner, MODE);
                String result = command + " " + getElementsFromArrayList(elementsArrayList);
                //System.out.println(result);
                return result;
            }
            else {
                elementsArrayList = commandsManager(command, attribute, scanner, MODE);
                String result = command + " " + attribute + " " + getElementsFromArrayList(elementsArrayList);
                //System.out.println(result);
                return result;
            }
        } catch (NullPointerException exception) {
            System.out.println("Please check if the command or its attribute is correct.");
            System.out.println("A list of available commands can be found by entering the help command.");
        }
        return null;
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

    public String getElementsFromArrayList(ArrayList<String> arrayList) {
        String value = "[";

        for(String item : arrayList) {
            value += item + ",";
        }

        value = value.substring(0, value.length() - 1);

        value += "]";

        return value;
    }
}

package commands;

import commands.commands.*;
import exceptions.FileHasBeenDeletedException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.*;

/**
 * The class CommandManager is used to execute any command from class CommandList
 * CommandManager has two @OverLoad methods: commandsManager() and commandsManager(String attribute)
 * The method executeCommand check attribute of command and if it is null will be called first commandManager() method, if it is not null will be called second commandsManager(String attribute) manager.
 * Then commandsManager(T) find command with attribute or without attribute and execute method which contains in the values of Map(String, CommandType) commandsMap.
 */
public class CommandsManager {
    // команды без ввода
    Help help = new Help();
    Info info = new Info();
    Show show = new Show();
    Clear clear = new Clear();
    Save save = new Save();
    SumOfNumberOfRooms sumOfNumberOfRooms = new SumOfNumberOfRooms();
    MaxByView maxByView = new MaxByView();

    // команды с атрибутом
    RemoveKey removeKey = new RemoveKey();
    CountByNumberOfRooms countByNumberOfRooms = new CountByNumberOfRooms();
    RemoveGreater removeGreater = new RemoveGreater();
    NullCommand nullCommand = new NullCommand();

    private void commandsManager(String command) {
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            //только серверные команды
            commandsMap.put("help", () -> help.help());
            commandsMap.put("info", () -> info.info());
            commandsMap.put("show", () -> {
                try {
                    show.show();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            commandsMap.put("clear", () -> clear.clear());
            commandsMap.put("save", () -> {
                try {
                    save.save();
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                } catch (FileHasBeenDeletedException e) {
                    throw new RuntimeException(e);
                }
            });
            commandsMap.put("sum_of_number_of_rooms", () -> sumOfNumberOfRooms.sumOfNumbersOfRooms());
            commandsMap.put("max_by_view", () -> maxByView.maxByView());
            commandsMap.put("execute_script", () -> nullCommand.nullCommand());
        }
        commandsMap.get(command).execute();
    }

    private void commandsManager(String command, String attribute) {
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            //только серверные команды
            commandsMap.put("remove_key", () -> removeKey.removeKey(attribute));
            commandsMap.put("count_by_number_of_rooms", () -> countByNumberOfRooms.countByNumberOfRooms(attribute));
            commandsMap.put("remove_greater", () -> removeGreater.removeGreater(attribute));
        }
        commandsMap.get(command).execute();
    }

    public void executeCommand(String fullCommand) throws ParserConfigurationException, TransformerException {
        String command = getCommand(fullCommand);
        String attribute = getAttribute(fullCommand);

        //for testing
        //System.out.println("command: " + command + ", attribute: " + attribute);

        try {
            if (attribute == null || attribute.trim().isEmpty()) commandsManager(command);
            else commandsManager(command, attribute);
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

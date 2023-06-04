package commands.commandsmanager;

import commands.CommandType;
import commands.commands.secured.Save;
import commands.commands.server.*;
import exceptions.FileHasBeenDeletedException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.*;

/**
 * The class CommandManager is used to execute any command from class CommandList
 * CommandManager has two @OverLoad methods: commandsManager() and commandsManager(String attribute)
 * The method executeCommand check attribute of command and if it is null will be called first commandManager() method, if it is not null will be called second commandsManager(String attribute) manager.
 * Then commandsManager(T) find command with attribute or without attribute and execute method which contains in the values of Map(String, CommandType) commandsMap.
 */
public class ServerCommandsManager extends CommandsManagerAbstract {
    // команды без ввода
    Help help = new Help();
    Info info = new Info();
    Show show = new Show();
    Clear clear = new Clear();
    Save save = new Save();
    SumOfNumberOfRooms sumOfNumberOfRooms = new SumOfNumberOfRooms();
    MaxByFurnish maxByFurnish = new MaxByFurnish();
    MaxByTransport maxByTransport = new MaxByTransport();
    MaxByView maxByView = new MaxByView();

    // команды с атрибутом
    Echo echo = new Echo();
    RemoveKey removeKey = new RemoveKey();
    CountByNumberOfRooms countByNumberOfRooms = new CountByNumberOfRooms();
    RemoveGreater removeGreater = new RemoveGreater();
    NullCommand nullCommand = new NullCommand();

    private Logger logger = LogManager.getLogger(ServerCommandsManager.class.getSimpleName());

    private void commandsManager(String command) {
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            //только серверные команды
            commandsMap.put(help.getCommandName(), () -> help.help());
            commandsMap.put(info.getCommandName(), () -> info.info());
            commandsMap.put(show.getCommandName(), () -> {
                try {
                    show.show();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            commandsMap.put(clear.getCommandName(), () -> clear.clear());
            commandsMap.put(sumOfNumberOfRooms.getCommandName(), () -> sumOfNumberOfRooms.sumOfNumbersOfRooms());
            commandsMap.put(maxByFurnish.getCommandName(), () -> maxByFurnish.maxByFurnish());
            commandsMap.put(maxByTransport.getCommandName(), () -> maxByTransport.maxByTransport());
            commandsMap.put(maxByView.getCommandName(), () -> maxByView.maxByView());
            commandsMap.put("execute_script", () -> nullCommand.nullCommand());
        }
        commandsMap.get(command).execute();
    }

    private void commandsManager(String command, String attribute) {
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            //только серверные команды
            commandsMap.put(echo.getCommandName(), () -> echo.echo(attribute));
            commandsMap.put(removeKey.getCommandName(), () -> removeKey.removeKey(Integer.valueOf(attribute)));
            commandsMap.put(countByNumberOfRooms.getCommandName(), () -> countByNumberOfRooms.countByNumberOfRooms(attribute));
            commandsMap.put(removeGreater.getCommandName(), () -> removeGreater.removeGreater(Integer.valueOf(attribute)));
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
            exception.printStackTrace();
            System.out.println("Please check if the command or its attribute is correct.");
            logger.info("Please check if the command or its attribute is correct.");
            System.out.println("A list of available commands can be found by entering the help command.");
            logger.info("A list of available commands can be found by entering the help command.");
        }
    }

    @Override
    public String description() {
        return "The ServerCommandsManager is used to manage only server commands.";
    }
}

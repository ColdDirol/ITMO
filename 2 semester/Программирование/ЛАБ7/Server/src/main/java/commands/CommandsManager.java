package commands;

import commands.clientcommands.*;
import serverlogic.outputchannel.ResponseArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The class CommandManager is used to execute any command from class CommandList
 * CommandManager has two @OverLoad methods: commandsManager() and commandsManager(String attribute)
 * The method executeCommand check attribute of command and if it is null will be called first commandManager() method, if it is not null will be called second commandsManager(String attribute) manager.
 * Then commandsManager(T) find command with attribute or without attribute and execute method which contains in the values of Map(String, CommandType) commandsMap.
 */
public class CommandsManager {
    ResponseArrayList responseArrayList = new ResponseArrayList();

    // команды без ввода
    Help help = new Help();
    Info info = new Info();
    Show show = new Show();
    Clear clear = new Clear();
    SumOfNumberOfRooms sumOfNumberOfRooms = new SumOfNumberOfRooms();
    MaxByView maxByView = new MaxByView();
    NullCommand nullCommand = new NullCommand();


    // команды с атрибутом
    Insert insert = new Insert();
    Echo echo = new Echo();
    Update update = new Update();
    RemoveKey removeKey = new RemoveKey();
    ReplaceIfGreater replaceIfGreater = new ReplaceIfGreater();
    ReplaceIfLowe replaceIfLowe = new ReplaceIfLowe();
    CountByNumberOfRooms countByNumberOfRooms = new CountByNumberOfRooms();

    //work
    private void commandsManager(String command, Integer userId){
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            commandsMap.put(help.toString(), () -> help.help());
            commandsMap.put(info.toString(), () -> info.info(userId)); // -userId
            commandsMap.put(show.toString(), () -> {
                try {
                    show.show(userId);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }); // -userId
            commandsMap.put(clear.toString(), () -> clear.clear(userId)); // -userId
            commandsMap.put(sumOfNumberOfRooms.toString(), () -> sumOfNumberOfRooms.sumOfNumbersOfRooms(userId)); // -userId
            commandsMap.put(maxByView.toString(), () -> maxByView.maxByView(userId)); // -userId
            commandsMap.put(nullCommand.toString(), () -> nullCommand.nullCommand());
        }
        commandsMap.get(command).execute();
    }

    //test in the progress
    private void commandsManager(String command, String attribute, Integer clientId, Integer userId) throws NullPointerException {
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            commandsMap.put(echo.toString(), () -> echo.echo(attribute));
            commandsMap.put(insert.toString(), () -> insert.insert(attribute, clientId, userId)); // -userId, clientId
            commandsMap.put(update.toString(), () -> update.update(attribute, clientId, userId)); // -userId, clientId
            commandsMap.put(removeKey.toString(), () -> removeKey.removeKey(attribute, userId)); // -userId
            commandsMap.put("replace_if_greater", () -> replaceIfGreater.replaceIfGreater(attribute, clientId, userId)); // -userId, clientId
            commandsMap.put("replace_if_lowe", () -> replaceIfLowe.replaceIfLowe(attribute, clientId, userId)); // -userId, clientId
            commandsMap.put(countByNumberOfRooms.toString(), () -> countByNumberOfRooms.countByNumberOfRooms(attribute, userId)); // -userId
        }
        commandsMap.get(command).execute();
    }

    //work
    public void executeCommand(String fullCommand, Integer clientId, Integer userId) throws ParserConfigurationException, TransformerException {
        //System.out.println("FC: " + fullCommand + " CID: " + clientId + " UID: " + userId);
        String command = getCommand(fullCommand);
        String attribute = getAttribute(fullCommand);

        //for testing
        System.out.println("command: " + command + ", attribute: " + attribute);

        try {
            if (attribute == null || attribute.trim().isEmpty()) {
                commandsManager(command, userId);
            }
            else {
                commandsManager(command, attribute, clientId, userId);
            }
        } catch (NullPointerException e) {
            System.out.println("Please check if the command or its attribute is correct.");
            responseArrayList.addStringToResponseArrayList("Please check if the command or its attribute is correct.");
            System.out.println("A list of available commands can be found by entering the help command.");
            responseArrayList.addStringToResponseArrayList("A list of available commands can be found by entering the help command.");
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

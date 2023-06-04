package commands.commandsmanager;

import commands.CommandType;
import commands.commands.secured.Save;
import commands.commands.secured.SaveExit;
import commands.commands.secured.history.ShowHistory;
import commands.commands.secured.history.TwistHistory;
import exceptions.FileHasBeenDeletedException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SecuredServerCommandsManager extends CommandsManagerAbstract {
    Save save = new Save();
    SaveExit saveExit = new SaveExit();
    ShowHistory showHistory = new ShowHistory();

    TwistHistory twistHistory = new TwistHistory();

    private Logger logger = LogManager.getLogger(ServerCommandsManager.class.getSimpleName());
    private void commandsManager(String command) throws FileHasBeenDeletedException {
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            // Show history of changes
            commandsMap.put(showHistory.getCommandName(), () -> showHistory.showHistory());
            // Save
            commandsMap.put(save.getCommandName(), () -> {
                try {
                    save.save();
                } catch (ParserConfigurationException | TransformerException | FileHasBeenDeletedException e) {
                    throw new RuntimeException(e);
                }
            });
            // Save and exit
            commandsMap.put(saveExit.getCommandName(), () -> {
                try {
                    saveExit.saveExit();
                } catch (FileHasBeenDeletedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        commandsMap.get(command).execute();
    }


    private void commandsManager(String command, String attribute) {
        Map<String, CommandType> commandsMap = new LinkedHashMap<>();
        {
            commandsMap.put(twistHistory.getCommandName(), () -> twistHistory.twistHistory(Integer.valueOf(attribute)));
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
        } catch (NullPointerException | FileHasBeenDeletedException exception) {
            exception.printStackTrace();
            System.out.println("Please check if the command or its attribute is correct.");
            logger.info("Please check if the command or its attribute is correct.");
            System.out.println("A list of available commands can be found by entering the help command.");
            logger.info("A list of available commands can be found by entering the help command.");
        }
    }

    @Override
    public String description() {
        return "The SecuredServerCommandsManager is used to manage only secured server commands.";
    }
}

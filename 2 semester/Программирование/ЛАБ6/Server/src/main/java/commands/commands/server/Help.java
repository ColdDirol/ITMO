package commands.commands.server;

import commands.CommandsMap;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

import java.util.*;

public class Help implements CommandInterface {
    CommandsMap commands = new CommandsMap();

    ResponseArrayList responseArrayList = new ResponseArrayList();

    Map<String, String> commandsMap = commands.getCommandsMapInfo();
    private Logger logger = LogManager.getLogger(Help.class.getSimpleName());

    public void help() {
        for(Map.Entry<String, String> item : commandsMap.entrySet()) {
            System.out.println(item.getKey() + ": " + item.getValue());
            responseArrayList.addElementToTheResponseArrayList(item.getKey() + ": " + item.getValue());
            logger.info(item.getKey() + ": " + item.getValue());
        }
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to show available commands.";
    }
}

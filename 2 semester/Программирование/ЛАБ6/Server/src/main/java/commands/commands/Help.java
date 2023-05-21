package commands.commands;

import commands.CommandsMap;
import serverlogic.ResponseArrayList;

import java.util.*;

public class Help implements Command {
    CommandsMap commands = new CommandsMap();

    ResponseArrayList responseArrayList = new ResponseArrayList();

    Map<String, String> commandsMap = commands.getCommandsMapInfo();

    public void help() {
        for(Map.Entry<String, String> item : commandsMap.entrySet()) {
            System.out.println(item.getKey() + ": " + item.getValue());
            responseArrayList.addElementToTheResponseArrayList(item.getKey() + ": " + item.getValue());
        }
    }

    @Override
    public String toString() {
        return "help";
    }

    @Override
    public String description() {
        return "The \"help\" command is used to show available commands.";
    }
}

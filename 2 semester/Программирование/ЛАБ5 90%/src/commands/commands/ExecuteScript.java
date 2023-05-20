package commands.commands;

import commands.CommandsList;
import commands.CommandsManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The class ExecuteScript is used to execute the *execute_script file_name* command
 */
public class ExecuteScript {
    CommandsManager commandsManager;
    CommandsList commandsList = new CommandsList();
    public void executeScript(String filepath) throws ParserConfigurationException, TransformerException {
        try {
            commandsManager = new CommandsManager();
            Path path = Paths.get(filepath);
            Scanner scanner = new Scanner(path);
            try {
                scanner.useDelimiter(System.getProperty("line.separator"));
                String line;
                while(scanner.hasNext()){
                    commandsList.printRecursionSet();
                    line = scanner.nextLine();
                    if(line == null || line.trim().isEmpty()) continue;
                    if(isComment(line)) {
                        System.out.println(line + " WAS COMMENTED");
                        continue;
                    }
                    System.out.println("--" + line);
                    if(needScanner(line)) commandsManager.executeCommand(line, scanner);
                    else {
                        if(commandsList.isRecursionSetElement(commandsManager.getAttribute(line))) {
                            System.out.println("Recursion has been detected!");
                            System.out.println("COMMAND EXECUTION WAS DESTROYED!");
                            break;
                        }
                        if(commandsManager.getCommand(line).equals(this.toString())) {
                            commandsList.addRecursionSetElement(commandsManager.getAttribute(line));
                        }
                        commandsManager.executeCommand(line);
                        commandsList.removeRecursionSetElement(commandsManager.getAttribute(line));
                    }
                }
                //commandsList.clearRecursionSet();
                scanner.close();
            }
            catch (NullPointerException exception) {
                System.out.println("Please check if the command or its attribute is correct.");
                System.out.println("A list of available commands can be found by entering the \"help\" command.");
            }
        } catch (FileNotFoundException exception) {
            System.err.println("File not found, please check if the name is correct and try again");
        } catch (IOException exception) {
            System.err.println("Error. Check if the execute_script command was entered correctly and try again");
        }
    }

    private boolean isComment(String fullcommand) {
        try {
            if (fullcommand.charAt(0) == '#') return true;
            else return false;
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }

    //work
    private boolean needScanner(String fullCommand) {
        Set<String> setWithScannerCommandsKeys = commandsList.getSetWithScannerCommandsKeys();
        commandsManager = new CommandsManager();
        String command = commandsManager.getCommand(fullCommand);

        if(setWithScannerCommandsKeys.contains(command)) return true;
        else return false;
    }

    @Override
    public String toString() {
        return "execute_script";
    }
}

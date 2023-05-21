package commands.commands;

import clientlogic.ConsoleOutputMode;
import commands.ClientSideCommandsSet;
import commands.CommandsManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class ExecuteScript extends ClientSideCommandsSet implements Command {
    CommandsManager commandsManager;

    String request;

    public void executeScript(String filepath, Socket socket) throws ParserConfigurationException, TransformerException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            commandsManager = new CommandsManager();
            Path path = Paths.get(filepath);
            Scanner scanner = new Scanner(path);
            addRecursionSetElement(filepath);
            try {
                scanner.useDelimiter(System.getProperty("line.separator"));
                String line;
                boolean recursion = false;
                while(scanner.hasNext()){
                    Thread.sleep(20);
                    line = scanner.nextLine();
                    if(line == null || line.trim().isEmpty()) continue;
                    if(isComment(line)) {
                        System.out.println(line + " WAS COMMENTED");
                        continue;
                    }
                    //System.out.println("--" + line);
                    if(needScanner(line)) {
                        request = commandsManager.executeCommand(line, scanner, ConsoleOutputMode.UNREADABLE);
                        //System.out.println(request);
                        sendRequest(bufferedWriter, request);
                        getResponse(bufferedReader);
                        continue;
                    }
                    // is working ^
                    if(line.contains(this.toString())) {
                        //для execute_script
                        if(isRecursionSetElement(commandsManager.getAttribute(line))) {
                            sendRequest(bufferedWriter, this.toString()); //стоп сигнал
                            getResponse(bufferedReader);
                            System.out.println();
                            System.out.println("Recursion has been detected!");
                            System.out.println("COMMAND EXECUTION WAS DESTROYED!");
                            break;
                        }
                        addRecursionSetElement(commandsManager.getAttribute(line));
                        commandsManager.executeCommand(line, socket);
                        continue;
                    }
                    sendRequest(bufferedWriter, this.toString()); //стоп-сигнал
                    getResponse(bufferedReader);
                }
            }
            catch (NullPointerException exception) {
                System.out.println("Please check if the command or its attribute is correct.");
                System.out.println("A list of available commands can be found by entering the \"help\" command.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("File not found, please check if the name is correct and try again");
        } catch (IOException exception) {
            System.out.println("ERROR. Check if the execute_script command was entered correctly and try again");
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
        Set<String> setWithScannerCommandsKeys = getSetWithScannerCommandsKeys();
        commandsManager = new CommandsManager();
        String command = commandsManager.getCommand(fullCommand);

        if(setWithScannerCommandsKeys.contains(command)) return true;
        else return false;
    }

    private static void sendRequest(BufferedWriter bufferedWriter, String message) throws IOException {
        System.out.println(message);
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    private static void getResponse(BufferedReader bufferedReader) throws IOException {
        String response = null;
        while (bufferedReader.ready()) {
            response = bufferedReader.readLine();
            if(response.contains("Enter a command: ")) continue;
            else System.out.println(response);
        }
    }

    @Override
    public String toString() {
        return "execute_script";
    }

    @Override
    public String description() {
        return null;
    }
}

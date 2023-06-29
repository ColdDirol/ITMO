package com.itmo.client.commands.clientcommands;

import com.itmo.client.clientlogic.inputlogic.ResponseArrayList;
import com.itmo.client.clientlogic.outputlogic.CredentialsMapEntry;
import com.itmo.client.commands.ClientCommands;
import org.json.simple.JSONObject;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ExecuteScript {
    CredentialsMapEntry credentialsMapEntry = new CredentialsMapEntry();
    ClientCommands clientCommands = new ClientCommands();
    ResponseArrayList responseStringChannel = new ResponseArrayList();
    Socket socket = new Socket();
    public void executeScript(File scriptfile, Socket socket) throws ParserConfigurationException, TransformerException {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // response <

            // >

//            // request <
            JSONObject requestJSONObject = new JSONObject();
            requestJSONObject.put("command", "null_command");
            requestJSONObject.put("username", credentialsMapEntry.getUsername());
            requestJSONObject.put("password", credentialsMapEntry.getPassword());

            String requestString = requestJSONObject.toString();
//            // >
//
            bufferedWriter.write(requestString);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(scriptfile);
            try {
                scanner.useDelimiter(System.getProperty("line.separator"));
                String line;
                while(scanner.hasNext()){
                    Thread.sleep(20);

                    while (bufferedReader.ready()) {
                        Thread.sleep(100);
                        String responseString = bufferedReader.readLine();
                    }

                    //clientCommands.printRecursionSet();
                    line = scanner.nextLine();
                    if (line == null || line.trim().isEmpty()) continue;
                    if (isComment(line)) {
                        System.out.println(line + " WAS COMMENTED");
                        continue;
                    }
                    //System.out.println("--" + line);

                    if (clientCommands.isRecursionSetElement(getAttribute(line))) {
                        System.out.println("Recursion has been detected!");
                        System.out.println("COMMAND EXECUTION WAS DESTROYED!");
                        break;
                    }
                    if (getCommand(line).equals(this.toString())) {
                        clientCommands.addRecursionSetElement(getAttribute(line));
                        executeScript(getAttribute(line), socket);
                    }

                    requestJSONObject = new JSONObject();
                    requestJSONObject.put("command", line);
                    requestJSONObject.put("username", credentialsMapEntry.getUsername());
                    requestJSONObject.put("password", credentialsMapEntry.getPassword());

                    requestString = requestJSONObject.toString();

                    bufferedWriter.write(requestString);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    clientCommands.removeRecursionSetElement(getAttribute(line));
                }

                requestJSONObject = new JSONObject();
                requestJSONObject.put("command", "null_command");
                requestJSONObject.put("username", credentialsMapEntry.getUsername());
                requestJSONObject.put("password", credentialsMapEntry.getPassword());

                requestString = requestJSONObject.toString();
//            // >
//
                bufferedWriter.write(requestString);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                String responseString = bufferedReader.readLine();
                scanner.close();
            }
            catch (NullPointerException exception) {
                System.out.println("Please check if the command or its attribute is correct.");
                System.out.println("A list of available commands can be found by entering the \"help\" command.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException exception) {
            System.err.println("File not found, please check if the name is correct and try again");
        } catch (IOException exception) {
            System.err.println("Error. Check if the execute_script command was entered correctly and try again");
        }
    }

    public void executeScript(String filepath, Socket socket) throws ParserConfigurationException, TransformerException {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // response <

            // >

//            // request <
            JSONObject requestJSONObject = new JSONObject();
            requestJSONObject.put("command", "null_command");
            requestJSONObject.put("username", credentialsMapEntry.getUsername());
            requestJSONObject.put("password", credentialsMapEntry.getPassword());

            String requestString = requestJSONObject.toString();
//            // >
//
            bufferedWriter.write(requestString);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Path path = Paths.get(filepath);
            Scanner scanner = new Scanner(path);
            try {
                scanner.useDelimiter(System.getProperty("line.separator"));
                String line;
                while(scanner.hasNext()){
                    Thread.sleep(20);

                    while (bufferedReader.ready()) {
                        Thread.sleep(100);
                        String responseString = bufferedReader.readLine();
                    }

                    //clientCommands.printRecursionSet();
                    line = scanner.nextLine();
                    if (line == null || line.trim().isEmpty()) continue;
                    if (isComment(line)) {
                        System.out.println(line + " WAS COMMENTED");
                        continue;
                    }
                    //System.out.println("--" + line);

                    if (clientCommands.isRecursionSetElement(getAttribute(line))) {
                        System.out.println("Recursion has been detected!");
                        System.out.println("COMMAND EXECUTION WAS DESTROYED!");
                        break;
                    }
                    if (getCommand(line).equals(this.toString())) {
                        clientCommands.addRecursionSetElement(getAttribute(line));
                        executeScript(getAttribute(line), socket);
                    }

                    requestJSONObject = new JSONObject();
                    requestJSONObject.put("command", line);
                    requestJSONObject.put("username", credentialsMapEntry.getUsername());
                    requestJSONObject.put("password", credentialsMapEntry.getPassword());

                    requestString = requestJSONObject.toString();

                    bufferedWriter.write(requestString);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    clientCommands.removeRecursionSetElement(getAttribute(line));
                }

                requestJSONObject = new JSONObject();
                requestJSONObject.put("command", "null_command");
                requestJSONObject.put("username", credentialsMapEntry.getUsername());
                requestJSONObject.put("password", credentialsMapEntry.getPassword());

                requestString = requestJSONObject.toString();
//            // >
//
                bufferedWriter.write(requestString);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                String responseString = bufferedReader.readLine();
                scanner.close();
            }
            catch (NullPointerException exception) {
                System.out.println("Please check if the command or its attribute is correct.");
                System.out.println("A list of available commands can be found by entering the \"help\" command.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
//    private boolean needSocket(String fullCommand) {
//        Set<String> setWithScannerCommandsKeys = clientCommands.getSetWithSocketCommandsKeys();
//        commandsManager = new CommandsManager();
//        String command = commandsManager.getCommand(fullCommand);
//
//        if(setWithScannerCommandsKeys.contains(command)) return true;
//        else return false;
//    }

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

    @Override
    public String toString() {
        return "execute_script";
    }
}

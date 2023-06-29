//package com.itmo.client.clientlogic;
//
//import clientlogic.inputlogic.ResponseStringChannel;
//import clientlogic.outputlogic.CredentialsMapEntry;
//import clientlogic.outputlogic.RequestDataType;
//import commands.ClientCommands;
//import commands.CommandManager;
//import commands.clientcommands.Exit;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.TransformerException;
//import java.io.*;
//import java.net.Socket;
//import java.util.HashSet;
//import java.util.NoSuchElementException;
//import java.util.Scanner;
//
//public class TCPClient {
//    private static String SERVER_HOST;
//    private static Integer SERVER_PORT;
//
//    ResponseStringChannel responseStringChannel = new ResponseStringChannel();
//    private Socket socket;
//
//    CommandManager commandManager = new CommandManager();
//    ClientCommands clientCommands = new ClientCommands();
//    private HashSet<String> clientCommandsHashSet = clientCommands.getClientCommandsHashSet();
//
//    Scanner scanner = new Scanner(System.in);
//
//    public TCPClient(String SERVER_HOST, int SERVER_PORT) throws IOException, InterruptedException {
//        this.SERVER_HOST = SERVER_HOST;
//        this.SERVER_PORT = SERVER_PORT;
//
//        connect();
//        run();
//    }
//
//    public void run() throws IOException, InterruptedException {
//        while (true) {
//            try (
//                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            ) {
//                System.out.println("Client has been connected to the server");
//                Thread.sleep(2000);
//                CredentialsMapEntry credentialsMapEntry = null;
//
//                String answer = null;
//                String username;
//                String password;
//
//                // Authorization
//                while (true) {
//                    // mode
//                    checkConnect();
//                    if (bufferedReader.ready()) {
//                        while (bufferedReader.ready()) {
//                            checkConnect();
//                            responseStringChannel.addElementToTheRefuseString(bufferedReader.readLine());
//                            Thread.sleep(20);
//                        }
//                        checkConnect();
//                        responseStringChannel.readRefuseStringFromServer();
//                    }
//                    while (true) {
//                        try {
//                            answer = scanner.nextLine();
//                            try {
//                                if (Integer.parseInt(answer) == 3) Exit.exit(socket);
//                                if (Integer.parseInt(answer) >= 1 && Integer.parseInt(answer) <= 3) break;
//                                System.out.print("Enter answer correctly: ");
//                            } catch (NumberFormatException e) {
//                                System.out.print("Please, enter a number: ");
//                            }
//                        } catch (NoSuchElementException e) {
//                            System.out.println("Недопустимый символ!");
//                        }
//                    }
//                    sendFastResponse(bufferedWriter, answer);
//                    Thread.sleep(50);
//
//                    // username
//                    checkConnect();
//                    if (bufferedReader.ready()) {
//                        while (bufferedReader.ready()) {
//                            checkConnect();
//                            responseStringChannel.addElementToTheRefuseString(bufferedReader.readLine());
//                        }
//                        responseStringChannel.readRefuseStringFromServer();
//                    }
//                    while (true) {
//                        try {
//                            username = scanner.nextLine();
//                            while (username.length() > 20 || username.trim().isEmpty()) {
//                                if (username.length() > 20)
//                                    System.out.print("Length of your username must be less then 20: ");
//                                if (username.trim().isEmpty()) System.out.print("Username can't be empty: ");
//                                username = scanner.nextLine();
//                            }
//                            break;
//                        } catch (NoSuchElementException e) {
//                            System.out.println("Недопустимый символ!");
//                        }
//                    }
//                    sendFastResponse(bufferedWriter, username);
//                    Thread.sleep(50);
//
//                    // password
//                    checkConnect();
//                    if (bufferedReader.ready()) {
//                        while (bufferedReader.ready()) {
//                            checkConnect();
//                            responseStringChannel.addElementToTheRefuseString(bufferedReader.readLine());
//                        }
//                        responseStringChannel.readRefuseStringFromServer();
//                    }
//                    while (true) {
//                        try {
//                        password = scanner.nextLine();
//                        while (password.length() != 8) {
//                            if (username.length() != 8)
//                                System.out.print("Length of your password must be equals to 8: ");
//                            if (username.trim().isEmpty()) System.out.print("Password can't be empty: ");
//                            username = scanner.nextLine();
//                        }
//                        break;
//                        } catch (NoSuchElementException e) {
//                            System.out.println("Недопустимый символ!");
//                        }
//                    }
//                    sendFastResponse(bufferedWriter, password);
//                    Thread.sleep(50);
//
//
//                    if (Boolean.parseBoolean(bufferedReader.readLine())) {
//                        credentialsMapEntry = new CredentialsMapEntry(username, password);
//                        System.out.println("Access Granted!");
//                        break;
//                    }
//                    Thread.sleep(50);
//                }
//
//                RequestDataType requestDataType = new RequestDataType(null, credentialsMapEntry);
//                Thread.sleep(50);
//
//                // Work
//                String command;
//                while (true) {
//                    try {
//                        if (bufferedReader.ready()) {
//                            checkConnect();
//                            String responseString = bufferedReader.readLine();
//
//                            // parse responseString to JSONObject
//                            JSONParser jsonParser = new JSONParser();
//                            JSONObject responseJSONObject = (JSONObject) jsonParser.parse(responseString);
//
//                            // parse JSONObject to JSONArray
//                            JSONArray jsonArray = (JSONArray) responseJSONObject.get("response");
//                            for (Object object : jsonArray) {
//                                System.out.print(object);
//                            }
//
//                            command = scanner.nextLine();
//
//                            if (clientCommandsHashSet.contains(commandManager.getCommand(command))) {
//                                commandManager.executeCommand(command, socket);
//                                continue;
//                            }
//
//                            JSONObject requestJSONObject = new JSONObject();
//                            requestJSONObject.put("command", command);
//                            requestJSONObject.put("username", credentialsMapEntry.getUsername());
//                            requestJSONObject.put("password", credentialsMapEntry.getPassword());
//
//                            String requestString = requestJSONObject.toString();
//
//                            sendFastResponse(bufferedWriter, requestString);
//                        }
//                    } catch (NullPointerException e) {
//                        System.out.println("Wrong request!");
//                    } catch (NoSuchElementException e) {
//                        System.out.println("ERROR");
//                        System.out.println("Wrong request!");
//                        System.exit(0);
//                    } catch (ParseException e) {
//                        throw new RuntimeException(e);
//                    } catch (ParserConfigurationException e) {
//                        throw new RuntimeException(e);
//                    } catch (TransformerException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            } catch (IOException e) {
//                System.out.println("Connection with Server " + SERVER_HOST + ":" + SERVER_PORT + " has been LOST!");
//
//                boolean hasConnection = false;
//                String temp;
//                while (!hasConnection) {
//                    try {
//                        System.out.print("Press ENTER to reconnect");
//                        temp = scanner.nextLine();
//                        System.out.println("Try to reconnect...");
//                        connect();
//                        run();
//                        hasConnection = true;
//                    } catch (IOException exception) {
//                        System.out.println("Connection failed!");
//                        System.out.println();
//                    }
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public void connect() throws IOException {
//        socket = new Socket(SERVER_HOST, SERVER_PORT);
//    }
//
//    public void checkConnect() throws IOException {
//        Socket checkSocket = new Socket(SERVER_HOST, SERVER_PORT);
//        checkSocket.close();
//    }
//
//    public void sendFastResponse(BufferedWriter bufferedWriter, String message) throws IOException {
//        checkConnect();
//        bufferedWriter.write(message);
//        bufferedWriter.newLine();
//        bufferedWriter.flush();
//    }
//}

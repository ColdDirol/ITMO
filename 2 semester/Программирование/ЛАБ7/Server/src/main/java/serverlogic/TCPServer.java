package serverlogic;

import commands.CommandsManager;
import commands.ServerCommandsManager;
import database.DatabaseManager;
import database.HashingMD2;
import database.actions.UserActions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import serverlogic.authorization.Authorization;
import serverlogic.outputchannel.IOHelperClass;
import serverlogic.outputchannel.ResponseLogic;
import serverlogic.outputchannel.ResponseArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
    ClientCuncurrentHashMap clientCuncurrentHashMap = new ClientCuncurrentHashMap();
    private static ExecutorService readPool = Executors.newFixedThreadPool(10);
    private static ExecutorService processPool = Executors.newFixedThreadPool(20);

    private Integer PORT;

    ResponseLogic responseLogic = new ResponseLogic();
    DatabaseManager databaseManager = null;
    HashingMD2 hashingMD2 = new HashingMD2();
    private String answer = null;
    private String username = null;
    private String password = null;

    UserActions userActions = new UserActions();
    CommandsManager commandsManager = new CommandsManager();
    ServerCommandsManager serverCommandsManager = new ServerCommandsManager();
    BufferedReader consoleBufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String serverCommand;

    public TCPServer(Integer PORT, DatabaseManager databaseManager) {
        this.PORT = PORT;
        this.databaseManager = databaseManager;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Add client to the map <
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                int clientId = clientSocket.hashCode();
                IOHelperClass ioHelperClass = new IOHelperClass(bufferedWriter, bufferedReader);
                clientCuncurrentHashMap.addElementToClientConcurrentHashMap(clientId, ioHelperClass);
                // >

                ResponseArrayList responseArrayList = new ResponseArrayList();

                // New thread for reading requests
                readPool.submit(() -> {
                    try {
                        // authorization
                        Authorization authorization = new Authorization(clientId);
                        authorization.authorization();

                        String requestString = null;

                        while (true) {
                            // send Response
                            responseArrayList.addStringToResponseArrayList("Enter a command: ");
                            processPool.submit(() -> {
                                try {
                                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            if(consoleBufferedReader.ready()) {
                                serverCommand = consoleBufferedReader.readLine();

                                serverCommandsManager.executeCommand(serverCommand);
                            }

                            // get Request
                            requestString = responseLogic.getRequest(clientId);

                            // parse responseString to JSONObject
                            JSONParser jsonParser = new JSONParser();
                            JSONObject responseJSONObject = (JSONObject) jsonParser.parse(requestString);

                            // parse JSONObject to Strings
                            // command executing
//                            System.out.println((String) responseJSONObject.get("command") + " " + clientId + " " + (String) responseJSONObject.get("username") + " " + (String) responseJSONObject.get("password"));
                            commandsManager.executeCommand((String) responseJSONObject.get("command"), clientId,
                                    userActions.getId((String) responseJSONObject.get("username"), (String) responseJSONObject.get("password")));
                        }
                    } catch (IOException e) {
                        //System.out.println("Client has been disconnected!");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    } catch (TransformerException e) {
                        throw new RuntimeException(e);
                    }
                });

                // New thread for sending responses
                new Thread(() -> {
                    try {
                        while (clientCuncurrentHashMap.containsKey(clientId)) {
                            Thread.sleep(100);
                        }
                        bufferedWriter.close();
                        bufferedReader.close();
                        clientSocket.close();
                        System.out.println("Client " + clientId + " disconnected.");
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void processRequest(String request, int clientId) {
//        String response = "Echo: " + request;
//        PrintWriter printWriter = clientMap.get(clientId);
//        if (printWriter != null) {
//            printWriter.println(response);
//        }
//    }
//
//    private static void sendFastResponse(BufferedWriter bufferedWriter, String message) throws IOException {
//        bufferedWriter.write(message);
//        bufferedWriter.newLine();
//        bufferedWriter.flush();
//    }
//    private static void sendFastResponse(BufferedWriter bufferedWriter, ArrayList<String> messageList) throws IOException {
//        for(String message : messageList) {
//            bufferedWriter.write(message);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//        }
//    }
}

//    public void run() {
//        while (true) {
//            System.out.println("Добрый день! Пожалуйста, авторизуйтесь:");
//            System.out.println("1. Авторизоваться");
//            System.out.println("2. Зарегистрироваться");
//            answer = scanner.nextLine();
//
//            if (answer.equals("1")) {
//                System.out.print("Пожалуйста, введите username: ");
//                username = scanner.nextLine();
//                System.out.print("Пожалуйста, введите пароль: ");
//                password = scanner.nextLine();
//
//                if (userActions.userExists(username) & userActions.getPermisionToLogin(username, password)) {
//                    System.out.println("Вход разрешен!");
//                    User user = userActions.getUser(username, hashingMD2.encodeStringMD2(password));
//                    System.out.println(user.getId());
//                    System.out.println(user.getUsername());
//                }
//            }
//            if (answer.equals("2")) {
//                System.out.print("Пожалуйста, придумайте username: ");
//                username = scanner.nextLine();
//                while (userActions.userExists(username)) {
//                    username = scanner.nextLine();
//                }
//                System.out.print("Пожалуйста, придумайте пароль: ");
//                password = scanner.nextLine();
//                while (password.length() < 7) {
//                    password = scanner.nextLine();
//                }
//
//                userActions.registerUser(username, password);
//                User user = userActions.getUser(username, hashingMD2.encodeStringMD2(password));
//                System.out.println(user.getId());
//                System.out.println(user.getUsername());
//            }
//        }
//    }

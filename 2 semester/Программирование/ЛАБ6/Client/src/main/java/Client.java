import clientlogic.output.ConsoleOutputMode;
import commands.ClientSideCommandsSet;
import commands.CommandsManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Client {
    private final static String SERVER_HOST = "localhost";
    private final static Integer SERVER_PORT = 5555;

    static SocketChannel clientChannel;
    private static Socket socket;
    static BufferedReader bufferedReader;
    static BufferedWriter bufferedWriter;


    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        while (true) {
            try {
                // Connect to the echo server on localhost and port 5555
                clientChannel = SocketChannel.open();
                clientChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

                // Work with socket
                // <
                socket = clientChannel.socket();
                // Get the input and output streams to the server
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                // >


                // Main objects creation <
                ClientSideCommandsSet clientSideCommandsSet = new ClientSideCommandsSet();
                CommandsManager commandsManager = new CommandsManager();

                Scanner scanner = new Scanner(System.in);
                // >

                String request;
                String command;
                while (true) {
                    try {
                        if (bufferedReader.ready()) {
                            getResponse(bufferedReader);
                            //ввод следующей команды
                            //System.out.print("Enter a command: ");
                            while (true) {
                                command = scanner.nextLine();
                                checkServerAvailability();
                                try {
                                    //выполнение общей команды (если нужно)
                                    if (clientSideCommandsSet.commonSideCommandsContains(commandsManager.getCommand(command))) {
                                        //выполнение команды
                                        request = commandsManager.executeCommand(command, scanner, ConsoleOutputMode.READABLE);
                                        sendRequest(bufferedWriter, request);
                                        continue;
                                    }
                                    //выполнение ТОЛЬКО клиентской команды
                                    if (clientSideCommandsSet.clientSideCommandsContains(commandsManager.getCommand(command))) {
                                        //выполнение команды
                                        commandsManager.executeCommand(command, socket);
                                        clientSideCommandsSet.clearRecursionSet();
                                        continue;
                                    }
                                    break;
                                } catch (NullPointerException e) {
                                    System.out.print("Enter a command: ");
                                }
                            }

                            //если нет - отправка команды на сервер
                            sendRequest(bufferedWriter, command);
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("NoSuchElementException: " + e.getMessage());
                        System.exit(0);
                    } catch (NullPointerException e) {
                        System.out.println("NullPointerException: " + e.getMessage());
                        sendRequest(bufferedWriter, "null_command");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                //System.out.println("IOException: " + e.getMessage());
                reconnect();
            }
        }
    }

    private static void checkServerAvailability() throws IOException {
        try {
            Socket tmpSocket = new Socket();
            tmpSocket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            tmpSocket.close();
        } catch (ConnectException e) {
            throw new IOException();
        } catch (SocketTimeoutException e) {
            throw new IOException();
        } catch (IOException e) {
            throw new IOException();
        }
    }
    private static void getResponse(BufferedReader bufferedReader) throws IOException, InterruptedException {
        // Server check
        // checkServerAvailability();


        String response;
        //Чтение ArrayList'а ответов сервера
        while (bufferedReader.ready()) {
            response = bufferedReader.readLine();
            if (response.equals("STOP")) {
                Thread.sleep(10);
                reconnect();
            }
            if (response.isEmpty()) continue;
            if (!bufferedReader.ready()) {
                System.out.print(response);
                break;
            } else System.out.println(response);
        }
    }
    private static void sendRequest(BufferedWriter bufferedWriter, String message) throws IOException {
        // Server check
        checkServerAvailability();


        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    private static void reconnect() {
        System.out.println("Connection with Server " + SERVER_HOST + ":" + SERVER_PORT + " has been LOST!");
        Scanner scanner = new Scanner(System.in);
        boolean hasConnection = false;
        String temp;
        while(!hasConnection) {
            System.out.print("Press ENTER to reconnect!");
            temp = scanner.nextLine();
            hasConnection = true;
        }
    }
}
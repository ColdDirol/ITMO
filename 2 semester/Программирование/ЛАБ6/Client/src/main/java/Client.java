import clientlogic.ConsoleOutputMode;
import commands.ClientSideCommandsSet;
import commands.CommandsManager;
import commands.commands.ExecuteScript;

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


                // Work with sockets <
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
                            command = scanner.nextLine();

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

                            //если нет - отправка команды на сервер
                            sendRequest(bufferedWriter, command);
                        }
                    } catch (NoSuchElementException e) {
                        System.exit(0);
                    } catch (NullPointerException e) {
                        System.out.println("Wrong request!");
                        sendRequest(bufferedWriter, "null_command");
                    }
                }
            } catch (IOException e) {
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
    }

    private static void getResponse(BufferedReader bufferedReader) throws IOException {
        String response;

        //Чтение ArrayList'а ответов сервера
        while (bufferedReader.ready()) {
            response = bufferedReader.readLine();
            if(response.isEmpty()) continue;
            if(!bufferedReader.ready()) {
                System.out.print(response);
                break;
            } else System.out.println(response);
        }
    }

    private static void sendRequest(BufferedWriter bufferedWriter, String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
}
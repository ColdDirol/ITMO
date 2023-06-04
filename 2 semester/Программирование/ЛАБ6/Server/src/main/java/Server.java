import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

import collection.CollectionManager;
import commands.commandsmanager.SecuredServerCommandsManager;
import commands.commandsmanager.ServerCommandsManager;
import commands.commandsmanager.CommonCommandsManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import serverlogic.condition.CommandBelonging;
import serverlogic.input.CommandWrapperClass;
import serverlogic.input.RequestInputMode;
import serverlogic.output.ResponseArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Server {
    private static String HOSTNAME = "localhost";
    private static Integer PORT = 5555;
    private static int BUF_SIZE = 256;
    private static int TIMEOUT = 3000; // Wait timeout (milliseconds)
    private static Logger logger = LogManager.getLogger(Server.class.getSimpleName());

    public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, TransformerException, SAXException {
        // Create BufferedReader to read the console
        BufferedReader consoleBufferedReader = new BufferedReader(new InputStreamReader(System.in));


        // Projects objects
        CommandBelonging commandBelonging = new CommandBelonging();
        ServerCommandsManager serverCommandsManager = new ServerCommandsManager();
        CommonCommandsManager commonCommandsManager = new CommonCommandsManager();
        SecuredServerCommandsManager securedServerCommandsManager = new SecuredServerCommandsManager();

        // Read collection from xml and save to the Collection class
        System.out.println("XML parsing started...");
        logger.info("XML parsing started...");
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.collectionReader();
        System.out.println("XML parsing finished!");
        logger.info("XML parsing finished!");


        // Create a new selector
        Selector selector = Selector.open();


        // Create a new non-blocking server socket channel
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);


        // Bind the server socket to localhost and port 5555
        InetSocketAddress isa = new InetSocketAddress(HOSTNAME, PORT);
        serverChannel.socket().bind(isa);


        // Register the server socket channel with the selector
        SelectionKey serverKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server started on port 5555");
        logger.info("Server started on port 5555");


        while (true) {
            // Wait for a connection
            if (selector.select(TIMEOUT) == 0) {
                continue;
            }


            // Create commandWrapperClass which contains (String command, RequestInputMode requestInputMode)
            CommandWrapperClass commandWrapperClass = new CommandWrapperClass();


            // Get all keys with ready events
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

            while (keyIter.hasNext()) {
                try {
                    SelectionKey key = keyIter.next();

                    if (key.isAcceptable()) {
                        // Accept a new connection
                        SocketChannel clientChannel = serverChannel.accept();


                        // Register the new connection with the selector
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientChannel.socket().getOutputStream()));


                        // Sending the first message to the Client so that the Client understands the connection to the server
                        bufferedWriter.write("Enter a command: ");
                        logger.info("Enter a command: ");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();


                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);

                        // Getting information about accept
                        // System.out.println("Accepted new connection from client at " + clientChannel.getRemoteAddress());
                        // logger.info("Accepted new connection from client at " + clientChannel.getRemoteAddress());

                    } else if (key.isReadable()) {
                        // Read from the client channel
                        SocketChannel clientChannel = (SocketChannel) key.channel();


                        // Create ByteBuffer to get request from client
                        ByteBuffer byteBufferRequest = ByteBuffer.allocate(BUF_SIZE);


                        // Create ResponseArrayList which contains response Strings to send back to the Client
                        ResponseArrayList responseArrayList = new ResponseArrayList();
                        byteBufferRequest.clear();


                        // Check the client connection
                        int bytesRead;
                        try {
                            bytesRead = clientChannel.read(byteBufferRequest);
                        } catch (IOException e) {
                            bytesRead = -1;
                        }
                        // Check for end-of-stream
                        if (bytesRead == -1) {
                            // System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
                            // logger.info("Client disconnected: " + clientChannel.getRemoteAddress());
                            clientChannel.close();
                            key.cancel();
                            continue;
                        }


                        // Convert bytes to String
                        byteBufferRequest.flip(); //чтение
                        Charset charset = Charset.forName("UTF-8");


                        // Choose input channel
                        if(consoleBufferedReader.ready()) {
                            commandWrapperClass.setCommand(consoleBufferedReader.readLine());
                            // Set the mode
                            commandWrapperClass.setRequestInputMode(RequestInputMode.SERVER);
                        }
                        else {
                            // Decoding request from byteBufferRequest
                            commandWrapperClass.setCommand(String.valueOf(charset.decode(byteBufferRequest))); //декодируем запрос от клиента
                            // Set the mode
                            commandWrapperClass.setRequestInputMode(RequestInputMode.CLIENT);
                        }


                        // Set the command to the CommandWrapperClass
                        commandWrapperClass.setCommand(commandWrapperClass.getCommand().replaceAll("[\r\n]", ""));


                        // Getting the mode of the
                        // <
                        if(commandWrapperClass.getRequestInputMode().equals(RequestInputMode.CLIENT)) {
                            System.out.println("Request from " + clientChannel.getRemoteAddress() + ": " + commandWrapperClass.getCommand());
                            logger.info("Request from " + clientChannel.getRemoteAddress() + ": " + commandWrapperClass.getCommand());
                        }
                        if(commandWrapperClass.getRequestInputMode().equals(RequestInputMode.SERVER)) {
                            System.out.println("Server console: " + commandWrapperClass.getCommand());
                            logger.info("Server console: " + commandWrapperClass.getCommand());
                        }
                        // >


                        // The command executing
                        // <
                        if (commandBelonging.isCommonSideCommand(serverCommandsManager.getCommand(commandWrapperClass.getCommand()))) {
                            // If command is common
                            commonCommandsManager.executeCommand(commandWrapperClass.getCommand());
                        }
                        if (commandBelonging.isServerSideCommand(serverCommandsManager.getCommand(commandWrapperClass.getCommand()))){
                            // If command is server
                            serverCommandsManager.executeCommand(commandWrapperClass.getCommand());
                        }
                        if(commandBelonging.isSecuredServerSideCommands(serverCommandsManager.getCommand(commandWrapperClass.getCommand()))
                                                                & commandWrapperClass.getRequestInputMode().equals(RequestInputMode.SERVER)) {
                            // If command is server and secured
                            securedServerCommandsManager.executeCommand(commandWrapperClass.getCommand());
                        }
                        byteBufferRequest.clear();
                        // >


                        // Adding message about waiting a new request from the Client
                        responseArrayList.addElementToTheResponseArrayList("\n" + "Enter a command: ");
                        logger.info("Enter a command: ");


                        // Output message back to the Client
                        // <
                        ArrayList<String> response = responseArrayList.getResponseArrayList();
                        // Create ByteBuffer to send response to the Client
                        ByteBuffer byteBufferResponse = ByteBuffer.allocate(response.size() * 256);
                        for (String value : response) {
                            Thread.sleep(5);
                            byteBufferResponse.put((value).getBytes());
                        }
                        responseArrayList.clearResponseArrayList();
                        // Writing
                        byteBufferResponse.flip();
                        while (byteBufferResponse.hasRemaining()) {
                            clientChannel.write(byteBufferResponse);
                            System.out.println("-");
                        }
                        // >


                        // Clearing the ByteBufferResponse
                        byteBufferResponse.clear();

                        // Registration status of the SelectionKey
                        SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
                    }

                    // Remove the key from the selected set, since it's been handled
                    keyIter.remove();

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
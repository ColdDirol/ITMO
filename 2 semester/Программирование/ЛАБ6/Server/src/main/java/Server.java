import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

import collection.CollectionManager;
import commands.CommandsManager;
import commands.CommonCommandsManager;
import commands.ServerSideCommandsSet;
import org.xml.sax.SAXException;
import serverlogic.ResponseArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Server {
    private static int BUF_SIZE = 256;
    private static int TIMEOUT = 3000; // Wait timeout (milliseconds)

    public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, TransformerException, SAXException {
        BufferedReader consoleBufferedReader = new BufferedReader(new InputStreamReader(System.in));

        //Projects objects
        CommandsManager commandsManager = new CommandsManager();
        ServerSideCommandsSet serverSideCommandsSet = new ServerSideCommandsSet();
        CommonCommandsManager commonCommandsManager = new CommonCommandsManager();

        //Read collection from xml and save to the Collection class
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.collectionReader();

        // Create a new selector
        Selector selector = Selector.open();

        // Create a new non-blocking server socket channel
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        // Bind the server socket to localhost and port 5555
        InetSocketAddress isa = new InetSocketAddress("localhost", 5555);
        serverChannel.socket().bind(isa);

        // Register the server socket channel with the selector
        SelectionKey serverKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server started on port 5555");

        while (true) {
            // Wait for a connection
            if (selector.select(TIMEOUT) == 0) {
                continue;
            }

            String command;

            // Get all keys with ready events
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                try {
                    SelectionKey key = keyIter.next();

                    if (key.isAcceptable()) {
                        // Accept a new connection
                        SocketChannel clientChannel = serverChannel.accept();
//                    clientChannel.configureBlocking(false);

                        // Register the new connection with the selector\

                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientChannel.socket().getOutputStream()));
                        bufferedWriter.write("Enter a command: ");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);

                        System.out.println("Accepted new connection from client at " + clientChannel.getRemoteAddress());
                    } else if (key.isReadable()) {
                        // Read from the client channel
                        SocketChannel clientChannel = (SocketChannel) key.channel();

                        //ByteBuffer to get request from client
                        ByteBuffer byteBufferRequest = ByteBuffer.allocate(BUF_SIZE);

                        ResponseArrayList responseArrayList = new ResponseArrayList();
                        byteBufferRequest.clear();

                        int bytesRead;

                        // Проверка на отключенного клиента
                        // <
                        try {
                            bytesRead = clientChannel.read(byteBufferRequest);
                        } catch (IOException e) {
                            bytesRead = -1;
                        }
                        // Check for end-of-stream
                        if (bytesRead == -1) {
                            System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
                            clientChannel.close();
                            key.cancel();
                            continue;
                        }
                        //>

                        //System.out.println(new String(byteBuffer.array())); //check

                        // Convert bytes to String
                        byteBufferRequest.flip(); //чтение
                        Charset charset = Charset.forName("UTF-8");

                        //Choose input channel
                        if(consoleBufferedReader.ready()) command = consoleBufferedReader.readLine();
                        else command = String.valueOf(charset.decode(byteBufferRequest)); //декодируем запрос от клиента

                        command = command.replaceAll("[\r\n]", "");

                        System.out.println("Received message from " + clientChannel.getRemoteAddress() + ": " + command);

                        //выполнение команды
                        //  <
                        if (serverSideCommandsSet.commonSideCommandsContains(commandsManager.getCommand(command))) {
                            commonCommandsManager.executeCommand(command);
                        } else {
                            commandsManager.executeCommand(command);
                        }
                        byteBufferRequest.clear();
                        //  >

                        // добавление сообщения о новом запросе ввести команду
                        //if(responseArrayList.size().equals(0)) responseArrayList.addElementToTheResponseArrayList("Something went wrong.");
                        responseArrayList.addElementToTheResponseArrayList("\n" + "Enter a command: ");

                        // Вывод информации обратно клиенту
                        //<
                        ArrayList<String> response = responseArrayList.getResponseArrayList();
                        //ByteBuffer to send request to client
                        ByteBuffer byteBufferResponse = ByteBuffer.allocate(response.size() * 256);
                        for (String value : response) {
                            Thread.sleep(5);
                            byteBufferResponse.put((value).getBytes());
                        }

                        responseArrayList.clearResponseArrayList();

                        byteBufferResponse.flip(); //запись

                        while (byteBufferResponse.hasRemaining()) {
                            clientChannel.write(byteBufferResponse);
                            System.out.println("-");
                        }

                        System.out.println(new String(byteBufferResponse.array())); //check
                        byteBufferResponse.clear();

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
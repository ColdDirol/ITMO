import clientlogic.TCPClient;
import clientlogic.inputlogic.ResponseStringChannel;

import java.io.IOException;

public class Client {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    ResponseStringChannel responseStringChannel = new ResponseStringChannel();

    public static void main(String[] args) {
        try {
            TCPClient TCPClient = new TCPClient(SERVER_HOST, SERVER_PORT);
        } catch (IOException e) {
            System.out.println("ERROR:");
            System.out.println("Connection with Server " + SERVER_HOST + ":" + SERVER_PORT + " has been LOST!");
            System.out.println("Perhaps such a Server DOES NOT EXIST.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
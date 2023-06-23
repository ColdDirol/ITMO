package commands.clientcommands;

import java.io.IOException;
import java.net.Socket;

/**
 * The class Exit is used to execute the *exit* command
 */
public class Exit {
    public static void exit(Socket socket) throws IOException {
        socket.close();
        System.out.println("All the best...");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "exit";
    }
}

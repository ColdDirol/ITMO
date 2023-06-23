package commands.clientcommands;


import java.io.IOException;
import java.net.Socket;

public class LogOut {
    public void logOut(Socket socket) throws IOException {
        socket.close();
        System.out.println("Logging out...");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "logout";
    }
}

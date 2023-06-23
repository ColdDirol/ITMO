package commands.servercommands;

import serverlogic.ClientCuncurrentHashMap;

public class Exit {
    ClientCuncurrentHashMap clientCuncurrentHashMap = new ClientCuncurrentHashMap();

    public void exit() {
        clientCuncurrentHashMap.closeAllSockets();
        System.out.println("Server has been stopped!");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "exit";
    }
}

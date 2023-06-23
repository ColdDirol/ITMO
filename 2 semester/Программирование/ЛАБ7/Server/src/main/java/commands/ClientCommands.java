package commands;

import java.util.HashSet;

public class ClientCommands {
    private static HashSet<String> clientCommandsHashSet = new HashSet<>();
    {
        clientCommandsHashSet.add("exit");
        clientCommandsHashSet.add("execute_script");
    }

    public HashSet<String> getClientCommandsHashSet() {
        return clientCommandsHashSet;
    }
}

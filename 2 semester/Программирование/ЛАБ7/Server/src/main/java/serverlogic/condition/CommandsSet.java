package serverlogic.condition;

import serverlogic.ServerLogicInterface;

import java.util.HashSet;
import java.util.Set;

public class CommandsSet implements ServerLogicInterface {
    private Set<String> commonSideCommandsSet = new HashSet<>();
    {
        commonSideCommandsSet.add("insert");
        commonSideCommandsSet.add("update");
        commonSideCommandsSet.add("replace_if_greater");
        commonSideCommandsSet.add("replace_if_lowe");
    }
    public Set<String> getCommonSideCommandsSet() {
        return commonSideCommandsSet;
    }

    private Set<String> serverSideCommandsSet = new HashSet<>();
    {
        serverSideCommandsSet.add("echo");
        serverSideCommandsSet.add("help");
        serverSideCommandsSet.add("info");
        serverSideCommandsSet.add("show");
        serverSideCommandsSet.add("remove_key");
        serverSideCommandsSet.add("clear");
        serverSideCommandsSet.add("sum_of_number_of_rooms");
        serverSideCommandsSet.add("max_by_furnish");
        serverSideCommandsSet.add("max_by_transport");
        serverSideCommandsSet.add("max_by_view");
        serverSideCommandsSet.add("count_by_number_of_rooms");
    }
    public Set<String> getServerSideCommandsSet() {
        return serverSideCommandsSet;
    }

    private Set<String> securedServerSideCommands = new HashSet<>();
    {
        securedServerSideCommands.add("show_history");
        securedServerSideCommands.add("twist_history");
        securedServerSideCommands.add("save");
        securedServerSideCommands.add("save_exit");
    }
    public Set<String> getSecuredServerSideCommands() {
        return securedServerSideCommands;
    }

    @Override
    public String description() {
        return "The CommandsSet is used to contain sorted by type commands.";
    }
}

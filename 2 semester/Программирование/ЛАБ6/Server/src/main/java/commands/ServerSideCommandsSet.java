package commands;

import java.util.HashSet;
import java.util.Set;

public class ServerSideCommandsSet {
    private Set<String> commonSideCommandsSet = new HashSet<>();
    {
        commonSideCommandsSet.add("insert");
        commonSideCommandsSet.add("update");
        commonSideCommandsSet.add("replace_if_greater");
        commonSideCommandsSet.add("replace_if_lowe");
    }

    public boolean commonSideCommandsContains(String command) {
        if(commonSideCommandsSet.contains(command)) return true;
        else return false;
    }

    private Set<String> serverSideCommandsSet = new HashSet<>();
    {
        serverSideCommandsSet.add("help");
        serverSideCommandsSet.add("info");
        serverSideCommandsSet.add("show");
        serverSideCommandsSet.add("remove_key");
        serverSideCommandsSet.add("clear");
        serverSideCommandsSet.add("save");
        serverSideCommandsSet.add("sum_of_number_of_rooms");
        serverSideCommandsSet.add("max_by_view");
        serverSideCommandsSet.add("count_by_number_of_rooms");
    }

    public boolean serverSideCommandsContains(String command) {
        if(serverSideCommandsSet.contains(command)) return true;
        else return false;
    }
}

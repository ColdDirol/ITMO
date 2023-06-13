package commands;

import java.util.*;

public class ClientSideCommandsSet {
    private Map<String, String> commandsMap = new HashMap();
    {
        commandsMap.put("help", null);
        commandsMap.put("info", null);
        commandsMap.put("show", null);
        commandsMap.put("insert", "{element}");
        commandsMap.put("update", "id {element}");
        commandsMap.put("remove_key", "id");
        commandsMap.put("clear", null);
        commandsMap.put("save", null);
        commandsMap.put("execute_script", "file_name");
        commandsMap.put("exit", null);
        commandsMap.put("remove_greater", "id");
        commandsMap.put("replace_if_greater", "id {element}");
        commandsMap.put("replace_if_lowe", "id {element}");
        commandsMap.put("sum_of_number_of_rooms", null);
        commandsMap.put("max_by_view", null);
        commandsMap.put("count_by_number_of_rooms", "numberOfRooms");
    }

    public Map<String, String> getCommandsMap() {
        return commandsMap;
    }

    public Set<String> getCommandsMapKeySet() {
        return commandsMap.keySet();
    }

    public Set<Map.Entry<String, String>> getCommandsMapEntrySet() {
        return commandsMap.entrySet();
    }



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


    private Set<String> clientSideCommandsSet = new HashSet<>();
    {
        clientSideCommandsSet.add("exit");
        clientSideCommandsSet.add("execute_script");
        clientSideCommandsSet.add("ColdDirol");
    }
    public boolean clientSideCommandsContains(String command) {
        if(clientSideCommandsSet.contains(command)) return true;
        else return false;
    }

    private HashSet<String> setWithSocketCommandsKeys = new HashSet<>();
    {
        setWithSocketCommandsKeys.add("execute_script");
    }

    public boolean isSocketCommand(String command) {
        if(setWithSocketCommandsKeys.contains(command)) return true;
        else return false;
    }



    //for ExecuteScript class <
    private HashSet<String> setWithScannerCommandsKeys = new HashSet<String>();
    {
        setWithScannerCommandsKeys.add("insert");
        setWithScannerCommandsKeys.add("update");
        setWithScannerCommandsKeys.add("replace_if_greater");
        setWithScannerCommandsKeys.add("replace_if_lowe");
    }
    public HashSet<String> getSetWithScannerCommandsKeys() {
        return setWithScannerCommandsKeys;
    }

    private static HashSet<String> recursionSet = new HashSet<>();
    public static boolean isRecursionSetElement(String element) {
        if(recursionSet.contains(element)) return true;
        else return false;
    }
    public static void addRecursionSetElement(String element) {
        recursionSet.add(element);
    }
    public static void removeRecursionSetElement(String element) {
        recursionSet.remove(element);
    }
    public static void clearRecursionSet() {
        recursionSet.clear();
        //System.out.println(recursionSet);
    }
    public void printRecursionSet() {
        System.out.println(recursionSet);
    }


    //test
    private static ArrayDeque<String> recursionDeque = new ArrayDeque<String>();
    public static boolean isRecursionDequeElement(String element) {
        if(recursionDeque.contains(element)) return true;
        else return false;
    }
    public static void addLastRecursionDequeElement(String element) {
        recursionDeque.addLast(element);
    }
    public static void removeLastRecursionDequeElement() {
        recursionDeque.removeLast();
    }
    public static void clearRecursionDeque() {
        recursionDeque.clear();
    }
    public static void printRecursionDeque() {
        System.out.println(recursionDeque);
    }
    // >
}

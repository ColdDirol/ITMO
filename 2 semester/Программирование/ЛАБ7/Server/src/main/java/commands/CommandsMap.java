package commands;

import java.util.*;

/**
 * The class CommandsList is used to help execute the *help* command
 */
public class CommandsMap {
    private Map<String, String> commandsMapInfo = new HashMap<String, String>();
    {
        commandsMapInfo.put("help", "display help for available commands");
        commandsMapInfo.put("info", "output information about the collection (type, initialization date, number of items, etc.) to the standard output stream");
        commandsMapInfo.put("show", "output to the standard output stream all elements of the collection in string representation");
        commandsMapInfo.put("insert null {element}", "add a new element with the specified key");
        commandsMapInfo.put("update id {element}", "refresh the value of the collection item whose id is equal to the given one");
        commandsMapInfo.put("remove_key null", "remove an item from the collection by its key");
        commandsMapInfo.put("clear", "clear the collection");
        commandsMapInfo.put("save", "save the collection to the file");
        commandsMapInfo.put("execute_script file_name", "read and execute the script from the specified file. The script contains commands in the same form as they are entered by the user in the interactive mode");
        commandsMapInfo.put("exit", "terminate the program (without saving to the file)");
        commandsMapInfo.put("remove_greater {element}", "remove from the collection all items that exceed the specified");
        commandsMapInfo.put("replace_if_greater null {element}", "replace the value by the key, if the new value is greater than the old value");
        commandsMapInfo.put("replace_if_lowe null {element}", "replace the value by the key, if the new value is less than the old value");
        commandsMapInfo.put("sum_of_number_of_rooms", "display the sum of the numberOfRooms field values for all items in the collection");
        commandsMapInfo.put("max_by_view", "output any object from the collection whose View number value is maximal in the current collection");
        commandsMapInfo.put("count_by_number_of_rooms numberOfRooms", "output the number of elements, the value of the *numberOfRooms* field of which is equal to the given one");
    }
    public Map getCommandsMapInfo(){
        return commandsMapInfo;
    }

    private HashSet<String> setWithSocketCommandsKeys = new HashSet<String>();
    {
        setWithSocketCommandsKeys.add("insert");
        setWithSocketCommandsKeys.add("update");
        setWithSocketCommandsKeys.add("replace_if_greater");
        setWithSocketCommandsKeys.add("replace_if_lowe");
    }
    public HashSet<String> getSetWithSocketCommandsKeys() {
        return setWithSocketCommandsKeys;
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

    public Set<Map.Entry<String, String>> getCommandsMapEntrySet() {
        return commandsMapInfo.entrySet();
    }
}

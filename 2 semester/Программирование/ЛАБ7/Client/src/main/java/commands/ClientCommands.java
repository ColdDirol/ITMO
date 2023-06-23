package commands;

import java.util.HashSet;

public class ClientCommands {
    private static HashSet<String> clientCommandsHashSet = new HashSet<>();
    {
        clientCommandsHashSet.add("exit");
        clientCommandsHashSet.add("logout");
        clientCommandsHashSet.add("execute_script");
    }

    public HashSet<String> getClientCommandsHashSet() {
        return clientCommandsHashSet;
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
}

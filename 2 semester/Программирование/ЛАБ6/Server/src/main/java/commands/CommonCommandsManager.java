package commands;
import commands.commands.Insert;
import commands.commands.ReplaceIfGreater;
import commands.commands.ReplaceIfLowe;
import commands.commands.Update;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommonCommandsManager extends CommandsManager{
    Insert insert = new Insert();
    Update update = new Update();
    ReplaceIfGreater replaceIfGreater = new ReplaceIfGreater();
    ReplaceIfLowe replaceIfLowe = new ReplaceIfLowe();

    private void commonCommandsManager(String command, String attribute) {
        Map<String, CommandType> commonCommandsMap = new LinkedHashMap<>();
        {
            commonCommandsMap.put("insert", () -> insert.insert(attribute));
        }
        commonCommandsMap.get(command).execute();
    }
    private void commonCommandsManager(String command, String key, String attribute) {
        Map<String, CommandType> commonCommandsMap = new LinkedHashMap<>();
        {
            commonCommandsMap.put("update", () -> update.update(Integer.valueOf(key), attribute));
            commonCommandsMap.put("replace_if_greater", () -> replaceIfGreater.replaceIfGreater(Integer.valueOf(key), attribute));
            commonCommandsMap.put("replace_if_lowe", () -> replaceIfLowe.replaceIfLowe(Integer.parseInt(key), attribute));
        }
        commonCommandsMap.get(command).execute();
    }

    public void executeCommand(String fullcommand) {
        String command = getCommand(fullcommand);
        String key = key(fullcommand);
        String attribute = attribute(fullcommand);

        System.out.println("C:" + command + " K:" + key + " A:" + attribute);

        if(key == null || key.trim().isEmpty()) commonCommandsManager(command, attribute);
        else commonCommandsManager(command, key, attribute);
    }

    public String key(String fullcommand) {
        String key = "";
        String tmp = fullcommand;

        String[] words = fullcommand.split(" ");
        key = words[1];

        if(key.contains("[") | key.contains("]")) return null;
        return key;
    }

    private String attribute(String fullcommand) {
        int cnt = 0;
        String attribute = "";

        for (int i = 0; i < fullcommand.length(); i++) {
            if (fullcommand.charAt(i) == '[') cnt = i - 1;
            if (cnt > 0 & i > cnt) attribute += fullcommand.charAt(i);
        }
        return attribute;
    }
}

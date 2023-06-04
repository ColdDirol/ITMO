package commands.commandsmanager;
import commands.CommandType;
import commands.commands.common.Insert;
import commands.commands.common.ReplaceIfGreater;
import commands.commands.common.ReplaceIfLowe;
import commands.commands.common.Update;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommonCommandsManager extends CommandsManagerAbstract {
    Insert insert = new Insert();
    Update update = new Update();
    ReplaceIfGreater replaceIfGreater = new ReplaceIfGreater();
    ReplaceIfLowe replaceIfLowe = new ReplaceIfLowe();

    private void commonCommandsManager(String command, String attribute) {
        Map<String, CommandType> commonCommandsMap = new LinkedHashMap<>();
        {
            commonCommandsMap.put(insert.getCommandName(), () -> insert.insert(attribute));
        }
        commonCommandsMap.get(command).execute();
    }
    private void commonCommandsManager(String command, String key, String attribute) {
        Map<String, CommandType> commonCommandsMap = new LinkedHashMap<>();
        {
            commonCommandsMap.put(update.getCommandName(), () -> update.update(Integer.valueOf(key), attribute));
            commonCommandsMap.put(replaceIfGreater.getCommandName(), () -> replaceIfGreater.replaceIfGreater(Integer.valueOf(key), attribute));
            commonCommandsMap.put(replaceIfLowe.getCommandName(), () -> replaceIfLowe.replaceIfLowe(Integer.valueOf(key), attribute));
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

    @Override
    public String description() {
        return "The CommonCommandsManager is used to manage only common commands.";
    }
}

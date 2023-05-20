package commands.commands;

import commands.CommandsList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


/**
 * The class Help is used to execute the *help* command
 */
public class Help {
    CommandsList commands = new CommandsList();

    // получить набор всех ключей
    Set<String> keysSet = commands.getCommandsMapInfo().keySet();

    // получить набор всех значений
    Collection<String> valuesSet = commands.getCommandsMapInfo().values();

    public void help(){
        // перевести набор ключей в список
        ArrayList<String> keys = new ArrayList<String>();
        for (String key : keysSet) keys.add(key);

        // перевести набор значений в список
        ArrayList<String> values = new ArrayList<String>();
        for (String value : valuesSet) values.add(value);

        // вывод
        for(int i = 0; i < keys.size(); i++){
            System.out.println(keys.get(i) + " : " + values.get(i));
        }
    }

    @Override
    public String toString() {
        return "help";
    }
}

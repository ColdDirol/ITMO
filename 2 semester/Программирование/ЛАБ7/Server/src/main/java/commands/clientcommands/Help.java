package commands.clientcommands;


import commands.CommandsMap;
import serverlogic.outputchannel.ResponseArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


/**
 * The class Help is used to execute the *help* command
 */
public class Help {
    CommandsMap commandsMap = new CommandsMap();

    // получить набор всех ключей
    Set<String> keysSet = commandsMap.getCommandsMapInfo().keySet();

    // получить набор всех значений
    Collection<String> valuesSet = commandsMap.getCommandsMapInfo().values();
    ResponseArrayList responseArrayList = new ResponseArrayList();

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

            responseArrayList.addStringToResponseArrayList(keys.get(i) + " : " + values.get(i));
        }
    }

    @Override
    public String toString() {
        return "help";
    }
}

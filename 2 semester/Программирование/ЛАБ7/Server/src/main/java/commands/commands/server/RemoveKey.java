package commands.commands.server;

import collection.ServerCollection;
import collection.flat.Flat;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

import java.util.Hashtable;

public class RemoveKey implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();

    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(RemoveKey.class.getSimpleName());
    public void removeKey(Integer key) {
        try {
            serverCollection.remove(key);
            responseArrayList.addElementToTheResponseArrayList("The flat called " + key + " has been deleted");
            logger.info("The flat called " + key + " has been deleted");
        } catch (NumberFormatException e) {
            System.out.print("Пожалуйста, введите данную команду с числом!");
            responseArrayList.addElementToTheResponseArrayList("Пожалуйста, введите данную команду с числом!");
            logger.info("Пожалуйста, введите данную команду с числом!");
        }
    }

    @Override
    public String getCommandName() {
        return "remove_key";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to remove element of the collection which ID equals to command key (if ID == key -> remove).";
    }
}

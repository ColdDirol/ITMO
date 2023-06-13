package commands.commands.server;

import collection.ServerCollection;
import collection.ElementConstructor;
import collection.flat.Flat;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

import java.util.*;

public class RemoveGreater implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();
    Scanner scanner = new Scanner(System.in);
    ElementConstructor elementConstructor = new ElementConstructor();
    Set<Integer> idSet = hashtable.keySet();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    private Logger logger = LogManager.getLogger(RemoveGreater.class.getSimpleName());

    public void removeGreater(Integer value) {
        try {
            Integer id = value;
            for(Iterator<Map.Entry<Integer, Flat>> item = hashtable.entrySet().iterator(); item.hasNext();) {
                Integer idForRemove = null;
                if((idForRemove = item.next().getKey()) > id) {
                    item.remove();
                    responseArrayList.addElementToTheResponseArrayList("Flat with id " + idForRemove + " has been removed!");
                    logger.info("Flat with id " + idForRemove + " has been removed!");
                }
            }
        } catch (NumberFormatException e) {
            System.out.print("Enter *id* correctly");
            responseArrayList.addElementToTheResponseArrayList("Enter *id* correctly");
            logger.info("Enter *id* correctly");
        }
    }

    @Override
    public String getCommandName() {
        return "remove_greater";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to remove elements of the collection which ID less then command key (if ID < key -> remove).";
    }
}

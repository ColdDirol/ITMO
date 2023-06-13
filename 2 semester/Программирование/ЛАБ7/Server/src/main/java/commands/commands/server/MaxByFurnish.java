package commands.commands.server;

import collection.ServerCollection;
import collection.flat.Flat;
import collection.flat.Furnish;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

import java.util.ArrayList;
import java.util.Comparator;

public class MaxByFurnish implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    ArrayList<Flat> collectionArrayList;
    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(MaxByView.class.getSimpleName());

    public void maxByFurnish() {
        // Stream API <
        if (serverCollection.getHahstable().isEmpty()) {
            responseArrayList.addElementToTheResponseArrayList("Your collection is empty!");
            logger.info("Your collection is empty!");
        } else {
            collectionArrayList = new ArrayList<>(serverCollection.getHahstable().values());
            int maxId = collectionArrayList.stream().max(Comparator.comparingInt(Flat::getFurnishId)).orElse(new Flat()).getFurnishId();
            responseArrayList.addElementToTheResponseArrayList("Max element in the current collection is " + Furnish.getNameById(maxId));
            logger.info("Max element in the current collection is " + Furnish.getNameById(maxId));
        }
    }

    @Override
    public String getCommandName() {
        return "max_by_furnish";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to send to the client max level of Furnish field in the collection.";
    }
}

package commands.commands.server;

import collection.ServerCollection;
import collection.flat.Flat;
import collection.flat.Transport;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

import java.util.ArrayList;
import java.util.Comparator;

public class MaxByTransport implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    ArrayList<Flat> collectionArrayList;

    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(MaxByView.class.getSimpleName());

    public void maxByTransport() {
        // Stream API <
        if (serverCollection.getHahstable().isEmpty()) {
            responseArrayList.addElementToTheResponseArrayList("Your collection is empty!");
            logger.info("Your collection is empty!");
        } else {
            collectionArrayList = new ArrayList<>(serverCollection.getHahstable().values());
            int maxId = collectionArrayList.stream().max(Comparator.comparingInt(Flat::getTransportId)).orElse(new Flat()).getTransportId();
            responseArrayList.addElementToTheResponseArrayList("Max element in the current collection is " + Transport.getNameById(maxId));
            logger.info("Max element in the current collection is " + Transport.getNameById(maxId));
        }
    }

    @Override
    public String getCommandName() {
        return "max_by_transport";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to send to the client max level of Transport field in the collection.";
    }
}

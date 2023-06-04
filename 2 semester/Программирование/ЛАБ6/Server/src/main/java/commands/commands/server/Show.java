package commands.commands.server;

import collection.ServerCollection;
import collection.flat.Flat;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.collectionparser.HashtableToSortedArrayListParser;
import serverlogic.output.ResponseArrayList;

import java.util.ArrayList;
import java.util.Map;

public class Show implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    HashtableToSortedArrayListParser hashtableToSortedArrayListParser = new HashtableToSortedArrayListParser();
    ArrayList<Map.Entry<Integer, Flat>> entryArrayList;
    private Logger logger = LogManager.getLogger(Show.class.getSimpleName());
    public void show() throws InterruptedException {
        entryArrayList = hashtableToSortedArrayListParser.sortByFlatName(serverCollection.getHahstable());
        responseArrayList.addElementToTheResponseArrayList("-----------");
        logger.info("-----------");
        for(Map.Entry<Integer, Flat> item : entryArrayList){
            Thread.sleep(10);
            responseArrayList.addElementToTheResponseArrayList("The flat \"" + item.getValue().getName() + "\"");
            logger.info("The flat \"" + item.getKey() + "\"");
            responseArrayList.addElementToTheResponseArrayList("+");
            logger.info("+");
            responseArrayList.addElementToTheResponseArrayList("Flat Id: " + item.getValue().getId());
            logger.info("Flat Id: " + item.getValue().getId());
            responseArrayList.addElementToTheResponseArrayList("House: " + item.getValue().getHouseName());
            logger.info("House: " + item.getValue().getHouseName());
            responseArrayList.addElementToTheResponseArrayList("---");
            logger.info("---");
        }
        responseArrayList.addElementToTheResponseArrayList("-----------");
        logger.info("-----------");
    }

    @Override
    public String getCommandName() {
        return "show";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to show short information about the collection elements.";
    }
}

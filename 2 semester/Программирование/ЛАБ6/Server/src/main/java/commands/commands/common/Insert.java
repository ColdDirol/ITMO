package commands.commands.common;

import collection.ServerCollection;
import collection.ElementConstructor;
import collection.flat.Flat;
import commands.commands.CommandInterface;
import history.HistoryCollection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

public class Insert implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    HistoryCollection historyCollection = new HistoryCollection();
    ElementConstructor elementConstructor = new ElementConstructor();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(Insert.class.getSimpleName());
    public void insert(String elements) {
        try {
            // Flat construct
            Flat flat = elementConstructor.elementConstructor(elements);

            // Add flat object to the collection
            serverCollection.addObjectToHashtable(flat.getId(), flat);

            // Save changes to the history
            historyCollection.addHistoryElement(serverCollection.getHahstable());

            // Add message to the response
            responseArrayList.addElementToTheResponseArrayList("+ Object has been added!");

            // Add message to the logger
            logger.info("+ Object has been added!");
        } catch (NullPointerException e) {
            System.out.println("ERROR:");
            responseArrayList.addElementToTheResponseArrayList("ERROR:");
            logger.info("ERROR:");
            System.out.println("Flat element couldn't be added to the collection!");
            responseArrayList.addElementToTheResponseArrayList("Flat element couldn't be added to the collection!");
            logger.info("Flat element couldn't be added to the collection!");
            System.out.println("Try to repeat request!");
            responseArrayList.addElementToTheResponseArrayList("Try to repeat request!");
            logger.info("Try to repeat request!");
        }
    }

    @Override
    public String getCommandName() {
        return "insert";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to insert new element to the collection.";
    }
}

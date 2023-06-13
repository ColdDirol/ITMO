package commands.commands.common;

import collection.ServerCollection;
import collection.ElementConstructor;
import collection.flat.Flat;
import commands.commands.CommandInterface;
import history.HistoryCollection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

public class Update implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    HistoryCollection historyCollection = new HistoryCollection();
    ElementConstructor elementConstructor = new ElementConstructor();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    private Logger logger = LogManager.getLogger(Update.class.getSimpleName());

    public void update(Integer id, String elements) {
        // Flat construct
        Flat flat = elementConstructor.elementConstructor(id, elements);

        // Add flat object to the collection
        serverCollection.updateElementOfCollection(id, flat);

        // Save changes to the history
        historyCollection.addHistoryElement(serverCollection.getHahstable());

        // Add message to the response
        responseArrayList.addElementToTheResponseArrayList("*Flat object* has been updated!");

        // Add message to the logger
        logger.info("*Flat object* has been updated!");
    }

    @Override
    public String getCommandName() {
        return "update";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to update element with the key *id*.";
    }
}

package commands.commands.server;

import collection.ServerCollection;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

public class Clear implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(Clear.class.getSimpleName());

    public void clear(){
        serverCollection.clearHashtable();
        responseArrayList.addElementToTheResponseArrayList("Collection has been cleared!");
        logger.info("Collection has been cleared!");
    }

    @Override
    public String getCommandName() {
        return "clear";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to clear the collection for clear the collection.";
    }
}

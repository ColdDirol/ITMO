package commands.commands;

import collection.ServerCollection;
import serverlogic.ResponseArrayList;

public class Clear implements Command {
    ServerCollection serverCollection = new ServerCollection();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void clear(){
        serverCollection.clearHashtable();
        responseArrayList.addElementToTheResponseArrayList("Collection has been cleared!");
    }

    @Override
    public String toString() {
        return "clear";
    }

    @Override
    public String description() {
        return "The \"clear\" command is used to clear the collection for clear the collection.";
    }
}

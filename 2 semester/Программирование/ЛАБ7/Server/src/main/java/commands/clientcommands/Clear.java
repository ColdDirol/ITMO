package commands.clientcommands;

import collection.flatcollection.ServerCollection;
import database.actions.FlatActions;
import serverlogic.outputchannel.ResponseArrayList;

/**
 * The class Clear is used to execute the *clear* command
 */
public class Clear {
    ServerCollection serverCollection = new ServerCollection();
    FlatActions flatActions = new FlatActions();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    public void clear(Integer userId){
        serverCollection.getHahstable().entrySet().removeIf(entry -> entry.getValue().getUser_id() == userId);
        flatActions.removeFlatsFromDB(userId);
        responseArrayList.addStringToResponseArrayList("Collection has been cleared!");
    }

    @Override
    public String toString() {
        return "clear";
    }
}

package commands.clientcommands;

import collection.flatcollection.ServerCollection;
import database.actions.FlatActions;
import serverlogic.outputchannel.ResponseArrayList;

public class RemoveKey {
    ServerCollection serverCollection = new ServerCollection();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    FlatActions flatActions = new FlatActions();
    public void removeKey(String attribute, Integer userId) {
        try {
            if(flatActions.removeFlatFromDB(Integer.valueOf(attribute), userId)) {
                serverCollection.removeObjectFromHashtable(Integer.valueOf(attribute));
                responseArrayList.addStringToResponseArrayList("Flat with id " + attribute + " has been removed!");
            }
        } catch (NumberFormatException e) {
            responseArrayList.addStringToResponseArrayList("Пожалуйста, введите данную команду с числом!");
        }
    }

    @Override
    public String toString() {
        return "remove_key";
    }
}

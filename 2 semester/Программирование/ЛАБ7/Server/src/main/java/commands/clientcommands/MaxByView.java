package commands.clientcommands;

import collection.flatcollection.ServerCollection;
import collection.flatcollection.flat.Flat;
import serverlogic.outputchannel.ResponseArrayList;

import java.util.Map;

/**
 * The class MaxByView is used to execute the *max_by_view* command
 */
public class MaxByView {
    ServerCollection serverCollection = new ServerCollection();

    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void maxByView(Integer userId) {
        int maxId = 0;
        int flatId = 0;
        String name = null;

        for(Map.Entry<Integer, Flat> item : serverCollection.getHahstable().entrySet()){
            System.out.println(item.getKey());
            if(item.getValue().getView().getId() > maxId && item.getValue().getUser_id() == userId) {
                maxId = item.getValue().getView().getId();
                name = item.getValue().getView().getName();
                flatId = item.getKey();
            }
        }

        if(serverCollection.getHahstable().size() == 0) {
            responseArrayList.addStringToResponseArrayList("Yout collection is empty!");
        }
        else responseArrayList.addStringToResponseArrayList("Max element in the current collection is " + name + " in the flat " + flatId);
    }

    @Override
    public String toString() {
        return "max_by_view";
    }
}

package commands.commands;

import collection.ServerCollection;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

import java.util.Hashtable;
import java.util.Map;

public class MaxByView {
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();

    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void maxByView() {
        int maxId = 0;
        int flatId = 0;
        String name = null;

        for(Map.Entry<Integer, Flat> item : hashtable.entrySet()){
            System.out.println(item.getKey());
            if(item.getValue().getView().getId() > maxId) {
                maxId = item.getValue().getView().getId();
                name = item.getValue().getView().getTitle();
                flatId = item.getKey();
            }
        }

        if(hashtable.size() == 0) {
            responseArrayList.addElementToTheResponseArrayList("Yout collection is empty!");
        }
        else responseArrayList.addElementToTheResponseArrayList("Max element in the current collection is " + name + " in the flat " + flatId);
    }
}

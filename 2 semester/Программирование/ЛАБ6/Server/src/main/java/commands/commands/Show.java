package commands.commands;

import collection.ServerCollection;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

import java.util.Map;

public class Show {
    ServerCollection serverCollection = new ServerCollection();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    public void show() throws InterruptedException {
        responseArrayList.addElementToTheResponseArrayList("-----------");
        for(Map.Entry<Integer, Flat> item : serverCollection.getMapEntriesSet()){
            Thread.sleep(10);
            responseArrayList.addElementToTheResponseArrayList("The flat \"" + item.getKey() + "\"");
            responseArrayList.addElementToTheResponseArrayList("+");
            responseArrayList.addElementToTheResponseArrayList("Flat: " + item.getValue().getName());
            responseArrayList.addElementToTheResponseArrayList("House: " + item.getValue().getHouseName());
            responseArrayList.addElementToTheResponseArrayList("---");
        }
        responseArrayList.addElementToTheResponseArrayList("-----------");
    }

    @Override
    public String toString() {
        return "show";
    }
}

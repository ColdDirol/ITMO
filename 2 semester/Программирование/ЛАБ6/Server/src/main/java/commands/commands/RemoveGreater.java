package commands.commands;

import collection.ServerCollection;
import collection.ElementConstructor;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

import java.util.*;

public class RemoveGreater {
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();
    Scanner scanner = new Scanner(System.in);
    ElementConstructor elementConstructor = new ElementConstructor();
    Set<Integer> idSet = hashtable.keySet();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void removeGreater(String value) {
        try {
            Integer id = Integer.parseInt(value);
            for(Iterator<Map.Entry<Integer, Flat>> item = hashtable.entrySet().iterator(); item.hasNext();) {
                int idForRemove = 0;
                if((idForRemove = item.next().getKey()) > id) {
                    item.remove();
                    responseArrayList.addElementToTheResponseArrayList("Flat with id " + idForRemove + " has been removed!");
                }
            }
        } catch (NumberFormatException e) {
            System.out.print("Enter *id* correctly");
            responseArrayList.addElementToTheResponseArrayList("Enter *id* correctly");
        }
    }
}

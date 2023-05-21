package commands.commands;

import collection.ServerCollection;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

import java.util.Hashtable;

public class RemoveKey {
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();

    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void removeKey(String key) {
        try {
            serverCollection.remove(Integer.parseInt(key));
            responseArrayList.addElementToTheResponseArrayList("The flat called " + key + " has been deleted");
        } catch (NumberFormatException e) {
            System.out.print("Пожалуйста, введите данную команду с числом!");
            responseArrayList.addElementToTheResponseArrayList("Пожалуйста, введите данную команду с числом!");
        }
    }

    @Override
    public String toString() {
        return "remove_key";
    }
}

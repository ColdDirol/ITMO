package commands.commands;

import collection.ServerCollection;
import collection.ElementConstructor;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

public class Insert {
    ServerCollection serverCollection = new ServerCollection();
    ElementConstructor elementConstructor = new ElementConstructor();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void insert(String elements) {
        Flat flat = elementConstructor.elementConstructor(elements);
        serverCollection.addObjectToHashtable(flat.getId(), flat);
        responseArrayList.addElementToTheResponseArrayList("+ Object has been added!");
    }
}

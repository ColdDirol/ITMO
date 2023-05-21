package commands.commands;

import collection.ServerCollection;
import collection.ElementConstructor;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

public class Update implements Command {
    ServerCollection serverCollection = new ServerCollection();
    ElementConstructor elementConstructor = new ElementConstructor();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void update(Integer id, String elements) {
        Flat flat = elementConstructor.elementConstructor(id, elements);
        serverCollection.updateElementOfCollection(id, flat);
        responseArrayList.addElementToTheResponseArrayList("*Flat object* has been updated!");
    }

    @Override
    public String toString() {
        return "update";
    }

    @Override
    public String description() {
        return "";
    }
}

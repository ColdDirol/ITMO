package commands.commands;

import collection.ServerCollection;
import collection.ElementConstructor;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

import java.util.Hashtable;
import java.util.Set;

public class ReplaceIfGreater implements Command {
    ServerCollection serverCollection = new ServerCollection();
    ElementConstructor elementConstructor = new ElementConstructor();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();
    Flat oldFlat;
    Flat newFlat;
    ResponseArrayList responseArrayList = new ResponseArrayList();
    Set<Integer> idSet = hashtable.keySet();

    public void replaceIfGreater(Integer key, String elements) {
        try {
            System.out.println("_" + key + "_");
            Integer id = key;
            if(idSet.contains(id)) {
                oldFlat = hashtable.get(id);
                newFlat = elementConstructor.elementConstructor(key, elements);
                if(newFlat.objectSize() > oldFlat.objectSize()) {
                    hashtable.replace(id, newFlat);
                    System.out.println("*Flat object* has been replaced!");
                    responseArrayList.addElementToTheResponseArrayList("*Flat object* has been replaced!");
                } else {
                    System.out.println("New *flat object* is lower than old.");
                    responseArrayList.addElementToTheResponseArrayList("New *flat object* is lower than old.");
                    System.out.println("The operation was not completed.");
                    responseArrayList.addElementToTheResponseArrayList("The operation was not completed.");
                }
            } else {
                throw new NullPointerException("Format error");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please, use number as key!");
            responseArrayList.addElementToTheResponseArrayList("Please, use number as key!");
        } catch (NullPointerException e) {
            e.getMessage();
            System.out.println("____");
            e.printStackTrace();
            System.out.println("____");
            responseArrayList.addElementToTheResponseArrayList("Enter the *id* key of element which exist!");
        }
    }

    @Override
    public String toString() {
        return "replace_if_greater";
    }

    @Override
    public String description() {
        return "The \"replace_if_greater\" command is used to replace the collection object if new object is greater.";
    }
}

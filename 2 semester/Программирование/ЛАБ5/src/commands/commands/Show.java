package commands.commands;

import collection.Collection;
import collection.flat.Flat;

import java.util.Hashtable;
import java.util.Map;

/**
 * The class Show is used to execute the *show* command
 */
public class Show {
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();

    public void show(){
        System.out.println("-----------");
        for(Map.Entry<Integer, Flat> item : hashtable.entrySet()){
            System.out.println("The room \"" + item.getKey() + "\"");
            System.out.println("+");
            System.out.println(item.getValue().toString());
            System.out.println("---");
        }
        System.out.println("-----------");
    }

    @Override
    public String toString() {
        return "show";
    }
}

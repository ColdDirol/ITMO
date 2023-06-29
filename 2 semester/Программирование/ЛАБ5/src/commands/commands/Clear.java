package commands.commands;

import collection.Collection;
import collection.flat.Flat;

import java.util.Hashtable;

/**
 * The class Clear is used to execute the *clear* command
 */
public class Clear {
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();

    public void clear(){
        hashtable.clear();
        System.out.println("Collection cleared");
    }

    @Override
    public String toString() {
        return "clear";
    }
}

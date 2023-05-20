package commands.commands;

import collection.Collection;
import collection.CollectionManager;
import collection.flat.Flat;

import java.time.LocalDateTime;
import java.util.Hashtable;

/**
 * The class Info is used to execute the *info* command
 */
public class Info {
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();
    LocalDateTime initializationDate = collection.getInitializationDate();
    public void info(){
        System.out.println("Information about the collection:");
        System.out.println("Collection type: " + hashtable.getClass().getSimpleName() + ";");
        System.out.println("Initialization date: " + initializationDate.getHour() + ":" + initializationDate.getMinute() + " " + initializationDate.getDayOfMonth() + " " + initializationDate.getMonth() + " " + initializationDate.getYear());
        System.out.println("Number of elements: " + hashtable.size());
    }

    @Override
    public String toString() {
        return "info";
    }
}

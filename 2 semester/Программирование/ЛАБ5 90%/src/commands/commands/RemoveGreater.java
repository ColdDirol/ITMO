package commands.commands;

import collection.Collection;

import collection.ElementConstructor;
import collection.flat.Flat;

import java.util.*;

/**
 * The class RemoveGreater is used to execute the *remove_greater name* command
 */
public class RemoveGreater {
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();
    Scanner scanner = new Scanner(System.in);
    ElementConstructor elementConstructor = new ElementConstructor();
    Set<Integer> idSet = hashtable.keySet();

    public void removeGreater(String value) {
        while(true) {
            try {
                Integer id = Integer.parseInt(value);
                for(Iterator<Map.Entry<Integer, Flat>> item = hashtable.entrySet().iterator();item.hasNext();) {
                    if(item.next().getKey() > id) {
                        item.remove();
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Enter *id* correctly: ");
                value = scanner.nextLine();
            }
        }
    }
}

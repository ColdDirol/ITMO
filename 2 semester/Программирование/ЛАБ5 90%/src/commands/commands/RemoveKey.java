package commands.commands;

import collection.Collection;
import collection.flat.Flat;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * The class RemoveKey is used to execute the *remove_key id* command
 */
public class RemoveKey {
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();

    public void remove(Integer key) {
//        for(Iterator<Map.Entry<String, Flat>> item = hashtable.entrySet().iterator(); item.hasNext();) {
//            if (item.next().getKey().equals(key)) {
//                item.remove();
//                break;
//            }
//        }

        for(Map.Entry<Integer, Flat> item : hashtable.entrySet()){
            if(item.getKey().equals(key)) {
                hashtable.remove(key);
                System.out.println("The room called " + key + " has been deleted");
            }
        }
    }

    @Override
    public String toString() {
        return "remove_key";
    }
}

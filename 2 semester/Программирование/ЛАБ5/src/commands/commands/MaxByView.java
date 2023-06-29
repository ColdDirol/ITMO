package commands.commands;

import collection.Collection;
import collection.flat.Flat;
import collection.flat.View;

import java.util.Hashtable;
import java.util.Map;

/**
 * The class MaxByView is used to execute the *max_by_view* command
 */
public class MaxByView {
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();

    public void maxByView() {
        for(Map.Entry<Integer, Flat> item : hashtable.entrySet()){
            if(item.getValue().getView() == View.GOOD) {
                System.out.println(item.getValue().toString());
            }
        }
    }
}

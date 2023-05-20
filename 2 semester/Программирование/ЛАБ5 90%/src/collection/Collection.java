package collection;

import collection.flat.Flat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;


/**
 * The class Collection is the main class for storing Collection.
 * The Hashtable used as static to give access from any class.
 */
public class Collection {
    /**
     * @param hashtable
     * The private Hashtable static field with main collection
     */
    private static Hashtable<Integer, Flat> hashtable = new Hashtable<>();
    /**
     * @param initializationDate
     * The private LocalDateTime static field with LocalDateTime value of collection's initialization date
     */
    private static LocalDateTime initializationDate = LocalDateTime.now();

    public Hashtable<Integer, Flat> getHahstable() {
        return hashtable;
    }
    public void setHashtable(Hashtable<Integer, Flat> hashtable) {
        this.hashtable = hashtable;
    }
    public void setHashtableElements(Integer id, Flat flat) {
        hashtable.put(id, flat);
    }

    /**
     * The method getInitializationDate() get LocalDateTime value of collection's initialization date
     * @return LocalDateTime value
     */
    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }
    /**
     * The method updateInitializationDate() update LocalDateTime value of collection's initialization date
     */
    public void updateInitializationDate(){
        initializationDate = LocalDateTime.now();
    }

    /**
     * The method print() used to take information about the collection
     */
    public void print() {
        // получить набор всех ключей
        /**
         * @param keysSet is collection of type Set with Hashmap's keys (Integer id of Flat)
         */
        Set<Integer> keysSet = hashtable.keySet();
        // перевести набор ключей в список
        /**
         * @param keys is collection of type List with Hashmap's keys (Integer id of Flat)
         */
        ArrayList<Integer> keys = new ArrayList<>();
        for (Integer key : keysSet) keys.add(key);

        // получить набор всех значений
        /**
         * @param valuesSet is collection of type Collection with Hashmap's values (Flat objects)
         */
        java.util.Collection<Flat> valuesSet = hashtable.values();
        // перевести набор значений в список
        /**
         * @param values is collection of type List with Hashmap's values (Flat objects)
         */
        ArrayList<Flat> values = new ArrayList<>();
        for (Flat value : valuesSet) values.add(value);
        for(int i = 0; i < keys.size(); i++){
            System.out.println(keys.get(i) + " : " + values.get(i).getName());
        }
    }
}

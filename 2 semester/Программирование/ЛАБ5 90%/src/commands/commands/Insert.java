package commands.commands;

import collection.Collection;
import collection.ElementConstructor;
import collection.flat.*;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * The class Insert is used to execute the *insert 1 {element}* command
 */
public class Insert {
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();
    ElementConstructor elementConstructor = new ElementConstructor();
    Flat flat;
    public void insert(String value) {
        flat = elementConstructor.elementConstruct();

        hashtable.put(flat.getId(), flat);
    }
    public void insert(String value, Scanner scanner) {
        flat = elementConstructor.elementConstruct(scanner);

        if(flat.equals(null)){
            System.out.println("Error creating object");
        }
        else hashtable.put(flat.getId(), flat);
    }
}
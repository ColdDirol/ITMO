package commands.commands;

import collection.Collection;
import collection.flat.Flat;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The class SumOfNumberOfRooms is used to execute the *sum_of_numbers_of_rooms* command
 */
public class SumOfNumberOfRooms {
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();

    // Получить набор всех значений
    java.util.Collection <Flat> valuesCollection;

    int sum = 0;

    public void sumOfNumbersOfRooms(){
        valuesCollection = hashtable.values();
        for(Flat value : valuesCollection){
            sum += value.getNumberOfRooms();
        }
        System.out.println("The total number of rooms in the collection: " + sum);
    }

    @Override
    public String toString() {
        return "sum_of_numbers_of_rooms";
    }
}

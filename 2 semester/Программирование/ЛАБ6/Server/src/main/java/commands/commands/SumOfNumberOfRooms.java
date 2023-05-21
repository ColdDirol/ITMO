package commands.commands;

import collection.ServerCollection;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

import java.util.Hashtable;

public class SumOfNumberOfRooms {
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();

    // Получить набор всех значений
    java.util.Collection <Flat> valuesCollection;

    ResponseArrayList responseArrayList = new ResponseArrayList();
    int sum = 0;

    public void sumOfNumbersOfRooms(){
        valuesCollection = hashtable.values();
        for(Flat value : valuesCollection){
            sum += value.getNumberOfRooms();
        }

        responseArrayList.addElementToTheResponseArrayList("The total number of rooms in the collection: " + sum);
    }

    @Override
    public String toString() {
        return "sum_of_numbers_of_rooms";
    }
}

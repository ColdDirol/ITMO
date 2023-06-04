package commands.commands.server;

import collection.ServerCollection;
import collection.flat.Flat;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

import java.util.ArrayList;
import java.util.Hashtable;

public class SumOfNumberOfRooms implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();

    // Получить набор всех значений
    java.util.Collection <Flat> valuesCollection;

    ResponseArrayList responseArrayList = new ResponseArrayList();

    private Logger logger = LogManager.getLogger(SumOfNumberOfRooms.class.getSimpleName());

    ArrayList<Flat> collectionArrayList;

    public void sumOfNumbersOfRooms() {
        // Stream API <
        collectionArrayList = new ArrayList<>(serverCollection.getHahstable().values());
        long sum = collectionArrayList.stream().mapToLong(Flat::getNumberOfRooms).sum();
//      >

        // old algorithm <
//        int sum = 0;
//        valuesCollection = hashtable.values();
//        for(Flat value : valuesCollection){
//            sum += value.getNumberOfRooms();
//        }
//        >

        responseArrayList.addElementToTheResponseArrayList("The total number of rooms in the collection: " + sum);
        logger.info("The total number of rooms in the collection: " + sum);
    }

    @Override
    public String getCommandName() {
        return "sum_of_numbers_of_rooms";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to calculate the sum of the number of rooms of all elements of the collection.";
    }
}

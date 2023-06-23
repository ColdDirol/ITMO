package commands.clientcommands;

import collection.flatcollection.ServerCollection;
import collection.flatcollection.flat.Flat;
import serverlogic.outputchannel.ResponseArrayList;

/**
 * The class SumOfNumberOfRooms is used to execute the *sum_of_numbers_of_rooms* command
 */
public class SumOfNumberOfRooms {
    ServerCollection serverCollection = new ServerCollection();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void sumOfNumbersOfRooms(Integer userId){
        Long sumOfNumberOfRooms = serverCollection.getHahstable().values().stream()
                .filter(flat -> flat.getUser_id().equals(userId))
                .mapToLong(Flat::getNumberOfRooms)
                .sum();

        responseArrayList.addStringToResponseArrayList("The total number of rooms in the collection: " + sumOfNumberOfRooms);
    }

    @Override
    public String toString() {
        return "sum_of_numbers_of_rooms";
    }
}

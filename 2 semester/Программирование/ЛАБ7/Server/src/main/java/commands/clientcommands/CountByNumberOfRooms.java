package commands.clientcommands;

import collection.flatcollection.ServerCollection;
import collection.flatcollection.flat.Flat;
import org.apache.log4j.Logger;
import serverlogic.outputchannel.ResponseArrayList;

import java.util.Map;

public class CountByNumberOfRooms {

    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = Logger.getLogger(CountByNumberOfRooms.class.getSimpleName());

    public void countByNumberOfRooms(String numberOfRooms, Integer userId) {
        int cnt = 0;
        try {
            for (Map.Entry<Integer, Flat> item : ServerCollection.getHahstable().entrySet()) {
                if (item.getValue().getNumberOfRooms() == Long.parseLong(numberOfRooms) && item.getValue().getUser_id() == userId) {
                    cnt++;
                }
            }
            if(cnt == 0) {
                responseArrayList.addStringToResponseArrayList("Elements has not exist with number " + Long.parseLong(numberOfRooms));
                logger.info("Elements has not exist with number " + Long.parseLong(numberOfRooms));
            }
            else {
                responseArrayList.addStringToResponseArrayList("The number of elements is: " + cnt);
                logger.info("The number of elements is: " + cnt);
            }
        } catch (NumberFormatException e) {
            System.out.print("Пожалуйста, введите данную команду с числом!");
            logger.info("Пожалуйста, введите данную команду с числом!");
            responseArrayList.addStringToResponseArrayList("Пожалуйста, введите данную команду с числом!");
            logger.info("Пожалуйста, введите данную команду с числом!");
        }
    }

    @Override
    public String toString() {
        return "count_by_number_of_rooms";
    }
}

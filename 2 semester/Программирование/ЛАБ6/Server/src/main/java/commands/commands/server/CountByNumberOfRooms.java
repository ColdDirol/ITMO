package commands.commands.server;

import collection.ServerCollection;
import collection.flat.Flat;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class CountByNumberOfRooms implements CommandInterface {
    Scanner scanner;
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(CountByNumberOfRooms.class.getSimpleName());

    Flat flat;
    public void countByNumberOfRooms(String numberOfRooms) {
        int cnt = 0;
        try {
            for (Map.Entry<Integer, Flat> item : hashtable.entrySet()) {
                if (item.getValue().getNumberOfRooms() == Long.parseLong(numberOfRooms)) {
                    cnt++;
                }
            }
            if(cnt == 0) {
                responseArrayList.addElementToTheResponseArrayList("Elements has not exist with number " + Long.parseLong(numberOfRooms));
                logger.info("Elements has not exist with number " + Long.parseLong(numberOfRooms));
            }
            else {
                responseArrayList.addElementToTheResponseArrayList("The number of elements is: " + cnt);
                logger.info("The number of elements is: " + cnt);
            }
        } catch (NumberFormatException e) {
            System.out.print("Пожалуйста, введите данную команду с числом!");
            logger.info("Пожалуйста, введите данную команду с числом!");
            responseArrayList.addElementToTheResponseArrayList("Пожалуйста, введите данную команду с числом!");
            logger.info("Пожалуйста, введите данную команду с числом!");
        }
    }

    @Override
    public String getCommandName() {
        return "count_by_number_of_rooms";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to clear the collection for clear the collection.";
    }
}

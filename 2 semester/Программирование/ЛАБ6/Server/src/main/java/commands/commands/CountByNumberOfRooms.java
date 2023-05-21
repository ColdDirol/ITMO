package commands.commands;

import collection.ServerCollection;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class CountByNumberOfRooms implements Command {
    Scanner scanner;
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    Flat flat;
    public void countByNumberOfRooms(String numberOfRooms) {
        int cnt = 0;
        try {
            for (Map.Entry<Integer, Flat> item : hashtable.entrySet()) {
                if (item.getValue().getNumberOfRooms() == Long.parseLong(numberOfRooms)) {
                    cnt++;
                }
            }
            if(cnt == 0) responseArrayList.addElementToTheResponseArrayList("Elements has not exist with number " + Long.parseLong(numberOfRooms));
            else responseArrayList.addElementToTheResponseArrayList("The number of elements is: " + cnt);
        } catch (NumberFormatException e) {
            System.out.print("Пожалуйста, введите данную команду с числом!");
            responseArrayList.addElementToTheResponseArrayList("Пожалуйста, введите данную команду с числом!");
        }
    }

    @Override
    public String toString() {
        return "count_by_number_of_rooms";
    }

    @Override
    public String description() {
        return "The \"count_by_number_of_rooms\" command is used to clear the collection for clear the collection.";
    }
}

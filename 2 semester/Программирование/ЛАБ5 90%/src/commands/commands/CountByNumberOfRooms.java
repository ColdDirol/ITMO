package commands.commands;

import collection.Collection;
import collection.ElementConstructor;
import collection.flat.Flat;

import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

/**
 * The class CountByNumbersOfRooms is used to execute the *count_by_numbers_of_rooms* command
 */
public class CountByNumberOfRooms {
    Scanner scanner;
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();
    int cnt = 0;

    Flat flat;
    public void countByNumberOfRooms(String numberOfRooms) {
        scanner = new Scanner(System.in);
        while(true) {
            try {
                for (Map.Entry<Integer, Flat> item : hashtable.entrySet()) {
                    if (item.getValue().getNumberOfRooms() == Long.parseLong(numberOfRooms)) {
                        cnt++;
                    }
                }
                if(cnt == 0) System.out.println("Elements has not exist with number " + Long.parseLong(numberOfRooms));
                else System.out.println("The number of elements is: " + cnt);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите число: ");
                numberOfRooms = scanner.nextLine();
            }
        }
    }

    @Override
    public String toString() {
        return "count_by_number_of_rooms";
    }
}

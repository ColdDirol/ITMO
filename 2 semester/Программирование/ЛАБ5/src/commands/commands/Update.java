package commands.commands;

import collection.Collection;
import collection.ElementConstructor;
import collection.flat.Flat;

import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * The class Update is used to execute the *update id* command
 */
public class Update {
    Scanner scanner;
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();
    ElementConstructor elementConstructor = new ElementConstructor();
    Set<Integer> idSet = hashtable.keySet();
    Flat flat;
    public void update(String value) {
        scanner = new Scanner(System.in);
        while(true) {
            try {
                Integer id = Integer.parseInt(value);
                if(idSet.contains(id)) {
                    flat = elementConstructor.elementConstruct(id);
                    hashtable.replace(id, flat);
                    System.out.println("Command has been completed.");
                } else {
                    throw new NullPointerException("Format error");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Enter *id* correctly: ");
                value = scanner.nextLine();
            } catch (NullPointerException e) {
                System.out.println("There is no element with this *id*.");
                System.out.print("Enter the *id* of element which exist, or \"0\" to end the command: ");
                value = scanner.nextLine();
                if(value.equals("0")) {
                    System.out.println("The command has been ended.");
                    break;
                }
            }
        }
    }

    public void update(String value, Scanner scanner) {
        try {
            Integer id = Integer.parseInt(value);
            if(idSet.contains(id)) {
                flat = elementConstructor.elementConstruct(id, scanner);
                hashtable.replace(id, flat);
                System.out.println("Command has been completed.");
            } else {
                throw new NullPointerException("Format error");
            }
        } catch (NumberFormatException e) {
            System.out.print("Invalid *id* entered.");
            System.out.println("The operation was not completed.");
        } catch (NullPointerException e) {
            System.out.println("Flat object with this *id* does not exist in the collection.");
            System.out.println("The operation was not completed.");
        }
    }
}

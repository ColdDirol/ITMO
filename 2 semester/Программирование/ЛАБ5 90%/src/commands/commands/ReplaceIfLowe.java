package commands.commands;

import collection.Collection;
import collection.ElementConstructor;
import collection.flat.Flat;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

/**
 * The class ReplaceIfLowe is used to execute the *replace_if_lowe name* command
 */
public class ReplaceIfLowe {
    Collection collection = new Collection();
    Hashtable<Integer, Flat> hashtable = collection.getHahstable();
    ElementConstructor elementConstructor = new ElementConstructor();
    Flat oldFlat;
    Flat newFlat;

    Scanner scanner = new Scanner(System.in);
    Set<Integer> idSet = hashtable.keySet();

    public void replaceIfLowe(String value) {
        while(true) {
            try {
                Integer id = Integer.parseInt(value);
                if(idSet.contains(id)) {
                    oldFlat = hashtable.get(id);
                    newFlat = elementConstructor.elementConstruct(id);
                    if(newFlat.objectSize() < oldFlat.objectSize()) {
                        hashtable.replace(id, newFlat);
                    } else {
                        System.out.println("New *flat object* is greater than old.");
                        System.out.println("The operation was not completed.");
                    }
                } else {
                    throw new NullPointerException("Format error");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Enter *id* correctly: ");
                value = scanner.nextLine();
            } catch (NullPointerException e) {
                System.out.println("\"Flat object with this *id* does not exist in the collection.\"");
                System.out.print("Enter the *id* of element which exist, or \"0\" to end the command: ");
                value = scanner.nextLine();
                if(value.equals("0")) {
                    System.out.println("The command has been ended.");
                    break;
                }
            }
        }
    }

    public void replaceIfLowe(String value, Scanner scanner) {
        try {
            Integer id = Integer.parseInt(value);
            if(idSet.contains(id)) {
                oldFlat = hashtable.get(id);
                newFlat = elementConstructor.elementConstruct(id, scanner);
                if(newFlat.objectSize() < oldFlat.objectSize()) {
                    hashtable.replace(id, newFlat);
                } else {
                    System.out.println("New *flat object* is greater than old.");
                    System.out.println("The operation was not completed.");
                }
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

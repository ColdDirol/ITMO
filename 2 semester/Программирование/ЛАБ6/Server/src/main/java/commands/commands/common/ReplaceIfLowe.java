package commands.commands.common;

import collection.ElementConstructor;
import collection.ServerCollection;
import collection.flat.Flat;
import commands.commands.CommandInterface;
import history.HistoryCollection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

import java.util.Hashtable;
import java.util.Set;

public class ReplaceIfLowe implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    HistoryCollection historyCollection = new HistoryCollection();
    ElementConstructor elementConstructor = new ElementConstructor();
    Flat oldFlat;
    Flat newFlat;
    ResponseArrayList responseArrayList = new ResponseArrayList();
    Set<Integer> idSet = serverCollection.getHahstable().keySet();

    private Logger logger = LogManager.getLogger(ReplaceIfLowe.class.getSimpleName());

    public void replaceIfLowe(Integer key, String elements) {
        try {
            Integer id = key;
            if(idSet.contains(id)) {
                oldFlat = serverCollection.getHahstable().get(id);
                newFlat = elementConstructor.elementConstructor(id, elements);
                if(newFlat.objectSize() < oldFlat.objectSize()) {
                    replace(id);

                    System.out.println("*Flat object* has been replaced!");
                    responseArrayList.addElementToTheResponseArrayList("*Flat object* has been replaced!");
                    logger.info("*Flat object* has been replaced!");
                } else {
                    System.out.println("New *flat object* is greater than old.");
                    responseArrayList.addElementToTheResponseArrayList("New *flat object* is greater than old.");
                    logger.info("New *flat object* is greater than old.");
                    System.out.println("The operation was not completed.");
                    responseArrayList.addElementToTheResponseArrayList("The operation was not completed.");
                    logger.info("The operation was not completed.");
                }
            } else {
                throw new NullPointerException("Format error");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please, use number as key!");
            responseArrayList.addElementToTheResponseArrayList("Please, use a number as key!");
            logger.info("Please, use a number as key!");
        } catch (NullPointerException e) {
            responseArrayList.addElementToTheResponseArrayList("Enter the *id* key of element which exist!");
            logger.info("Enter the *id* key of element which exist!");
        }
    }

    private void replace(Integer id) {
        serverCollection.getHahstable().replace(id, newFlat);
        historyCollection.addHistoryElement(serverCollection.getHahstable());
    }

    @Override
    public String getCommandName() {
        return "replace_if_lowe";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to replace the collection object if new object is lowe.";
    }
}
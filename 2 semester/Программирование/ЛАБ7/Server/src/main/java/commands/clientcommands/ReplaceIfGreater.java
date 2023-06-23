package commands.clientcommands;

import collection.flatcollection.ElementConstructor;
import collection.flatcollection.ServerCollection;
import collection.flatcollection.flat.Flat;
import database.actions.FlatActions;
import org.apache.log4j.Logger;
import serverlogic.outputchannel.ResponseArrayList;

public class ReplaceIfGreater {
    ServerCollection serverCollection = new ServerCollection();
    FlatActions flatActions = new FlatActions();
    ElementConstructor elementConstructor = new ElementConstructor();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    Logger logger = Logger.getLogger(ReplaceIfLowe.class.getSimpleName());
    Flat oldFlat;
    Flat newFlat;
    public void replaceIfGreater(String flatId, Integer clientId, Integer userId) {
        try {
            oldFlat = flatActions.getFlatById(Integer.valueOf(flatId), userId);
            if(oldFlat.equals(null)) {
                System.out.println("ERROR!");
                return;
            }
            newFlat = elementConstructor.elementConstruct(clientId, userId, Integer.parseInt(flatId));

            if(newFlat.objectSize() > oldFlat.objectSize()) {
                if (flatActions.updateFlatIntDB(newFlat, userId)) {
                    serverCollection.updateElementOfCollection(Integer.valueOf(flatId), newFlat);
                    System.out.println("*Flat object* has been replaced!");
                    responseArrayList.addStringToResponseArrayList("*Flat object* has been replaced!");
                    logger.info("*Flat object* has been replaced!");
                } else {
                    System.out.println("ERROR with updating the flat object!");
                    responseArrayList.addStringToResponseArrayList("ERROR with updating the flat object!");
                    logger.info("ERROR with updating the flat object!");
                }
            } else {
                System.out.println("New *flat object* is lower than old.");
                responseArrayList.addStringToResponseArrayList("New *flat object* is lower than old.");
                logger.info("New *flat object* is lower than old.");
                System.out.println("The operation was not completed.");
                responseArrayList.addStringToResponseArrayList("The operation was not completed.");
                logger.info("The operation was not completed.");
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("Some problems!");
        } catch (NumberFormatException e) {
            responseArrayList.addStringToResponseArrayList("Пожалуйста, введите данную команду с числом!");
        }
    }

    @Override
    public String toString() {
        return "replace_if_greater";
    }
}

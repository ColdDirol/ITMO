package commands.clientcommands;

import collection.flatcollection.ElementConstructor;
import collection.flatcollection.ServerCollection;
import collection.flatcollection.flat.Flat;
import database.actions.FlatActions;
import serverlogic.outputchannel.ResponseArrayList;

/**
 * The class Insert is used to execute the *insert 1 {element}* command
 */
public class Insert {
    ServerCollection serverCollection = new ServerCollection();
    ElementConstructor elementConstructor = new ElementConstructor();
    FlatActions flatActions = new FlatActions();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    Flat flat;
    public void insert(String attribute, Integer clientId, Integer userId) {
        try {
            flat = elementConstructor.elementConstruct(clientId, userId);
            if (flatActions.addNewFlatToDB(flat)) {
                serverCollection.addObjectToHashtable(flat.getId(), flat);
                responseArrayList.addStringToResponseArrayList("New flat object has been created!");
            } else {
                System.out.println("ERROR with inserting the flat object!");
                responseArrayList.addStringToResponseArrayList("ERROR with inserting the flat object!");
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("Some problems!");
        }
    }

    @Override
    public String toString() {
        return "insert";
    }
}
package commands.clientcommands;

import collection.flatcollection.ElementConstructor;
import collection.flatcollection.ServerCollection;
import collection.flatcollection.flat.Flat;
import database.actions.FlatActions;
import serverlogic.outputchannel.ResponseArrayList;

public class Update {
    ServerCollection serverCollection = new ServerCollection();
    ElementConstructor elementConstructor = new ElementConstructor();
    FlatActions flatActions = new FlatActions();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    Flat flat;
    public void update(String attribute, Integer clientId, Integer userId) {
        try {
            flat = elementConstructor.elementConstruct(clientId, userId, Integer.parseInt(attribute));
            if(flatActions.updateFlatIntDB(flat, userId)) {
                serverCollection.updateElementOfCollection(Integer.valueOf(attribute), flat);
                responseArrayList.addStringToResponseArrayList("Flat has been updated!");
            } else {
                System.out.println("ERROR with updating the flat object!");
                responseArrayList.addStringToResponseArrayList("ERROR with updating the flat object!");
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
        return "update";
    }
}

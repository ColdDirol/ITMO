package commands.commands.server;

import collection.ServerCollection;
import collection.flat.Flat;
import collection.flat.View;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

import java.util.*;

public class MaxByView implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    ArrayList<Flat> collectionArrayList;

    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(MaxByView.class.getSimpleName());

    public void maxByView() {
        // Stream API <
        if(serverCollection.getHahstable().isEmpty()) {
            responseArrayList.addElementToTheResponseArrayList("Your collection is empty!");
            logger.info("Your collection is empty!");
        } else {
            collectionArrayList = new ArrayList<>(serverCollection.getHahstable().values());
            int maxId = collectionArrayList.stream().max(Comparator.comparingInt(Flat::getViewId)).orElse(new Flat()).getViewId();
            responseArrayList.addElementToTheResponseArrayList("Max element in the current collection is " + View.getNameById(maxId));
            logger.info("Max element in the current collection is " + View.getNameById(maxId));
        }
//      >




        // old algorithm <
//        int maxId = 0;
//        Integer flatId = null;
//        String name = null;
//        for(Map.Entry<Integer, Flat> item : hashtable.entrySet()){
//            System.out.println(item.getKey());
//            if(item.getValue().getView().getId() > maxId) {
//                maxId = item.getValue().getView().getId();
//                name = item.getValue().getView().getName();
//                flatId = item.getKey();
//            }
//        }
//
//
//        if(hashtable.size() == 0 & !flatId.equals(null)) {
//            responseArrayList.addElementToTheResponseArrayList("Your collection is empty!");
//            logger.info("Your collection is empty!");
//        }
//        else {
//            responseArrayList.addElementToTheResponseArrayList("Max element in the current collection is " + name + " in the flat " + flatId);
//            logger.info("Max element in the current collection is " + name + " in the flat " + flatId);
//        }
//        >
    }

    @Override
    public String getCommandName() {
        return "max_by_view";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to send to the client max level of View field in the collection.";
    }
}

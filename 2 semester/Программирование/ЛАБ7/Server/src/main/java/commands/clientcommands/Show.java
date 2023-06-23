package commands.clientcommands;

import collection.flatcollection.ServerCollection;
import collection.flatcollection.flat.Flat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.outputchannel.ResponseArrayList;

import java.util.Comparator;

/**
 * The class Show is used to execute the *show* command
 */
public class Show {
    ServerCollection serverCollection = new ServerCollection();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(Show.class.getSimpleName());
    public void show(Integer userId) throws InterruptedException {
        responseArrayList.addStringToResponseArrayList("-----------");
        serverCollection.getHahstable().values()
                .stream()
                .filter(flat -> flat.getUser_id() == userId)
                .sorted(Comparator.comparing(Flat::getName))
                .forEach(flat -> responseArrayList.addStringToResponseArrayList(
                        "Name: " + flat.getName() + ", \n\rFlat ID: " + flat.getId() + ", \n\rUID: " + flat.getUser_id() + ", \n\rHouse name: " + flat.getHouseName() + "\n\r"));

        logger.info("Show command has been executed!");


//        serverCollection.getHahstable().values()
//                .stream()
//                .sorted(Comparator.comparing(Flat::getName))
//                .forEach(flat -> responseArrayList.addStringToResponseArrayList(
//                        "Name: " + flat.getName() + ", \n\rUser ID: " + flat.getUser_id()
//                                + ", \n\rID: " + flat.getId() + ", \n\rHouse name: " + flat.getHouseName() + "\n\r"));
    }

    @Override
    public String toString() {
        return "show";
    }
}

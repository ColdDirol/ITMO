package commands.clientcommands;

import collection.flatcollection.ServerCollection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.outputchannel.ResponseArrayList;

/**
 * The class Info is used to execute the *info* command
 */
public class Info {
    ServerCollection serverCollection = new ServerCollection();

    ResponseArrayList responseArrayList = new ResponseArrayList();
    //OutputChannel outputChannel = new OutputChannel();

    private String headString;
    private String collectionTypeString;
    private String initializationDateString;
    private String numberOfElementsString;
    private Logger logger = LogManager.getLogger(Info.class.getSimpleName());
    public void info(Integer userId){
        headString = "Information about the collection:";
        System.out.println(headString);
        responseArrayList.addStringToResponseArrayList(headString);
        logger.info(headString);
        //outputChannel.addElementToResponseArrayList("Information about the collection:");

        collectionTypeString = "Collection type: " + serverCollection.getHahstable().getClass().getSimpleName() + ";";
        System.out.println(collectionTypeString);
        responseArrayList.addStringToResponseArrayList(collectionTypeString);
        logger.info(collectionTypeString);
        //outputChannel.addElementToResponseArrayList(collectionTypeString);


        initializationDateString = "Initialization date: "
                + serverCollection.getInitializationDate().getHour() + ":"
                + serverCollection.getInitializationDate().getMinute() + " "
                + serverCollection.getInitializationDate().getDayOfMonth() + " "
                + serverCollection.getInitializationDate().getMonth() + " "
                + serverCollection.getInitializationDate().getYear();

        System.out.println(initializationDateString);
        responseArrayList.addStringToResponseArrayList(initializationDateString);
        logger.info(serverCollection.getInitializationDate());
        //outputChannel.addElementToResponseArrayList(initializationDateString);

        numberOfElementsString = "Number of elements: " + serverCollection.getHahstable().size();
        System.out.println(numberOfElementsString);
        responseArrayList.addStringToResponseArrayList(numberOfElementsString);
        logger.info(numberOfElementsString);
        //outputChannel.addElementToResponseArrayList(numberOfElementsString);
    }

    @Override
    public String toString() {
        return "info";
    }
}

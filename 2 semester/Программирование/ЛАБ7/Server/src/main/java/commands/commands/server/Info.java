package commands.commands.server;

import collection.ServerCollection;
import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

public class Info implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    //Hashtable<String, Flat> hashtable = serverCollection.getHahstable();
    //LocalDateTime initializationDate = serverCollection.getInitializationDate();

    ResponseArrayList responseArrayList = new ResponseArrayList();
    //OutputChannel outputChannel = new OutputChannel();

    private String headString;
    private String collectionTypeString;
    private String initializationDateString;
    private String numberOfElementsString;
    private Logger logger = LogManager.getLogger(Info.class.getSimpleName());
    public void info(){
        headString = "Information about the collection:";
        System.out.println(headString);
        responseArrayList.addElementToTheResponseArrayList(headString);
        logger.info(headString);
        //outputChannel.addElementToResponseArrayList("Information about the collection:");

        collectionTypeString = "Collection type: " + serverCollection.getHahstable().getClass().getSimpleName() + ";";
        System.out.println(collectionTypeString);
        responseArrayList.addElementToTheResponseArrayList(collectionTypeString);
        logger.info(collectionTypeString);
        //outputChannel.addElementToResponseArrayList(collectionTypeString);


        initializationDateString = "Initialization date: "
                + serverCollection.getInitializationDate().getHour() + ":"
                + serverCollection.getInitializationDate().getMinute() + " "
                + serverCollection.getInitializationDate().getDayOfMonth() + " "
                + serverCollection.getInitializationDate().getMonth() + " "
                + serverCollection.getInitializationDate().getYear();

        System.out.println(initializationDateString);
        responseArrayList.addElementToTheResponseArrayList(initializationDateString);
        logger.info(serverCollection.getInitializationDate());
        //outputChannel.addElementToResponseArrayList(initializationDateString);

        numberOfElementsString = "Number of elements: " + serverCollection.getHahstable().size();
        System.out.println(numberOfElementsString);
        responseArrayList.addElementToTheResponseArrayList(numberOfElementsString);
        logger.info(numberOfElementsString);
        //outputChannel.addElementToResponseArrayList(numberOfElementsString);
    }

    @Override
    public String getCommandName() {
        return "info";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to show short information about the collection.";
    }
}

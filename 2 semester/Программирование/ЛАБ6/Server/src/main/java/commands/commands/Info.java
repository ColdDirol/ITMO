package commands.commands;

import collection.ServerCollection;
import collection.flat.Flat;
import serverlogic.ResponseArrayList;

import java.time.LocalDateTime;
import java.util.Hashtable;

public class Info {
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();
    LocalDateTime initializationDate = serverCollection.getInitializationDate();

    ResponseArrayList responseArrayList = new ResponseArrayList();
    //OutputChannel outputChannel = new OutputChannel();

    private String headString;
    private String collectionTypeString;
    private String initializationDateString;
    private String numberOfElementsString;
    public void info(){
        headString = "Information about the collection:";
        System.out.println(headString);
        responseArrayList.addElementToTheResponseArrayList(headString);
        //outputChannel.addElementToResponseArrayList("Information about the collection:");

        collectionTypeString = "Collection type: " + hashtable.getClass().getSimpleName() + ";";
        System.out.println(collectionTypeString);
        responseArrayList.addElementToTheResponseArrayList(collectionTypeString);
        //outputChannel.addElementToResponseArrayList(collectionTypeString);


        initializationDateString = "Initialization date: "
                + initializationDate.getHour() + ":"
                + initializationDate.getMinute() + " "
                + initializationDate.getDayOfMonth() + " "
                + initializationDate.getMonth() + " "
                + initializationDate.getYear();

        System.out.println(initializationDateString);
        responseArrayList.addElementToTheResponseArrayList(initializationDateString);
        //outputChannel.addElementToResponseArrayList(initializationDateString);

        numberOfElementsString = "Number of elements: " + hashtable.size();
        System.out.println(numberOfElementsString);
        responseArrayList.addElementToTheResponseArrayList(numberOfElementsString);
        //outputChannel.addElementToResponseArrayList(numberOfElementsString);
    }

    @Override
    public String toString() {
        return "info";
    }
}

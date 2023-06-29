package com.itmo.client.clientlogic.inputlogic;

import com.itmo.client.construct.RootContainer;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class CollectionFromServer {
    RootContainer rootContainer = new RootContainer();
    private static ArrayList<String[]> collectionArrayList;
    public void updateCollectionFromServer() throws IOException, ParseException, InterruptedException {
        collectionArrayList = rootContainer.getJSONArrayList();
    }

    public static ArrayList<String[]> getCollectionArrayList() {
        return collectionArrayList;
    }
}

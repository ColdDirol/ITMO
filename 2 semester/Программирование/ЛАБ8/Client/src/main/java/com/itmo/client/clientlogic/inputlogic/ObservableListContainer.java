package com.itmo.client.clientlogic.inputlogic;

import com.itmo.client.construct.RootContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class ObservableListContainer {
    private ObservableList<String[]> data;
    static RootContainer rootContainer = new RootContainer();
    static CollectionFromServer collectionFromServer = new CollectionFromServer();

    public void updateObservableList() throws IOException, ParseException, InterruptedException {
        rootContainer.sendCommandJSONArray("show_as_array");
        collectionFromServer.updateCollectionFromServer();
        rootContainer.setStringArrayList(collectionFromServer.getCollectionArrayList());
        data = FXCollections.observableArrayList(rootContainer.getStringArrayList());
    }

    public static void updateObservableList(String MODE) throws IOException, ParseException, InterruptedException {
        rootContainer.sendCommandJSONArray("show_as_array");
        collectionFromServer.updateCollectionFromServer();
        rootContainer.setStringArrayList(collectionFromServer.getCollectionArrayList());
    }

    public ObservableList<String[]> getData() throws IOException, ParseException, InterruptedException {
        updateObservableList();
        return data;
    }
}

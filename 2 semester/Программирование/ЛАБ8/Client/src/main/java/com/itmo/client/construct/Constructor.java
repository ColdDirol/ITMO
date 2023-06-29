package com.itmo.client.construct;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Constructor {
    RootContainer rootContainer = new RootContainer();
    public void construct(Stage stage) throws IOException, InterruptedException {
        Socket socket = new Socket();
        rootContainer.connect();
        rootContainer.setStage(stage);
    }
}

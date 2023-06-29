package com.itmo.client;

import com.itmo.client.localisation.Localization;
import com.itmo.client.scenes.authorization.AuthorizationChooseModeScene;
import com.itmo.client.construct.Constructor;
import com.itmo.client.construct.RootContainer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {
    Constructor constructor = new Constructor();
    RootContainer rootContainer = new RootContainer();

    AuthorizationChooseModeScene authorizationChooseModeScene = new AuthorizationChooseModeScene();
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        stage.setResizable(false);

        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });

        constructor.construct(stage);

        authorizationChooseModeScene.showScene(stage);
    }

    public static void main(String[] args) {
        Localization.setLocale();
        launch();
    }
}
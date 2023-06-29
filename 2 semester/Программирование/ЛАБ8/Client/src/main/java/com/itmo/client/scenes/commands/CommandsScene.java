package com.itmo.client.scenes.commands;

import com.itmo.client.localisation.Localization;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CommandsScene {
    InsertCommandScene insertCommandScene = new InsertCommandScene();
    RemoveCommandScene removeCommandScene = new RemoveCommandScene();
    UpdateCommandScene updateCommandScene = new UpdateCommandScene();
    ExecuteScriptCommandScene executeScriptCommandScene = new ExecuteScriptCommandScene();

    Font rubikLight23;
    Stage commandsStage;
    public void showScene() throws UnsupportedEncodingException {
        rubikLight23 = Font.loadFont(getClass().getResourceAsStream("/fonts/rubikLight.ttf"), 23);

        VBox commandsVBox = new VBox();
        commandsVBox.setMaxSize(500, 800);


        Button buttonExecuteScript = new Button(Localization.getText("buttonExecuteScript"));
        buttonExecuteScript.setFont(rubikLight23);
        buttonExecuteScript.setMaxSize(500, 50);
        buttonExecuteScript.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonClear = new Button(Localization.getText("buttonClear"));
        buttonClear.setFont(rubikLight23);
        buttonClear.setMaxSize(500, 50);
        buttonClear.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonCountByNumberOfRooms= new Button(Localization.getText("buttonCountByNumberOfRooms"));
        buttonCountByNumberOfRooms.setFont(rubikLight23);
        buttonCountByNumberOfRooms.setMaxSize(500, 50);
        buttonCountByNumberOfRooms.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonEcho = new Button(Localization.getText("buttonEcho"));
        buttonEcho.setFont(rubikLight23);
        buttonEcho.setMaxSize(500, 50);
        buttonEcho.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonHelp = new Button(Localization.getText("buttonHelp"));
        buttonHelp.setFont(rubikLight23);
        buttonHelp.setMaxSize(500, 50);
        buttonHelp.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonInfo = new Button(Localization.getText("buttonInfo"));
        buttonInfo.setFont(rubikLight23);
        buttonInfo.setMaxSize(500, 50);
        buttonInfo.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonInsert = new Button(Localization.getText("buttonInsert"));
        buttonInsert.setFont(rubikLight23);
        buttonInsert.setMaxSize(500, 50);
        buttonInsert.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonMaxByView = new Button(Localization.getText("buttonMaxByView"));
        buttonMaxByView.setFont(rubikLight23);
        buttonMaxByView.setMaxSize(500, 50);
        buttonMaxByView.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonRemove = new Button(Localization.getText("buttonRemove"));
        buttonRemove.setFont(rubikLight23);
        buttonRemove.setMaxSize(500, 50);
        buttonRemove.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonReplaceIfGreater = new Button(Localization.getText("buttonReplaceIfGreater"));
        buttonReplaceIfGreater.setFont(rubikLight23);
        buttonReplaceIfGreater.setMaxSize(500, 50);
        buttonReplaceIfGreater.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonReplaceIfLowe = new Button(Localization.getText("buttonReplaceIfLowe"));
        buttonReplaceIfLowe.setFont(rubikLight23);
        buttonReplaceIfLowe.setMaxSize(500, 50);
        buttonReplaceIfLowe.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonUpdate = new Button(Localization.getText("buttonUpdate"));
        buttonUpdate.setFont(rubikLight23);
        buttonUpdate.setMaxSize(500, 50);
        buttonUpdate.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonClose = new Button(Localization.getText("buttonClose"));
        buttonClose.setFont(rubikLight23);
        buttonClose.setMaxSize(500, 50);
        buttonClose.setStyle("-fx-background-color: #FF9999; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки



        commandsVBox.setSpacing(15);
        commandsVBox.setPadding(new Insets(15, 0, 0, 0));
        commandsVBox.getChildren().add(buttonExecuteScript);
        commandsVBox.getChildren().add(buttonClear);
        commandsVBox.getChildren().add(buttonCountByNumberOfRooms);
        commandsVBox.getChildren().add(buttonEcho);
        commandsVBox.getChildren().add(buttonHelp);
        commandsVBox.getChildren().add(buttonInfo);
        commandsVBox.getChildren().add(buttonInsert);
        commandsVBox.getChildren().add(buttonMaxByView);
        commandsVBox.getChildren().add(buttonRemove);
        commandsVBox.getChildren().add(buttonReplaceIfGreater);
        commandsVBox.getChildren().add(buttonReplaceIfLowe);
        commandsVBox.getChildren().add(buttonUpdate);
        commandsVBox.getChildren().add(buttonClose);

        Scene scene = new Scene(commandsVBox, 500, 800);
        commandsStage = new Stage();
        commandsStage.setResizable(false);
        commandsStage.setScene(scene);
        commandsStage.show();


        buttonExecuteScript.setOnAction(actionEvent -> {
            System.out.println("to ExecuteScript");
            try {
                executeScriptCommandScene.execute();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        buttonInsert.setOnAction(actionEvent -> {
            System.out.println("to Insert");
            try {
                insertCommandScene.flatName(buttonInsert);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        buttonRemove.setOnAction(actionEvent -> {
            System.out.println("to Remove");
            try {
                removeCommandScene.remove();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        buttonUpdate.setOnAction(actionEvent -> {
            System.out.println("to Update");
            try {
                updateCommandScene.update(buttonUpdate);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        buttonClose.setOnAction(actionEvent -> {
            System.out.println("to WorkScene");
            commandsStage.close();
        });
    }

    public boolean isCommandShowing() throws NullPointerException {
        return commandsStage.isShowing();
    }
}

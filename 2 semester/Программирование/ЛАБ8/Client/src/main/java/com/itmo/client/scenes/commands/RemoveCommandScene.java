package com.itmo.client.scenes.commands;

import com.itmo.client.construct.RootContainer;
import com.itmo.client.localisation.Localization;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RemoveCommandScene {
    RootContainer rootContainer = new RootContainer();
    Font rubikLight23;

    public void remove() throws UnsupportedEncodingException {
        rubikLight23 = Font.loadFont(getClass().getResourceAsStream("/fonts/rubikLight.ttf"), 23);

        Text removeText = new Text(Localization.getText("removeText"));
        removeText.setFont(rubikLight23);


        Button buttonRemove = new Button("Remove");
        buttonRemove.setFont(rubikLight23);
        buttonRemove.setPrefWidth(150);
        buttonRemove.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки


        TextField inputTextField = new TextField();
        inputTextField.setAlignment(Pos.CENTER);
        inputTextField.setFont(rubikLight23);
//        input.setStyle("-fx-background-color: F1F0F0; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50; -fx-border-width: 1px; -fx-border-color: gray; -fx-padding: 5px;");
        inputTextField.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;");
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inputTextField.getText().length() > 20 || !isNumber(inputTextField.getText())) {
                inputTextField.clear();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Максимальное количество символов - 20");
                alert.showAndWait();
            }
        });


        GridPane gridPane = new GridPane();
        gridPane.add(removeText, 0, 0);
        gridPane.setHalignment(removeText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonRemove, 0, 2);
        gridPane.setHalignment(buttonRemove, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        Stage stageRemove = new Stage();
        stageRemove.setResizable(false);
        stageRemove.setScene(scene);
        stageRemove.show();

        buttonRemove.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("remove_key " + inputTextField.getText());
                confirm(stageRemove);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void confirm(Stage stage) throws IOException, ParseException, InterruptedException {
        rubikLight23 = Font.loadFont(getClass().getResourceAsStream("/fonts/rubikLight.ttf"), 23);

        //rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text(rootContainer.getResponseArrayListAsString());
        flatNameText.setFont(rubikLight23);


        Button buttonConfirm = new Button("Ok");
        buttonConfirm.setFont(rubikLight23);
        buttonConfirm.setPrefWidth(150);
        buttonConfirm.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки


        GridPane gridPane = new GridPane();
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(buttonConfirm, 0, 1);
        gridPane.setHalignment(buttonConfirm, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonConfirm.setOnAction(actionEvent -> {
            stage.close();
        });
    }

    private boolean isNumber(String str) {
        try {
            int number = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

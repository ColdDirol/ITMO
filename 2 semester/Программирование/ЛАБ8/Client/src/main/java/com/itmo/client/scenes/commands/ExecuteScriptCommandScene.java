package com.itmo.client.scenes.commands;

import com.itmo.client.commands.clientcommands.ExecuteScript;
import com.itmo.client.construct.RootContainer;
import com.itmo.client.localisation.Localization;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ExecuteScriptCommandScene {
    private ExecuteScript executeScript = new ExecuteScript();
    private RootContainer rootContainer = new RootContainer();
    Font rubikLight23;
    File scriptFile;

    public void execute() throws UnsupportedEncodingException {
        rubikLight23 = Font.loadFont(getClass().getResourceAsStream("/fonts/rubikLight.ttf"), 23);

        Text chooseScriptFileText = new Text(Localization.getText("chooseScriptFileText"));
        chooseScriptFileText.setFont(rubikLight23);

        Button buttonChoose = new Button(Localization.getText("buttonChoose"));
        buttonChoose.setFont(rubikLight23);
        buttonChoose.setPrefWidth(150);
        buttonChoose.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonRun = new Button(Localization.getText("buttonRun"));
        buttonRun.setFont(rubikLight23);
        buttonRun.setPrefWidth(150);
        buttonRun.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        GridPane gridPane = new GridPane();
        gridPane.add(chooseScriptFileText, 0, 0);
        gridPane.setHalignment(chooseScriptFileText, HPos.CENTER);
        gridPane.add(buttonChoose, 0, 1);
        gridPane.setHalignment(buttonChoose, HPos.CENTER);
        gridPane.add(buttonRun, 0, 2);
        gridPane.setHalignment(buttonRun, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        Stage stageExecuteScript = new Stage();
        stageExecuteScript.setResizable(false);
        stageExecuteScript.setScene(scene);
        stageExecuteScript.show();

        buttonChoose.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            scriptFile = fileChooser.showOpenDialog(stageExecuteScript);
        });

        buttonRun.setOnAction(actionEvent -> {
            if (scriptFile != null) {
                try {
                    executeScript.executeScript(scriptFile, rootContainer.getSocket());
                    confirm(stageExecuteScript);
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void confirm(Stage stage) throws IOException, ParseException, InterruptedException {
        rubikLight23 = Font.loadFont(getClass().getResourceAsStream("/fonts/rubikLight.ttf"), 23);


        Text executionFinishedText = new Text(Localization.getText("executionFinishedText"));
        executionFinishedText.setFont(rubikLight23);


        Button buttonConfirm = new Button("Ok");
        buttonConfirm.setFont(rubikLight23);
        buttonConfirm.setPrefWidth(150);
        buttonConfirm.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки


        GridPane gridPane = new GridPane();
        gridPane.add(executionFinishedText, 0, 0);
        gridPane.setHalignment(executionFinishedText, HPos.CENTER);
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

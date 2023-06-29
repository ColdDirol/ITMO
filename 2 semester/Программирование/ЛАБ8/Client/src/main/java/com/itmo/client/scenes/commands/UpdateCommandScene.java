package com.itmo.client.scenes.commands;

import com.itmo.client.clientlogic.inputlogic.ResponseArrayList;
import com.itmo.client.construct.RootContainer;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class UpdateCommandScene {
    RootContainer rootContainer = new RootContainer();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    Stage stageFirst;


    Font rubikLight23;

    Button buttonNext;

    TextField inputTextField;


    Integer id;


    public void update(Button button) throws IOException, ParseException {
        rubikLight23 = Font.loadFont(getClass().getResourceAsStream("/fonts/rubikLight.ttf"), 23);

        Text flatNameText = new Text("Enter the ID of the flat:");
        flatNameText.setFont(rubikLight23);


        buttonNext = new Button("Continue");
        buttonNext.setFont(rubikLight23);
        buttonNext.setPrefWidth(150);
        buttonNext.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки


        inputTextField = new TextField();
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
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonNext, 0, 2);
        gridPane.setHalignment(buttonNext, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        Stage stageInsert = new Stage();
        stageInsert.setResizable(false);
        stageInsert.setScene(scene);
        stageInsert.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                id = Integer.valueOf(inputTextField.getText());
                rootContainer.sendCommandJSONArray("update " + id);
                flatName(stageInsert);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void flatName(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Enter the name of the flat:");
        flatNameText.setFont(rubikLight23);


        buttonNext = new Button("Continue");
        buttonNext.setFont(rubikLight23);
        buttonNext.setPrefWidth(150);
        buttonNext.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки


        inputTextField = new TextField();
        inputTextField.setAlignment(Pos.CENTER);
        inputTextField.setFont(rubikLight23);
//        input.setStyle("-fx-background-color: F1F0F0; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50; -fx-border-width: 1px; -fx-border-color: gray; -fx-padding: 5px;");
        inputTextField.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;");
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inputTextField.getText().length() > 20) {
                inputTextField.clear();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Максимальное количество символов - 20");
                alert.showAndWait();
            }
        });


        GridPane gridPane = new GridPane();
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonNext, 0, 2);
        gridPane.setHalignment(buttonNext, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray(inputTextField.getText());
                xCoordinate(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void xCoordinate(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Enter the X coordinate:");
        flatNameText.setFont(rubikLight23);


        inputTextField = new TextField();
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
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonNext, 0, 2);
        gridPane.setHalignment(buttonNext, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray(inputTextField.getText());
                yCoordinate(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void yCoordinate(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Enter the Y coordinate:");
        flatNameText.setFont(rubikLight23);


        inputTextField = new TextField();
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
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonNext, 0, 2);
        gridPane.setHalignment(buttonNext, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray(inputTextField.getText());
                flatArea(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void flatArea(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Enter the Area:");
        flatNameText.setFont(rubikLight23);


        inputTextField = new TextField();
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
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonNext, 0, 2);
        gridPane.setHalignment(buttonNext, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray(inputTextField.getText());
                flatNumberOfRooms(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void flatNumberOfRooms(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Enter the Number Of Rooms:");
        flatNameText.setFont(rubikLight23);


        inputTextField = new TextField();
        inputTextField.setAlignment(Pos.CENTER);
        inputTextField.setFont(rubikLight23);
//        input.setStyle("-fx-background-color: F1F0F0; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50; -fx-border-width: 1px; -fx-border-color: gray; -fx-padding: 5px;");
        inputTextField.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;");
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inputTextField.getText().length() > 20 || !isNumber(inputTextField.getText()) || !brackets(inputTextField.getText(), 0, 7)) {
                inputTextField.clear();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Максимальное количество символов - 20");
                alert.showAndWait();
            }
        });


        GridPane gridPane = new GridPane();
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonNext, 0, 2);
        gridPane.setHalignment(buttonNext, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray(inputTextField.getText());
                flatFURNISH(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void flatFURNISH(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Choose the furnish:");
        flatNameText.setFont(rubikLight23);


        Button buttonDESIGNER = new Button("DESIGNER");
        buttonDESIGNER.setFont(rubikLight23);
        buttonDESIGNER.setPrefWidth(150);
        buttonDESIGNER.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonNONE = new Button("NONE");
        buttonNONE.setFont(rubikLight23);
        buttonNONE.setPrefWidth(150);
        buttonNONE.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonFINE = new Button("FINE");
        buttonFINE.setFont(rubikLight23);
        buttonFINE.setPrefWidth(150);
        buttonFINE.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonBAD = new Button("BAD");
        buttonBAD.setFont(rubikLight23);
        buttonBAD.setPrefWidth(150);
        buttonBAD.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        HBox hBox = new HBox();
        hBox.getChildren().add(buttonDESIGNER);
        hBox.getChildren().add(buttonNONE);
        hBox.getChildren().add(buttonFINE);
        hBox.getChildren().add(buttonBAD);

        GridPane gridPane = new GridPane();
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(hBox, 0, 1);
        gridPane.setHalignment(hBox, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonDESIGNER.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("1");
                flatVIEW(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        buttonNONE.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("2");
                flatVIEW(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        buttonFINE.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("3");
                flatVIEW(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        buttonBAD.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("4");
                flatVIEW(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void flatVIEW(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Choose the view:");
        flatNameText.setFont(rubikLight23);


        Button buttonYARD = new Button("YARD");
        buttonYARD.setFont(rubikLight23);
        buttonYARD.setPrefWidth(150);
        buttonYARD.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonPARK = new Button("PARK");
        buttonPARK.setFont(rubikLight23);
        buttonPARK.setPrefWidth(150);
        buttonPARK.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonBAD = new Button("BAD");
        buttonBAD.setFont(rubikLight23);
        buttonBAD.setPrefWidth(150);
        buttonBAD.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonGOOD = new Button("GOOD");
        buttonGOOD.setFont(rubikLight23);
        buttonGOOD.setPrefWidth(150);
        buttonGOOD.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        HBox hBox = new HBox();
        hBox.getChildren().add(buttonYARD);
        hBox.getChildren().add(buttonPARK);
        hBox.getChildren().add(buttonBAD);
        hBox.getChildren().add(buttonGOOD);

        GridPane gridPane = new GridPane();
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(hBox, 0, 1);
        gridPane.setHalignment(hBox, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonYARD.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("1");
                flatTRANSPORT(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        buttonPARK.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("2");
                flatTRANSPORT(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        buttonBAD.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("3");
                flatTRANSPORT(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        buttonGOOD.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("4");
                flatTRANSPORT(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void flatTRANSPORT(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Choose the transport:");
        flatNameText.setFont(rubikLight23);


        Button buttonFEW = new Button("FEW");
        buttonFEW.setFont(rubikLight23);
        buttonFEW.setPrefWidth(150);
        buttonFEW.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonNONE = new Button("NONE");
        buttonNONE.setFont(rubikLight23);
        buttonNONE.setPrefWidth(150);
        buttonNONE.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonENOUGH = new Button("ENOUGH");
        buttonENOUGH.setFont(rubikLight23);
        buttonENOUGH.setPrefWidth(150);
        buttonENOUGH.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        HBox hBox = new HBox();
        hBox.getChildren().add(buttonFEW);
        hBox.getChildren().add(buttonNONE);
        hBox.getChildren().add(buttonENOUGH);

        GridPane gridPane = new GridPane();
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(hBox, 0, 1);
        gridPane.setHalignment(hBox, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonFEW.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("1");
                houseName(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        buttonNONE.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("2");
                houseName(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        buttonENOUGH.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray("3");
                houseName(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void houseName(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Enter the name of the house:");
        flatNameText.setFont(rubikLight23);


        buttonNext = new Button("Continue");
        buttonNext.setFont(rubikLight23);
        buttonNext.setPrefWidth(150);
        buttonNext.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки


        inputTextField = new TextField();
        inputTextField.setAlignment(Pos.CENTER);
        inputTextField.setFont(rubikLight23);
//        input.setStyle("-fx-background-color: F1F0F0; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50; -fx-border-width: 1px; -fx-border-color: gray; -fx-padding: 5px;");
        inputTextField.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;");
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inputTextField.getText().length() > 20) {
                inputTextField.clear();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Максимальное количество символов - 20");
                alert.showAndWait();
            }
        });


        GridPane gridPane = new GridPane();
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonNext, 0, 2);
        gridPane.setHalignment(buttonNext, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray(inputTextField.getText());
                houseYear(stage);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void houseYear(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Enter the year of the house:");
        flatNameText.setFont(rubikLight23);


        inputTextField = new TextField();
        inputTextField.setAlignment(Pos.CENTER);
        inputTextField.setFont(rubikLight23);
//        input.setStyle("-fx-background-color: F1F0F0; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50; -fx-border-width: 1px; -fx-border-color: gray; -fx-padding: 5px;");
        inputTextField.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;");
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inputTextField.getText().length() > 20 || !isNumber(inputTextField.getText()) || !brackets(inputTextField.getText(), 0, 874)) {
                inputTextField.clear();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Максимальное количество символов - 20");
                alert.showAndWait();
            }
        });


        GridPane gridPane = new GridPane();
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonNext, 0, 2);
        gridPane.setHalignment(buttonNext, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray(inputTextField.getText());
                houseNumberOfLifts(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void houseNumberOfLifts(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text("Enter the year of the house:");
        flatNameText.setFont(rubikLight23);


        inputTextField = new TextField();
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
        gridPane.add(flatNameText, 0, 0);
        gridPane.setHalignment(flatNameText, HPos.CENTER);
        gridPane.add(inputTextField, 0, 1);
        gridPane.setHalignment(inputTextField, HPos.CENTER);
        gridPane.add(buttonNext, 0, 2);
        gridPane.setHalignment(buttonNext, HPos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);

        BorderPane borderPane = new BorderPane(stackPane);

        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray(inputTextField.getText());
                System.out.println(rootContainer.readAsString());
                stage.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }




    public static boolean isNumber(String str) {
        try {
            int number = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean brackets(String str, int left, int right) {
        try {
            int number = Integer.parseInt(str);
            if(number >= left && number <= right) return true;
            else return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

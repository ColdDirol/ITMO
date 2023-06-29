package com.itmo.client.scenes.commands;

import com.itmo.client.clientlogic.inputlogic.ResponseArrayList;
import com.itmo.client.construct.RootContainer;
import com.itmo.client.localisation.Localization;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class InsertCommandScene {
    RootContainer rootContainer = new RootContainer();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    Stage stageFirst;


    Font rubikLight23;

    Button buttonNext;

    TextField inputTextField;

    public void flatName(Button button) throws IOException, ParseException {
        stageFirst = (Stage) button.getScene().getWindow();

        rootContainer.sendCommandJSONArray("insert 1");

        rubikLight23 = Font.loadFont(getClass().getResourceAsStream("/fonts/rubikLight.ttf"), 23);

        rootContainer.getResponseArrayListAsString();

        Text flatNameText = new Text(Localization.getText("flatNameText"));
        flatNameText.setFont(rubikLight23);


        buttonNext = new Button(Localization.getText("continueButton"));
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

        Stage stageInsert = new Stage();
        stageInsert.setResizable(false);
        stageInsert.setScene(scene);
        stageInsert.show();

        buttonNext.setOnAction(actionEvent -> {
            try {
                rootContainer.sendCommandJSONArray(inputTextField.getText());
                xCoordinate(stageInsert);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void xCoordinate(Stage stage) throws IOException, ParseException {
        rootContainer.getResponseArrayListAsString();

        Text xCoordinateText = new Text(Localization.getText("xCoordinateText"));
        xCoordinateText.setFont(rubikLight23);


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
        gridPane.add(xCoordinateText, 0, 0);
        gridPane.setHalignment(xCoordinateText, HPos.CENTER);
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

        Text yCoordinateText = new Text(Localization.getText("yCoordinateText"));
        yCoordinateText.setFont(rubikLight23);


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
        gridPane.add(yCoordinateText, 0, 0);
        gridPane.setHalignment(yCoordinateText, HPos.CENTER);
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

        Text areaText = new Text(Localization.getText("areaText"));
        areaText.setFont(rubikLight23);


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
        gridPane.add(areaText, 0, 0);
        gridPane.setHalignment(areaText, HPos.CENTER);
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

        Text flatNumberOfRoomsText = new Text(Localization.getText("flatNumberOfRoomsText"));
        flatNumberOfRoomsText.setFont(rubikLight23);


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
        gridPane.add(flatNumberOfRoomsText, 0, 0);
        gridPane.setHalignment(flatNumberOfRoomsText, HPos.CENTER);
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

        Text flatFurnishText = new Text(Localization.getText("flatFurnishText"));
        flatFurnishText.setFont(rubikLight23);


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
        gridPane.add(flatFurnishText, 0, 0);
        gridPane.setHalignment(flatFurnishText, HPos.CENTER);
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

        Text flatViewText = new Text(Localization.getText("flatViewText"));
        flatViewText.setFont(rubikLight23);


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
        gridPane.add(flatViewText, 0, 0);
        gridPane.setHalignment(flatViewText, HPos.CENTER);
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

        Text flatTransportText = new Text(Localization.getText("flatTransportText"));
        flatTransportText.setFont(rubikLight23);


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
        gridPane.add(flatTransportText, 0, 0);
        gridPane.setHalignment(flatTransportText, HPos.CENTER);
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

        Text houseNameText = new Text(Localization.getText("houseNameText"));
        houseNameText.setFont(rubikLight23);


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
        gridPane.add(houseNameText, 0, 0);
        gridPane.setHalignment(houseNameText, HPos.CENTER);
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

        Text houseYearText = new Text(Localization.getText("houseYearText"));
        houseYearText.setFont(rubikLight23);


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
        gridPane.add(houseYearText, 0, 0);
        gridPane.setHalignment(houseYearText, HPos.CENTER);
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

        Text houseNumberOfLiftsText = new Text(Localization.getText("houseNumberOfLiftsText"));
        houseNumberOfLiftsText.setFont(rubikLight23);


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
        gridPane.add(houseNumberOfLiftsText, 0, 0);
        gridPane.setHalignment(houseNumberOfLiftsText, HPos.CENTER);
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

package com.itmo.client.scenes.work;

import com.itmo.client.clientlogic.inputlogic.ObservableListContainer;
import com.itmo.client.clientlogic.outputlogic.CredentialsMapEntry;
import com.itmo.client.construct.RootContainer;
import com.itmo.client.localisation.Localization;
import com.itmo.client.scenes.commands.CommandsScene;
import com.itmo.client.scenes.commands.InsertCommandScene;
import com.itmo.client.scenes.commands.RemoveCommandScene;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WorkTableScene {
    RootContainer rootContainer = new RootContainer();
    CredentialsMapEntry credentialsMapEntry = new CredentialsMapEntry();


    CommandsScene commandsScene = new CommandsScene();
    InsertCommandScene insertCommandScene = new InsertCommandScene();
    RemoveCommandScene removeCommandScene = new RemoveCommandScene();


    WorkMapScene workMapScene = new WorkMapScene();

    Font webServeroff43;
    Font lato18;
    Font lato30;
    Font latoBold30;

    Text textLogo;

//    private ObservableList<String[]> data = FXCollections.observableArrayList(
//            new String[]{"1", "2", "Flat 1", "10", "20", "2023-06-25 18:13:54", "50", "2", "NONE", "STREET", "CAR", "House 1", "2020", "2"},
//            new String[]{"2", "3", "Flat 2", "20", "30", "2023-06-26 08:05:23", "75", "3", "FULL", "PARK", "BUS", "House 2", "2015", "1"}
//    );
    ObservableListContainer observableListContainer = new ObservableListContainer();

    public void showScene(Stage stage) throws IOException, ParseException, InterruptedException {
        Button rusLanguageButton = new Button();
        ImageView imageRusFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/rus.jpg")));
        imageRusFlag.setPreserveRatio(false);
        imageRusFlag.setFitWidth(16);
        imageRusFlag.setFitHeight(10);
        rusLanguageButton.setGraphic(imageRusFlag);
        rusLanguageButton.setPrefSize(16, 10);

        Button engLanguageButton = new Button();
        ImageView imageEngFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/eng.png")));
        imageEngFlag.setFitWidth(16);
        imageEngFlag.setFitHeight(10);
        engLanguageButton.setGraphic(imageEngFlag);
        engLanguageButton.setPrefSize(16, 10);

        Button estLanguageButton = new Button();
        ImageView imageEstFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/est.jpg")));
        imageEstFlag.setFitWidth(16);
        imageEstFlag.setFitHeight(10);
        estLanguageButton.setGraphic(imageEstFlag);
        estLanguageButton.setPrefSize(16, 10);

        Button porLanguageButton = new Button();
        ImageView imagePorFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/por.jpg")));
        imagePorFlag.setFitWidth(16);
        imagePorFlag.setFitHeight(10);
        porLanguageButton.setGraphic(imagePorFlag);
        porLanguageButton.setPrefSize(16, 10);

        Button sweLanguageButton = new Button();
        ImageView imageSweFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/swe.jpg")));
        imageSweFlag.setFitWidth(16);
        imageSweFlag.setFitHeight(10);
        sweLanguageButton.setGraphic(imageSweFlag);
        sweLanguageButton.setPrefSize(16, 10);

        HBox hBoxFlags = new HBox();
        hBoxFlags.setPadding(new Insets(0, 0, 0, 70));
        hBoxFlags.getChildren().addAll(rusLanguageButton, engLanguageButton, estLanguageButton, porLanguageButton, sweLanguageButton);
        hBoxFlags.setPrefSize(90, 20);


        webServeroff43 = Font.loadFont(getClass().getResourceAsStream("/fonts/WebServeroff.ttf"), 35);
        lato18 = Font.loadFont(getClass().getResourceAsStream("/fonts/Lato.ttf"), 18);
        lato30 = Font.loadFont(getClass().getResourceAsStream("/fonts/Lato.ttf"), 30);
        latoBold30 = Font.loadFont(getClass().getResourceAsStream("/fonts/LatoBold.ttf"), 30);

        Rectangle menuRectangle = new Rectangle();
        menuRectangle.setWidth(200);
        menuRectangle.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        menuRectangle.setStyle("-fx-fill: #7092BC");

        // logo <
        textLogo = new Text("ELLA REALTY");
        textLogo.setFont(webServeroff43);
        // >


        // buttons <
        Button buttonToMap = new Button(Localization.getText("buttonToMap"));
        buttonToMap.setFont(lato18);
        buttonToMap.setPrefWidth(150);
        buttonToMap.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonToCommands = new Button(Localization.getText("buttonToCommands"));
        buttonToCommands.setFont(lato18);
        buttonToCommands.setPrefWidth(150);
        buttonToCommands.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonToAdd = new Button(Localization.getText("buttonToAdd"));
        buttonToAdd.setFont(lato18);
        buttonToAdd.setPrefWidth(150);
        buttonToAdd.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonToRemove = new Button(Localization.getText("buttonToRemove"));
        buttonToRemove.setFont(lato18);
        buttonToRemove.setPrefWidth(150);
        buttonToRemove.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

        Button buttonToExit = new Button(Localization.getText("exit"));
        buttonToExit.setFont(lato18);
        buttonToExit.setPrefWidth(150);
        buttonToExit.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки
        // >


        // work zone shapes <
        TextFlow textDateFlow = new TextFlow();
        Text textDateTitle = new Text("  DATE                        ");
        textDateTitle.setFont(latoBold30);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = currentDate.format(formatter);
        Text textDateInfo = new Text(formattedDate);
        textDateInfo.setFont(lato30);
        textDateFlow.getChildren().add(textDateTitle);
        textDateFlow.getChildren().add(textDateInfo);
        textDateFlow.getChildren().add(hBoxFlags);

        TextFlow textUsernameFlow = new TextFlow();
        Text textUsernameTitle = new Text("  USERNAME            ");
        textUsernameTitle.setFont(latoBold30);
        Text textUsernameInfo = new Text(credentialsMapEntry.getUsername());
        textUsernameInfo.setFont(lato30);
        textUsernameFlow.getChildren().add(textUsernameTitle);
        textUsernameFlow.getChildren().add(textUsernameInfo);

        VBox textVBox = new VBox();
        textVBox.setMaxWidth(700);
        textVBox.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());
        textVBox.getChildren().add(textDateFlow);
        textVBox.getChildren().add(textUsernameFlow);




        TableView<String[]> table = new TableView<>();
        table.setItems(observableListContainer.getData());

        TableColumn<String[], String> idCol = new TableColumn<>("id");
        idCol.setStyle("-fx-background-color: #E3E8FF; -fx-border-color: #EEEEEE;");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));

        TableColumn<String[], String> userIdCol = new TableColumn<>("user_id");
        userIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));

        TableColumn<String[], String> nameCol = new TableColumn<>("name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));

        TableColumn<String[], String> xCol = new TableColumn<>("x");
        xCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));

        TableColumn<String[], String> yCol = new TableColumn<>("y");
        yCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));

        TableColumn<String[], String> creationCol = new TableColumn<>("creation");
        creationCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5]));

        TableColumn<String[], String> areaCol = new TableColumn<>("area");
        areaCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[6]));

        TableColumn<String[], String> numberOfRoomsCol = new TableColumn<>("numberofrooms");
        numberOfRoomsCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[7]));

        TableColumn<String[], String> furnishCol = new TableColumn<>("furnish");
        furnishCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[8]));

        TableColumn<String[], String> viewCol = new TableColumn<>("view");
        viewCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[9]));

        TableColumn<String[], String> transportCol = new TableColumn<>("transport");
        transportCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[10]));

        TableColumn<String[], String> houseNameCol = new TableColumn<>("house_name");
        houseNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[11]));

        TableColumn<String[], String> houseYearCol = new TableColumn<>("house_year");
        houseYearCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[12]));

        TableColumn<String[], String> numberOfLiftsCol = new TableColumn<>("house_numberoflifts");
        numberOfLiftsCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[13]));

        table.getColumns().addAll(idCol, userIdCol, nameCol, xCol, yCol, creationCol, areaCol, numberOfRoomsCol,
                furnishCol, viewCol, transportCol, houseNameCol, houseYearCol, numberOfLiftsCol);

        ScrollPane scrollTablePane = new ScrollPane(table);
        scrollTablePane.setPrefHeight(690 - textVBox.getHeight());
        scrollTablePane.setFitToHeight(true);
        scrollTablePane.setFitToWidth(true);

        StackPane tableStackPane = new StackPane(scrollTablePane);
        // >







        // menu Panes <
        GridPane menuGridPane = new GridPane();
        menuGridPane.setMaxWidth(150);
        menuGridPane.add(textLogo, 0, 0);
        menuGridPane.setHalignment(textLogo, HPos.CENTER);
        menuGridPane.setMargin(textLogo, new Insets(10, 0, 0, 0)); //отступ
        menuGridPane.add(buttonToMap, 0, 1);
        menuGridPane.setHalignment(buttonToMap, HPos.CENTER);
        menuGridPane.setMargin(buttonToMap, new Insets(20, 0, 0, 0)); //отступ
        menuGridPane.add(buttonToCommands, 0, 2);
        menuGridPane.setHalignment(buttonToCommands, HPos.CENTER);
        menuGridPane.setMargin(buttonToCommands, new Insets(100, 0, 0, 0)); //отступ
        menuGridPane.add(buttonToAdd, 0, 3);
        menuGridPane.setHalignment(buttonToAdd, HPos.CENTER);
        menuGridPane.setMargin(buttonToAdd, new Insets(10, 0, 0, 0)); //отступ
        menuGridPane.add(buttonToRemove, 0, 4);
        menuGridPane.setHalignment(buttonToRemove, HPos.CENTER);
        menuGridPane.setMargin(buttonToRemove, new Insets(10, 0, 0, 0)); //отступ
        menuGridPane.add(buttonToExit, 0, 5);
        menuGridPane.setHalignment(buttonToExit, HPos.CENTER);
        menuGridPane.setMargin(buttonToExit, new Insets(100, 0, 0, 0)); //отступ

        StackPane menuStackPane = new StackPane();
        menuStackPane.getChildren().add(menuGridPane);

        StackPane menuZoneStackPane = new StackPane();
        menuZoneStackPane.getChildren().add(menuRectangle);
        menuZoneStackPane.getChildren().add(menuStackPane);

        VBox dateNtableVBox = new VBox();
        dateNtableVBox.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());
        dateNtableVBox.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() - menuRectangle.getWidth());
        dateNtableVBox.setPadding(new Insets(10, 0, 0, 0));
        dateNtableVBox.getChildren().add(textVBox);
        dateNtableVBox.setSpacing(20);
        dateNtableVBox.getChildren().add(tableStackPane);

//
        HBox menuHBox = new HBox();
        menuHBox.getChildren().add(menuZoneStackPane);
//        menuHBox.getChildren().add(tableStackPane);
//        menuHBox.setSpacing(15);
//        menuHBox.getChildren().add(textVBox);
        menuHBox.getChildren().add(dateNtableVBox);
        // >;



//        HBox workZoneHBox = new HBox();
//        workZoneHBox.getChildren().add(menuHBox);
//        workZoneHBox.getChildren().add(tableStackPane);



        Scene workTableScene = new Scene(menuHBox, 1280, 720);
        workTableScene.widthProperty().addListener((observableValue, oldWidth, newWidth) -> {
            scrollTablePane.setPrefWidth(newWidth.doubleValue());
        });

        workTableScene.heightProperty().addListener((observableValue, oldHeight, newHeight) -> {
            scrollTablePane.setPrefHeight(newHeight.doubleValue());
        });

        stage.setScene(workTableScene);
        stage.show();



        rusLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("ru");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToMap.setText(Localization.getText("buttonToMap"));
                buttonToCommands.setText(Localization.getText("buttonToCommands"));
                buttonToAdd.setText(Localization.getText("buttonToAdd"));
                buttonToRemove.setText(Localization.getText("buttonToRemove"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        engLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("en");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToMap.setText(Localization.getText("buttonToMap"));
                buttonToCommands.setText(Localization.getText("buttonToCommands"));
                buttonToAdd.setText(Localization.getText("buttonToAdd"));
                buttonToRemove.setText(Localization.getText("buttonToRemove"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        estLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("est");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToMap.setText(Localization.getText("buttonToMap"));
                buttonToCommands.setText(Localization.getText("buttonToCommands"));
                buttonToAdd.setText(Localization.getText("buttonToAdd"));
                buttonToRemove.setText(Localization.getText("buttonToRemove"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        porLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("por");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToMap.setText(Localization.getText("buttonToMap"));
                buttonToCommands.setText(Localization.getText("buttonToCommands"));
                buttonToAdd.setText(Localization.getText("buttonToAdd"));
                buttonToRemove.setText(Localization.getText("buttonToRemove"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        sweLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("swe");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToMap.setText(Localization.getText("buttonToMap"));
                buttonToCommands.setText(Localization.getText("buttonToCommands"));
                buttonToAdd.setText(Localization.getText("buttonToAdd"));
                buttonToRemove.setText(Localization.getText("buttonToRemove"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });



        buttonToMap.setOnAction(actionEvent -> {
            System.out.println("to Map");
            try {
                workMapScene.showScene((Stage) buttonToMap.getScene().getWindow());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        buttonToCommands.setOnAction(actionEvent -> {
            System.out.println("to Commands");
            try {
                commandsScene.showScene();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        buttonToAdd.setOnAction(actionEvent -> {
            System.out.println("to Add");
            try {
                insertCommandScene.flatName(buttonToAdd);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        buttonToRemove.setOnAction(actionEvent -> {
            System.out.println("to Remove");
            try {
                removeCommandScene.remove();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        buttonToExit.setOnAction(actionEvent -> {
            System.out.println("to Exit");
            System.exit(0);
        });




        // поток для обновлений
        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000); //10 sec
                    if(!commandsScene.isCommandShowing()) {
                        System.out.println("Обновлено");
                        table.setItems(observableListContainer.getData());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (NullPointerException e) {
                    try {
                        Thread.sleep(30000); //10 sec
                        System.out.println("Не обновлено");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        updateThread.start();
    }
}

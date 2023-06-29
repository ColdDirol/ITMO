package com.itmo.client.scenes.work;

import com.itmo.client.clientlogic.inputlogic.CollectionFromServer;
import com.itmo.client.clientlogic.inputlogic.ObservableListContainer;
import com.itmo.client.clientlogic.outputlogic.CredentialsMapEntry;
import com.itmo.client.construct.RootContainer;
import com.itmo.client.localisation.Localization;
import com.itmo.client.scenes.commands.CommandsScene;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static javafx.application.Application.launch;

public class WorkMapScene {
    RootContainer rootContainer = new RootContainer();
    CredentialsMapEntry credentialsMapEntry = new CredentialsMapEntry();
    ObservableListContainer observableListContainer = new ObservableListContainer();
    MapHelper mapHelper;
    CommandsScene commandsScene = new CommandsScene();

    Font webServeroff43;
    Font lato18;
    Font lato30;
    Font latoBold30;

    Text textLogo;
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



        mapHelper = new MapHelper();

        RootContainer rootContainer = new RootContainer();

        WorkTableScene workTableScene = new WorkTableScene();

        webServeroff43 = Font.loadFont(getClass().getResourceAsStream("/fonts/WebServeroff.ttf"), 35);
        lato18 = Font.loadFont(getClass().getResourceAsStream("/fonts/Lato.ttf"), 18);
        lato30 = Font.loadFont(getClass().getResourceAsStream("/fonts/Lato.ttf"), 30);
        latoBold30 = Font.loadFont(getClass().getResourceAsStream("/fonts/LatoBold.ttf"), 30);


        ImageView backgroundMap = new ImageView(new Image(getClass().getResource("/images/map.png").toString()));
        backgroundMap.setPreserveRatio(true);
        backgroundMap.fitWidthProperty().bind(stage.widthProperty());
        backgroundMap.setFitHeight(backgroundMap.getFitWidth() / backgroundMap.getImage().getWidth() * backgroundMap.getImage().getHeight());

        Canvas canvas = new Canvas();
        canvas.setWidth(backgroundMap.getFitWidth());
        canvas.setHeight(backgroundMap.getFitHeight());

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//        printDots(graphicsContext);



        StackPane coordinatesStackPane = new StackPane();
        coordinatesStackPane.getChildren().add(backgroundMap);
        coordinatesStackPane.getChildren().add(canvas);
        coordinatesStackPane.prefWidthProperty().bind(stage.widthProperty());
        coordinatesStackPane.prefHeightProperty().bind(stage.heightProperty());


        Rectangle menuRectangle = new Rectangle();
        menuRectangle.setWidth(200);
        menuRectangle.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        menuRectangle.setStyle("-fx-fill: #7092BC");

        // logo <
        textLogo = new Text("ELLA REALTY");
        textLogo.setFont(webServeroff43);
        // >


        // buttons <
        Button buttonToTable = new Button(Localization.getText("buttonToTable"));
        buttonToTable.setFont(lato18);
        buttonToTable.setPrefWidth(150);
        buttonToTable.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()));
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
        // >







        // menu Panes <
        GridPane menuGridPane = new GridPane();
        menuGridPane.setMaxWidth(150);
        menuGridPane.add(textLogo, 0, 0);
        menuGridPane.setHalignment(textLogo, HPos.CENTER);
        menuGridPane.setMargin(textLogo, new Insets(10, 0, 0, 0)); //отступ
        menuGridPane.add(buttonToTable, 0, 1);
        menuGridPane.setHalignment(buttonToTable, HPos.CENTER);
        menuGridPane.setMargin(buttonToTable, new Insets(20, 0, 0, 0)); //отступ
//        menuGridPane.add(buttonToCommands, 0, 2);
//        menuGridPane.setHalignment(buttonToCommands, HPos.CENTER);
//        menuGridPane.setMargin(buttonToCommands, new Insets(100, 0, 0, 0)); //отступ
//        menuGridPane.add(buttonToAdd, 0, 3);
//        menuGridPane.setHalignment(buttonToAdd, HPos.CENTER);
//        menuGridPane.setMargin(buttonToAdd, new Insets(10, 0, 0, 0)); //отступ
//        menuGridPane.add(buttonToRemove, 0, 4);
//        menuGridPane.setHalignment(buttonToRemove, HPos.CENTER);
//        menuGridPane.setMargin(buttonToRemove, new Insets(10, 0, 0, 0)); //отступ
        menuGridPane.add(buttonToExit, 0, 1);
        menuGridPane.setHalignment(buttonToExit, HPos.CENTER);
        menuGridPane.setMargin(buttonToExit, new Insets(400, 0, 0, 0)); //отступ

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
        dateNtableVBox.getChildren().add(coordinatesStackPane);

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

        rusLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("ru");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToTable.setText(Localization.getText("buttonToTable"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        engLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("en");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToTable.setText(Localization.getText("buttonToTable"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        estLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("est");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToTable.setText(Localization.getText("buttonToTable"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        porLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("por");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToTable.setText(Localization.getText("buttonToTable"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        sweLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("swe");
            try {
                textDateInfo.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", new Locale(Locale.getDefault().getLanguage()))));
                buttonToTable.setText(Localization.getText("buttonToTable"));
                buttonToExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });



        buttonToTable.setOnAction(actionEvent -> {
            System.out.println("to Map");
            try {
                workTableScene.showScene((Stage) buttonToTable.getScene().getWindow());
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

        });
        buttonToRemove.setOnAction(actionEvent -> {
            System.out.println("to Remove");

        });
        buttonToExit.setOnAction(actionEvent -> {
            System.out.println("to Exit");
            System.exit(0);
        });


        Scene workMapScene = new Scene(menuHBox, 1280, 720);

        stage.setScene(workMapScene);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
            int i = 1;
            String[] strings;
            @Override
            public void handle(ActionEvent event) {
                strings = CollectionFromServer.getCollectionArrayList().get(i);
                System.out.println(strings);
                if(!mapHelper.coordinatesContains(strings[3], strings[4])) {
                    Color color = MapHelper.getUsersColorHashtable().get(Integer.parseInt(strings[1]));
                    graphicsContext.setFill(color);
                    graphicsContext.fillOval(Double.parseDouble(strings[3]), Double.parseDouble(strings[4]), 10, 10);
                    i = 0;
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000); //10 sec
                    System.out.println("Карта обновлена");
                    printDots(graphicsContext);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (NullPointerException e) {
                    System.out.println("Карта не обновлена");
                }
            }
        });

        updateThread.start();
    }



    public void printDots(GraphicsContext graphicsContext) {
        // запускаем анимацию
        AnimationTimer timer = new AnimationTimer() {
            private int index = 0; // индекс текущей точки
            private long lastTime = 0; // время последней анимации
            @Override
            public void handle(long now) {
                try {
                    ObservableListContainer.updateObservableList("1");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // проверяем, прошла ли достаточная задержка для анимации очередной точки
                if (now - lastTime >= 300 * 1_000_000) {
                    // проверяем, что есть еще точки
                    if (index < CollectionFromServer.getCollectionArrayList().size()) {
                        // получаем данные о текущей точке
                        String[] point = CollectionFromServer.getCollectionArrayList().get(index);

                        // получаем цвет в зависимости от user_id
                        Color color = mapHelper.getColor(Integer.valueOf(point[1]));

                        // рисуем точку на холсте
                        graphicsContext.setFill(color);
                        graphicsContext.fillOval(Double.parseDouble(point[3]), Double.parseDouble(point[4]), 10, 10);

                        // инкрементируем индекс текущей точки и обновляем время последней анимации
                        index++;
                        lastTime = now;
                    } else {
                        // все точки отрисованы, останавливаем анимацию
                        try {
                            ObservableListContainer.updateObservableList("1");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        this.stop();
                    }
                }
            }
        };
        timer.start();
        System.out.println("Stop");
    }



//    private void printDots(GraphicsContext graphicsContext) throws IOException, ParseException, InterruptedException {
//        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
//        ObservableListContainer.updateObservableList("1");
//        for (String[] record : CollectionFromServer.getCollectionArrayList()) {
//            Color color = MapHelper.getUsersColorHashtable().get(Integer.parseInt(record[1]));
//            if(!mapHelper.coordinatesContains(record[3], record[4])) {
//                graphicsContext.setFill(color);
//                graphicsContext.fillOval(Double.parseDouble(record[3]), Double.parseDouble(record[4]), 10, 10);
//                Thread.sleep(1000);
//            }
//        }
//    }




    // метод для получения цвета в зависимости от user_id
    private Color getColor(String userId) {
        int hash = userId.hashCode(); // вычисляем хэш-код
        int red = (hash & 0xFF0000) >> 16; // красный цвет - первые 2 байта хэш-кода
        int green = (hash & 0xFF00) >> 8; // зеленый цвет - следующие 2 байта
        int blue = hash & 0xFF; // синий цвет - последние 2 байта
        return Color.rgb(red, green, blue);
    }
}



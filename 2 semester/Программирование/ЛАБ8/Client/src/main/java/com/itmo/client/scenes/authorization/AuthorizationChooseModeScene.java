package com.itmo.client.scenes.authorization;

import com.itmo.client.localisation.Localization;
import com.itmo.client.clientlogic.inputlogic.ResponseArrayList;
import com.itmo.client.clientlogic.outputlogic.CredentialsMapEntry;
import com.itmo.client.construct.RootContainer;
import com.itmo.client.scenes.work.WorkTableScene;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AuthorizationChooseModeScene {
    RootContainer rootContainer = new RootContainer();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    CredentialsMapEntry credentialsMapEntry;
    String username;
    String password;


    Font webServeroff43;
    Font rubikLight23;

    StackPane backgroundStackPane = new StackPane();

    Text textLogo;

    WorkTableScene workTableScene = new WorkTableScene();

    Button rusLanguageButton;
    Button engLanguageButton;
    Button estLanguageButton;
    Button porLanguageButton;
    Button sweLanguageButton;

    public void showScene(Stage stage) throws IOException, InterruptedException {
        rusLanguageButton = new Button();
        ImageView imageRusFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/rus.jpg")));
        imageRusFlag.setPreserveRatio(false);
        imageRusFlag.setFitWidth(16);
        imageRusFlag.setFitHeight(10);
        rusLanguageButton.setGraphic(imageRusFlag);
        rusLanguageButton.setPrefSize(16, 10);

        engLanguageButton = new Button();
        ImageView imageEngFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/eng.png")));
        imageEngFlag.setFitWidth(16);
        imageEngFlag.setFitHeight(10);
        engLanguageButton.setGraphic(imageEngFlag);
        engLanguageButton.setPrefSize(16, 10);

        estLanguageButton = new Button();
        ImageView imageEstFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/est.jpg")));
        imageEstFlag.setFitWidth(16);
        imageEstFlag.setFitHeight(10);
        estLanguageButton.setGraphic(imageEstFlag);
        estLanguageButton.setPrefSize(16, 10);

        porLanguageButton = new Button();
        ImageView imagePorFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/por.jpg")));
        imagePorFlag.setFitWidth(16);
        imagePorFlag.setFitHeight(10);
        porLanguageButton.setGraphic(imagePorFlag);
        porLanguageButton.setPrefSize(16, 10);

        sweLanguageButton = new Button();
        ImageView imageSweFlag = new ImageView(new Image(getClass().getResourceAsStream("/images/flags/swe.jpg")));
        imageSweFlag.setFitWidth(16);
        imageSweFlag.setFitHeight(10);
        sweLanguageButton.setGraphic(imageSweFlag);
        sweLanguageButton.setPrefSize(16, 10);

        HBox hBoxFlags = new HBox();
        hBoxFlags.setPadding(new Insets(0, 0, 0, 10));
        hBoxFlags.getChildren().addAll(rusLanguageButton, engLanguageButton, estLanguageButton, porLanguageButton, sweLanguageButton);
        hBoxFlags.setPrefSize(100, 20);


        responseArrayList.setArrayList(rootContainer.readAsArrayList());
        System.out.println(responseArrayList.getArrayList());

        webServeroff43 = Font.loadFont(getClass().getResourceAsStream("/fonts/WebServeroff.ttf"), 100);
        rubikLight23 = Font.loadFont(getClass().getResourceAsStream("/fonts/rubikLight.ttf"), 23);

        ImageView backgroundBlur = new ImageView(new Image(getClass().getResource("/images/bg.png").toString()));
        backgroundBlur.setPreserveRatio(true);
        backgroundBlur.fitWidthProperty().bind(stage.widthProperty());
        backgroundBlur.setFitHeight(backgroundBlur.getFitWidth() / backgroundBlur.getImage().getWidth() * backgroundBlur.getImage().getHeight());



        StackPane stackPaneRoot = new StackPane();
        GridPane authorizationTextGridPane = new GridPane(); // для сообщения об авторизации
        authorizationTextGridPane.setMaxSize(100, 100);

        // квадрат со скругленными углами
        Rectangle roundedRectangle = new Rectangle();
        roundedRectangle.setWidth(660);
        roundedRectangle.setHeight(340);
        roundedRectangle.setArcWidth(20);
        roundedRectangle.setArcHeight(20);
        roundedRectangle.setFill(Color.WHITE);

        // logo <
        textLogo = new Text("ELLA REALTY");
        textLogo.setFont(webServeroff43);
        // >

        // Authorization (1 MODE) button
        Button buttonAuthorization = new Button(Localization.getText("authorization"));
        //Button buttonAuthorization = new Button("Авторизация");
        buttonAuthorization.setFont(rubikLight23);
        buttonAuthorization.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); //прозрачность фона кнопки

        // Registration (2 MODE) button
        Button buttonRegistration = new Button(Localization.getText("registration"));
//        Button buttonRegistration = new Button("Регистрация");
        buttonRegistration.setFont(rubikLight23);
        buttonRegistration.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); //прозрачность фона кнопки

        // Exit (3 MODE) button
        Button buttonExit = new Button(Localization.getText("exit"));
//        Button buttonExit = new Button("Выход");
        buttonExit.setFont(rubikLight23);
        buttonExit.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); //прозрачность фона кнопки

        responseArrayList.clearRefuseString();

        //---

        authorizationTextGridPane.add(textLogo, 0, 0);
        authorizationTextGridPane.setHalignment(textLogo, HPos.CENTER);
        authorizationTextGridPane.add(buttonAuthorization, 0, 1);
        authorizationTextGridPane.setHalignment(buttonAuthorization, HPos.CENTER);
        authorizationTextGridPane.setMargin(buttonAuthorization, new Insets(40, 0, 0, 0)); //отступ
        authorizationTextGridPane.add(buttonRegistration, 0, 2);
        authorizationTextGridPane.setHalignment(buttonRegistration, HPos.CENTER);
        authorizationTextGridPane.add(buttonExit, 0, 3);
        authorizationTextGridPane.setHalignment(buttonExit, HPos.CENTER);



        backgroundStackPane.getChildren().add(backgroundBlur);
        backgroundStackPane.getChildren().add(roundedRectangle);
        backgroundStackPane.getChildren().add(hBoxFlags);



        rusLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("ru");
            try {
                buttonAuthorization.setText(Localization.getText("authorization"));
                buttonRegistration.setText(Localization.getText("registration"));
                buttonExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        engLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("en");
            try {
                buttonAuthorization.setText(Localization.getText("authorization"));
                buttonRegistration.setText(Localization.getText("registration"));
                buttonExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        estLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("est");
            try {
                buttonAuthorization.setText(Localization.getText("authorization"));
                buttonRegistration.setText(Localization.getText("registration"));
                buttonExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        porLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("por");
            try {
                buttonAuthorization.setText(Localization.getText("authorization"));
                buttonRegistration.setText(Localization.getText("registration"));
                buttonExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        sweLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("swe");
            try {
                buttonAuthorization.setText(Localization.getText("authorization"));
                buttonRegistration.setText(Localization.getText("registration"));
                buttonExit.setText(Localization.getText("exit"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });



        buttonAuthorization.setOnAction(actionEvent -> {
            System.out.println("Authorization");
            try {
                rootContainer.writeString("1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                showUsernameInput(buttonAuthorization);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        buttonRegistration.setOnAction(actionEvent -> {
            System.out.println("Registration");
            try {
                rootContainer.writeString("2");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                showUsernameInput(buttonRegistration);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        buttonExit.setOnAction(actionEvent -> {
            System.out.println("Exit");
            System.exit(0);
        });

        stackPaneRoot.getChildren().add(backgroundStackPane);
        stackPaneRoot.getChildren().add(authorizationTextGridPane);


        Scene authorizationChooseModeScene = new Scene(stackPaneRoot, 1280, 720);

        stage.setScene(authorizationChooseModeScene);
        stage.show();

        //stage.setScene(authorizationChooseModeScene);
    }

    public void showUsernameInput(Button button) throws IOException, InterruptedException {
        responseArrayList.addElementToTheRefuseString(rootContainer.readAsString());
        System.out.println(responseArrayList.getArrayList());

        Stage stage = (Stage) button.getScene().getWindow();

        GridPane textGridPane = new GridPane();
        textGridPane.setMaxSize(100, 100);

        Text usernameText = new Text(Localization.getText("usernameRequest"));
        responseArrayList.clearRefuseString();
        usernameText.setFont(rubikLight23);

        TextField inputTextField = new TextField();
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

        Button buttonNext = new Button(Localization.getText("continueButton"));
        buttonNext.setFont(rubikLight23);
        buttonNext.setMaxSize(inputTextField.getMaxWidth(), inputTextField.getHeight());
        buttonNext.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки


        textGridPane.add(textLogo, 0, 0);
        textGridPane.setHalignment(textLogo, HPos.CENTER);
        textGridPane.add(usernameText, 0, 1);
        textGridPane.setHalignment(usernameText, HPos.CENTER);
        textGridPane.setMargin(usernameText, new Insets(37, 0, 0, 0)); //отступ
        textGridPane.add(inputTextField, 0, 2);
        textGridPane.setHalignment(inputTextField, HPos.CENTER);
        textGridPane.setMargin(inputTextField, new Insets(10, 0, 0, 0)); //отступ
        textGridPane.add(buttonNext, 0, 3);
        textGridPane.setHalignment(buttonNext, HPos.CENTER);
        textGridPane.setMargin(buttonNext, new Insets(10, 0, 0, 0)); //отступ


        buttonNext.setOnAction(actionEvent -> {
            try {
                username = inputTextField.getText();
                rootContainer.writeString(username);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                showPasswordInput(buttonNext);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });



        rusLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("ru");
            try {
                usernameText.setText(Localization.getText("usernameRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        engLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("en");
            try {
                usernameText.setText(Localization.getText("usernameRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        estLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("est");
            try {
                usernameText.setText(Localization.getText("usernameRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        porLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("por");
            try {
                usernameText.setText(Localization.getText("usernameRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        sweLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("swe");
            try {
                usernameText.setText(Localization.getText("usernameRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });



        StackPane stackPaneRoot = new StackPane();
        stackPaneRoot.getChildren().add(backgroundStackPane);
        stackPaneRoot.getChildren().add(textGridPane);




        Scene nextScene = new Scene(stackPaneRoot, 1280, 720);
        stage.setScene(nextScene);
        stage.show();
    }

    public void showPasswordInput(Button button) throws IOException, InterruptedException {
        responseArrayList.addElementToTheRefuseString(rootContainer.readAsString());
        System.out.println(responseArrayList.getArrayList());

        Stage stage = (Stage) button.getScene().getWindow();

        GridPane textGridPane = new GridPane();
        textGridPane.setMaxSize(100, 100);

        Text passwordRequest = new Text(Localization.getText("passwordRequest"));
        responseArrayList.clearRefuseString();
        passwordRequest.setFont(rubikLight23);

        TextField inputTextField = new TextField();
        inputTextField.setAlignment(Pos.CENTER);
        inputTextField.setFont(rubikLight23);
//        input.setStyle("-fx-background-color: F1F0F0; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50; -fx-border-width: 1px; -fx-border-color: gray; -fx-padding: 5px;");
        inputTextField.setStyle("-fx-background-color: F1F0F0; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;");
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inputTextField.getText().length() > 8) {
                inputTextField.clear();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Максимальное количество символов - 8");
                alert.showAndWait();
            }
        });

        Button buttonNext = new Button(Localization.getText("continueButton"));
        buttonNext.setFont(rubikLight23);
        buttonNext.setMaxSize(inputTextField.getMaxWidth(), inputTextField.getHeight());
        buttonNext.setStyle("-fx-background-color: #CDD6FF; -fx-background-radius: 50 50 50 50; -fx-border-color: transparent;"); //прозрачность фона кнопки


        textGridPane.add(textLogo, 0, 0);
        textGridPane.setHalignment(textLogo, HPos.CENTER);
        textGridPane.add(passwordRequest, 0, 1);
        textGridPane.setHalignment(passwordRequest, HPos.CENTER);
        textGridPane.setMargin(passwordRequest, new Insets(37, 0, 0, 0)); //отступ
        textGridPane.add(inputTextField, 0, 2);
        textGridPane.setHalignment(inputTextField, HPos.CENTER);
        textGridPane.setMargin(inputTextField, new Insets(10, 0, 0, 0)); //отступ
        textGridPane.add(buttonNext, 0, 3);
        textGridPane.setHalignment(buttonNext, HPos.CENTER);
        textGridPane.setMargin(buttonNext, new Insets(10, 0, 0, 0)); //отступ


        rusLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("ru");
            try {
                passwordRequest.setText(Localization.getText("passwordRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        engLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("en");
            try {
                passwordRequest.setText(Localization.getText("passwordRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        estLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("est");
            try {
                passwordRequest.setText(Localization.getText("passwordRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        porLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("por");
            try {
                passwordRequest.setText(Localization.getText("passwordRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        sweLanguageButton.setOnAction(actionEvent -> {
            Localization.setLanguage("swe");
            try {
                passwordRequest.setText(Localization.getText("passwordRequest"));
                buttonNext.setText(Localization.getText("continueButton"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });


        buttonNext.setOnAction(actionEvent -> {
            try {
                password = inputTextField.getText();
                rootContainer.writeString(password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                checkServerResponse(buttonNext);
            } catch (IOException e) {
                e.getMessage();
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                e.getMessage();
                throw new RuntimeException(e);
            } catch (ParseException e) {
                e.getMessage();
                throw new RuntimeException(e);
            }
        });




        StackPane stackPaneRoot = new StackPane();
        stackPaneRoot.getChildren().add(backgroundStackPane);
        stackPaneRoot.getChildren().add(textGridPane);




        Scene nextScene = new Scene(stackPaneRoot, 1280, 720);
        stage.setScene(nextScene);
        stage.show();
    }

    public void checkServerResponse(Button button) throws IOException, InterruptedException, ParseException {
        boolean isContinue = Boolean.parseBoolean(rootContainer.readAsString());
        System.out.println(isContinue);

        if(isContinue) {
            System.out.println("ACCESS GRANTED!");
            credentialsMapEntry = new CredentialsMapEntry(username, password);
            System.out.println(rootContainer.readAsString());
            workTableScene.showScene((Stage) button.getScene().getWindow());
        }
        else {
            System.out.println("PERMISSION DENIED!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка доступа");
            alert.setHeaderText(null);
            alert.setContentText("PERMISSION DENIED!");

            alert.showAndWait();

            showScene((Stage) button.getScene().getWindow());
        }
    }
}

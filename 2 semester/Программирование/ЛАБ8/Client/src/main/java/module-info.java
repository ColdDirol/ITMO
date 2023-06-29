module com.itmo.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.xml;


    opens com.itmo.client to javafx.fxml;
    exports com.itmo.client;
    exports com.itmo.client.localisation;
    opens com.itmo.client.localisation to javafx.fxml;
}
module org.cucumber.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.cucumber.common;
    requires lombok;

    opens org.cucumber.client to javafx.fxml;
    exports org.cucumber.client;
    exports org.cucumber.client.utils.classes;
    opens org.cucumber.client.utils.classes to javafx.fxml;
    exports org.cucumber.client.services;
    opens org.cucumber.client.services to javafx.fxml;
}

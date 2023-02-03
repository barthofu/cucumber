module org.cucumber.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.cucumber.common;

    opens org.cucumber.client to javafx.fxml;
    exports org.cucumber.client;
}

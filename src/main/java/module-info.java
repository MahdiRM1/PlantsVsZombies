module main.plantsvszombies {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires java.sql;

    opens main.plantsvszombies to javafx.fxml;
    exports main.plantsvszombies;
}
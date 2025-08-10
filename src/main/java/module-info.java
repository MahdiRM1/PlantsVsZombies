module main.plantsvszombies {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires java.sql;
    requires javafx.media;
    requires jdk.jshell;

    opens main.plantsvszombies to javafx.fxml;
    exports main.plantsvszombies;
    exports main.plantsvszombies.Game.Tools;
    opens main.plantsvszombies.Game.Tools to javafx.fxml;
    exports main.plantsvszombies.Game.PlayModes;
    opens main.plantsvszombies.Game.PlayModes to javafx.fxml;
}
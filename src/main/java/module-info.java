module com.example.miniproyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.sudo.Controller to javafx.fxml;
    opens com.example.sudo to javafx.fxml;
    exports com.example.sudo.Main;
    opens com.example.sudo.Main to javafx.fxml;
}
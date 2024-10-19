package com.example.sudo.View;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
public class AlertAdapter implements AlertHandler{
    @Override
    public void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void showConfirmation(String message, Runnable onConfirm) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                onConfirm.run();
            }
        });
    }
}

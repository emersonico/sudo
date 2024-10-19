package com.example.sudo.View;

public interface AlertHandler {

    void showError(String message);
    void showSuccess(String message);
    void showConfirmation(String message, Runnable onConfirm);

}

package com.example.sudo.View;

import com.example.sudo.Controller.SudokuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Esta clase representa la vista principal de la aplicación de Sudoku.
 * Extiende la clase `Stage` de JavaFX para crear una ventana.
 */

public class SudokuFormView extends Stage {
    public SudokuFormView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sudo/formsudoku.fxml"));
        Parent root = fxmlLoader.load();

        SudokuController controller = fxmlLoader.getController();

        // Inicializar componentes
        controller.startComponents();


        this.setTitle("Sudoku Form");
        this.setScene(new Scene(root));
        this.show();
    }

    // Clase interna para implementar el patrón Singleton
    private static class SudokuFormViewHolder {
        private static SudokuFormView INSTANCE;
    }

    /**
     * Obtiene la única instancia de la vista SudokuFormView.
     * Si aún no existe, la crea.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    public static void getInstance() throws IOException {
        SudokuFormViewHolder.INSTANCE = new SudokuFormView();
    }




}
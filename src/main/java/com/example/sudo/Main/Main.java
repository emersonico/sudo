package com.example.sudo.Main;
import com.example.sudo.View.SudokuFormView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Clase principal que inicia la aplicación de Sudoku.
 * Este programa genera un tablero de Sudoku de 6x6 y permite al usuario interactuar con él.
 *
 * @version 1.0
 * @since 2024-10-16
 * @autor Emerson Albornoz SUárez
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SudokuFormView.getInstance();
        System.out.println("hola");
    }

    public static void main(String[] args) {launch();}

}
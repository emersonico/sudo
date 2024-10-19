package com.example.sudo.Controller;

import com.example.sudo.View.SudokuBoardView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Esta clase es el controlador de la vista del Sudoku. Se encarga de la lógica
 * relacionada con la interacción del usuario y la actualización de la vista del tablero.
 */

public class SudokuController {
    private SudokuBoardView tableroSudoku;

    @FXML
    private Pane panelFondo;

    /**
     * Inicializa los componentes de la vista del Sudoku.
     * - Crea una instancia de `SudokuBoardView`.
     * - Configura el tamaño y estilo de las casillas del tablero.
     * - Agrega la vista del tablero al panel de fondo.
     * - Hace visible la vista del tablero.
     * - Genera un Sudoku inicial.
     */

    public void startComponents() {
        this.tableroSudoku = new SudokuBoardView();
        tableroSudoku.setTxtAltura(36);
        tableroSudoku.setTxWidth(36);
        tableroSudoku.setTxtLetterSize(27);

        //tableroSudoku.setPanelBackground(new Color(89,43,25));

        tableroSudoku.setLayoutX(20);
        tableroSudoku.setLayoutY(80);

        tableroSudoku.setTxtBackground1(Color.WHITE);
        tableroSudoku.setTextForeground1(Color.BLACK);
        tableroSudoku.setTxtBackground2(new Color((double) 232 / 255, (double) 102 / 255, (double) 102 / 255, 0));
        tableroSudoku.setTextForeground2(Color.BLACK);
        tableroSudoku.setTxtBackground3(new Color((double) 232 / 255, (double) 102 / 255, (double) 102 / 255, 0));
        tableroSudoku.setTextForeground3(Color.WHITE);


        panelFondo.getChildren().add(tableroSudoku);

        tableroSudoku.setVisible(true);
        tableroSudoku.makeSudoku();
        tableroSudoku.generateSudoku();


    }

    /**
     * Maneja el evento de click en el botón "Nuevo Juego".
     * Genera un nuevo Sudoku aleatorio.
     * Imprime un mensaje de consola (opcional).
     *
     * @param event El evento de click del botón.
     */
    @FXML
    void onNewGamePressedButton(ActionEvent event) {
        tableroSudoku.generateSudoku();
        System.out.println("Hola");
    }

    /**
     * Maneja el evento de click en el botón "Validar".
     * Valida el Sudoku ingresado por el usuario.
     * (La implementación de la validación se delega a la clase SudokuBoardView)
     *
     * @param event El evento de click del botón.
     */
    @FXML
    void onValidateButton(ActionEvent event) {
        tableroSudoku.validate();

    }

    /**
     * Maneja el evento de click en el botón "Ayuda".
     * (La implementación de la funcionalidad de ayuda se delega a la clase SudokuBoardView)
     *
     * @param actionEvent El evento de click del botón.
     */
    @FXML
    public void onHelpButton(ActionEvent actionEvent) {
        tableroSudoku.help();
    }

    /**
     * Maneja el evento de click en el botón "Resolver".
     * Resuelve el Sudoku automáticamente.
     * (La implementación del algoritmo de resolución se delega a la clase SudokuBoardView)
     *
     * @param event El evento de click del botón.
     */
    @FXML
    void onResolveButton(ActionEvent event) {
        // tableroSudoku.resolver();
        tableroSudoku.completeSudoku();}

}
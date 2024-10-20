package com.example.sudo.Controller;

import com.example.sudo.View.AlertAdapter;
import com.example.sudo.View.AlertHandler;
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
    private SudokuBoardView sudokuBoard;

    @FXML
    private Pane panelFondo;
    private AlertHandler alertHandler;

    /**
     * Inicializa los componentes de la vista del Sudoku.
     * - Crea una instancia de `SudokuBoardView`.
     * - Configura el tamaño y estilo de las casillas del tablero.
     * - Agrega la vista del tablero al panel de fondo.
     * - Hace visible la vista del tablero.
     * - Genera un Sudoku inicial.
     */

    public void startComponents() {
        this.alertHandler = new AlertAdapter();
        this.sudokuBoard = new SudokuBoardView();
        sudokuBoard.setTxtAltura(36);
        sudokuBoard.setTxWidth(36);
        sudokuBoard.setTxtLetterSize(27);

        //tableroSudoku.setPanelBackground(new Color(89,43,25));

        sudokuBoard.setLayoutX(20);
        sudokuBoard.setLayoutY(80);

        sudokuBoard.setTxtBackground1(Color.WHITE);
        sudokuBoard.setTextForeground1(Color.BLACK);
        sudokuBoard.setTxtBackground2(new Color((double) 232 / 255, (double) 102 / 255, (double) 102 / 255, 0));
        sudokuBoard.setTextForeground2(Color.BLACK);
        sudokuBoard.setTxtBackground3(new Color((double) 232 / 255, (double) 102 / 255, (double) 102 / 255, 0));
        sudokuBoard.setTextForeground3(Color.WHITE);


        panelFondo.getChildren().add(sudokuBoard);

        sudokuBoard.setVisible(true);
        sudokuBoard.makeSudoku();
        sudokuBoard.generateSudoku();


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

        alertHandler.showConfirmation("¿Seguro que desea iniciar una nueva partida?", () -> {
            sudokuBoard.generateSudoku();
            System.out.println("Nueva partida iniciada.");
        });
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
        sudokuBoard.validate();

    }

    /**
     * Maneja el evento de click en el botón "Ayuda".
     * (La implementación de la funcionalidad de ayuda se delega a la clase SudokuBoardView)
     *
     * @param actionEvent El evento de click del botón.
     */
    @FXML
    public void onHelpButton(ActionEvent actionEvent) {
        sudokuBoard.help();
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
        sudokuBoard.completeSudoku();}

}
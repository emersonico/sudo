package com.example.sudo.Model;

import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa un tablero de Sudoku de 6x6.
 * Proporciona métodos para generar, mostrar, y validar el tablero de Sudoku.
 */

public class Sudoku {
    private int sudoku[][];
    private int[][] solucion; // Matriz para almacenar la solucion

    /**
     * Constructor que inicializa un tablero vacío de Sudoku.
     */

    public Sudoku() {
        sudoku = new int[6][6];
        clearSudoku();

    }

    /**
     * Muestra la solución del tablero de Sudoku si es posible resolverlo.
     * Si el Sudoku es resuelto correctamente, imprime la solución en la consola.
     * Si no es posible resolver el Sudoku, muestra un mensaje de error.
     */

    public void showSudoku() {
        if (resolveSudoku()) {
            System.out.println("Sudoku resuelto:");
            for (int i = 0; i < sudoku.length; i++) {
                for (int j = 0; j < sudoku[0].length; j++) {
                    System.out.print(sudoku[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("No se pudo resolver el Sudoku.");
        }
    }

    /**
     * Limpia el tablero de Sudoku estableciendo todos sus valores a cero.
     */

    public void clearSudoku(){
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                sudoku[i][j] = 0;
            }
        }
    }

    /**
     * Genera un nuevo Sudoku resolviendo el tablero completamente y luego
     * escondiendo algunos números para que el jugador los complete.
     *
     * @param listTxt La matriz de TextFields utilizada para representar el tablero de Sudoku en la interfaz gráfica.
     */


    public void generateSudoku(TextField[][] listTxt) {
        clearSudoku(); // Limpia el tablero
        if (resolveSudoku()) { // Resuelve completamente el sudoku
            solucion = copyMatrix(sudoku);
            hideNumber(listTxt); // Esconde los numeros para dejar solo 2 por cuadrante
        } else {
            System.out.println("Error: No se pudo resolver el Sudoku.");
        }
    }
    /**
     * Esconde ciertos números en el tablero de Sudoku, dejando solo dos números visibles por cuadrante.
     * Luego actualiza la interfaz gráfica con los cambios.
     *
     * @param listTxt La matriz de TextFields utilizada para representar el tablero de Sudoku en la interfaz gráfica.
     */

    private void hideNumber(TextField[][] listTxt) {
        Random random = new Random();
        for (int firstRow = 0; firstRow < 6; firstRow += 2) {
            for (int colInicio = 0; colInicio < 6; colInicio += 3) {
                hideBlockNumber(firstRow, colInicio, random);
            }
        }
        // También actualizar la interfaz de usuario
        updateInterface(listTxt);
    }

    /**
     * Esconde hasta 4 números en un cuadrante de 2x3 en el tablero de Sudoku.
     *
     * @param firstRow La fila de inicio del cuadrante.
     * @param firstCol La columna de inicio del cuadrante.
     * @param random     Una instancia de la clase Random para generar posiciones aleatorias.
     */

    private void hideBlockNumber(int firstRow, int firstCol, Random random) {
        int count = 0;
        while (count < 4) { // Queremos eliminar hasta 4 números en cada cuadrante para dejar solo 2
            int row = random.nextInt(2) + firstRow;
            int col = random.nextInt(3) + firstCol;
            if (sudoku[row][col] != 0) {
                sudoku[row][col] = 0; // Esconder el número
                count++;
            }
        }
    }

    /**
     * Actualiza la interfaz gráfica del Sudoku, colocando los números visibles en los TextFields.
     * Los TextFields correspondientes a números ocultos se dejan editables.
     *
     * @param listTxt La matriz de TextFields utilizada para representar el tablero de Sudoku en la interfaz gráfica.
     */

    private void updateInterface(TextField[][] listTxt) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (sudoku[i][j] != 0) {
                    listTxt[i][j].setText(String.valueOf(sudoku[i][j]));
                    listTxt[i][j].setEditable(false);
                } else {
                    listTxt[i][j].setText("");
                    listTxt[i][j].setEditable(true);
                }
            }
        }
    }

    /**
     * Crea una copia de la matriz proporcionada.
     *
     * @param original La matriz original que se va a copiar.
     * @return Una nueva matriz que es una copia exacta de la matriz original.
     */

    private int[][] copyMatrix(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[0].length; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    /**
     * Obtiene la solución completa del Sudoku.
     *
     * @return La matriz de solución del Sudoku.
     */

    public int[][] getSolucion() {
        return solucion;
    }




    /**
     * Verifica si el Sudoku ha sido completado correctamente.
     *
     * @return true si el Sudoku está completo y cumple todas las reglas, false de lo contrario.
     */

    public boolean winCheck() {
        // Verificar todas las filas
        for (int i = 0; i < 6; i++) {
            if (!rowCheck(i)) {
                return false;
            }
        }

        // Verificar todas las columnas
        for (int j = 0; j < 6; j++) {
            if (!columnCheck(j)) {
                return false;
            }
        }

        // Verificar todos los bloques 2x3
        for (int fila = 0; fila < 6; fila += 2) {
            for (int col = 0; col < 6; col += 3) {
                if (!blockCheck(fila, col)) {
                    return false;
                }
            }
        }

        // Si pasa todas las verificaciones, el Sudoku está completo y es correcto
        return true;
    }

    /**
     * Verifica si una fila contiene todos los números del 1 al 6 sin repeticiones.
     *
     * @param row La fila a verificar.
     * @return true si la fila es válida, false de lo contrario.
     */


    private boolean rowCheck(int row) {
        boolean[] seen = new boolean[7]; // Para numeros del 1 al 6
        for (int j = 0; j < 6; j++) {
            int num = sudoku[row][j];
            if (num < 1 || num > 6 || seen[num]) {
                return false; // Numero fuera de rango o repetido
            }
            seen[num] = true;
        }
        return true;
    }

    /**
     * Verifica si una columna contiene todos los números del 1 al 6 sin repeticiones.
     *
     * @param col La columna a verificar.
     * @return true si la columna es válida, false de lo contrario.
     */


    private boolean columnCheck(int col) {
        boolean[] seen = new boolean[7]; // Para números del 1 al 6
        for (int i = 0; i < 6; i++) {
            int num = sudoku[i][col];
            if (num < 1 || num > 6 || seen[num]) {
                return false; // Número fuera de rango o repetido
            }
            seen[num] = true;
        }
        return true;
    }



    /**

     Verifica si un bloque de 2x3 en el sudoku es válido.
     @param firstRow La fila inicial del bloque.
     @param firstCol La columna inicial del bloque.
     @return true si el bloque es válido, false en caso contrario. */


    private boolean blockCheck(int firstRow, int firstCol) {
        boolean[] seen = new boolean[7]; // Para números del 1 al 6
        for (int i = firstRow; i < firstRow + 2; i++) {
            for (int j = firstCol; j < firstCol + 3; j++) {
                int num = sudoku[i][j];
                if (num < 1 || num > 6 || seen[num]) {
                    return false; // Número fuera de rango o repetido
                }
                seen[num] = true;
            }
        }
        return true;
    }

    /**

     Resuelve un sudoku de 6x6 utilizando backtracking.
     @return true si el sudoku se resolvió, false en caso contrario. */


    public boolean resolveSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                if (sudoku[i][j] == 0) { // Si la casilla está vacía
                    // Crear una lista con los valores posibles (1 a 6)
                    List<Integer> valores = Arrays.asList(1, 2, 3, 4, 5, 6);
                    // Mezclar los valores para probar en un orden aleatorio
                    Collections.shuffle(valores);

                    // Probar los valores en el orden aleatorio
                    for (int value : valores) {
                        // Validar fila, columna y bloque ignorando la casilla actual
                        if (validateRow(i, value, j) && validateColumn(j, value, i) && validateBlock(i, j, value)) {
                            sudoku[i][j] = value; // Asignar el valor
                            if (resolveSudoku()) { // Intentar resolver el siguiente
                                return true; // Si se resuelve, retorna verdadero
                            }
                            sudoku[i][j] = 0; // Retrocede si no se puede resolver
                        }
                    }
                    return false; // No se pudo colocar ningún número válido, retrocede
                }
            }
        }
        return true; // Sudoku resuelto
    }


    /**

     Valida si un número es válido para una fila específica, ignorando una columna.
     @param i La fila a validar.
     @param value El número a validar.
     @param colIgnore La columna que se debe ignorar.
     @return true si el número es válido, false en caso contrario. */


    public boolean validateRow(int i, int value, int colIgnore) {
        for (int j = 0; j < sudoku[i].length; j++) {
            if (j != colIgnore && sudoku[i][j] == value) {
                return false;
            }
        }
        return true;
    }

    /**

     Valida si un número es válido para una columna específica, ignorando una fila.
     @param j La columna a validar.
     @param value El número a validar.
     @param rowIgnore La fila que se debe ignorar.
     @return true si el número es válido, false en caso contrario. */


    public boolean validateColumn(int j, int value, int rowIgnore) {
        for (int i = 0; i < sudoku.length; i++) {
            if (i != rowIgnore && sudoku[i][j] == value) {
                return false;
            }
        }
        return true;
    }

    /**

     Valida si un número es válido para un bloque específico.

     @param row La fila del bloque.

     @param column La columna del bloque.

     @param num El número a validar.

     @return true si el número es válido, false en caso contrario.
     */

    private boolean validateBlock(int row, int column, int num) {
        int firstRow = (row / 2) * 2; // 2 filas por bloque
        int firstColumn = (column / 3) * 3; // 3 columnas por bloque

        for (int i = firstRow; i < firstRow + 2; i++) { // Revisar 2 filas
            for (int j = firstColumn; j < firstColumn + 3; j++) { // Revisar 3 columnas
                if (sudoku[i][j] == num) {
                    return false; // Número ya existe en el bloque
                }
            }
        }
        return true; // Número válido en el bloque
    }
    /**

     Valida si un número es válido para una posición específica en el sudoku, considerando las reglas del juego.
     @param fila La fila de la posición.
     @param columna La columna de la posición.
     @param num El número a validar.
     @param listTxt La matriz de TextField que representa el sudoku.
     @return true si el número es válido, false en caso contrario. */
    public boolean isNumberValid(int fila, int columna, int num, TextField[][] listTxt) {
        return validateRow1(fila, num, columna, listTxt) && validateColumn1(columna, num, fila, listTxt) && validateBlock1(fila, columna, num, listTxt);
    }

    private boolean validateRow1(int row, int num, int column, TextField[][] listTxt) {
        for (int j = 0; j < 6; j++) {
            if (j != column && getTextFieldValue1(row, j, listTxt) == num) {
                return false;
            }
        }
        return true;
    }

    private boolean validateColumn1(int column, int num, int row, TextField[][] listTxt) {
        for (int i = 0; i < 6; i++) {
            if (i != row && getTextFieldValue1(i, column, listTxt) == num) {
                return false;
            }
        }
        return true;
    }

    private boolean validateBlock1(int row, int column, int num, TextField[][] listTxt) {
        int filaInicio = (row / 2) * 2;
        int colInicio = (column / 3) * 3;
        for (int i = filaInicio; i < filaInicio + 2; i++) {
            for (int j = colInicio; j < colInicio + 3; j++) {
                if ((i != row || j != column) && getTextFieldValue1(i, j, listTxt) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getTextFieldValue1(int fila, int columna, TextField[][] listTxt) {
        String text = listTxt[fila][columna].getText();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }


    /**

     Establece el sudoku actual.
     @param sudoku El nuevo sudoku. */


    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }
    /**

     Obtiene el sudoku actual.
     @return El sudoku actual. */

    public int[][] getSudoku() {
        return sudoku;
    }
}
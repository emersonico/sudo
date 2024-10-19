package com.example.sudo.Model;

import javafx.scene.control.TextField;

import java.util.Random;

/**
 * Clase que representa un tablero de Sudoku de 6x6.
 * Proporciona métodos para generar, mostrar, y validar el tablero de Sudoku.
 */

public class Sudoku {
    private int sudoku[][];

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
     * Genera un nuevo tablero de Sudoku aleatoriamente.
     * Intenta resolver el Sudoku generado y si es válido, lo asigna como el tablero actual.
     * Si no es posible resolverlo, genera un nuevo tablero.
     *
     * @param listTxt Matriz de campos de texto que representan el tablero en la interfaz gráfica.
     */

    public void generateSudoku(TextField[][] listTxt) {
        clearSudoku();
        Random random = new Random();

        boolean sudokuValid = false;
        while (!sudokuValid) {
            clearSudoku();

            generateQuadrant(0, 0, random, listTxt); // Cuadrante 1
            generateQuadrant(0, 3, random, listTxt); // Cuadrante 2
            generateQuadrant(2, 0, random, listTxt); // Cuadrante 3
            generateQuadrant(2, 3, random, listTxt); // Cuadrante 4
            generateQuadrant(4, 0, random, listTxt); // Cuadrante 5
            generateQuadrant(4, 3, random, listTxt); // Cuadrante 6

            if (resolveSudoku()) {
                sudokuValid = true;
            } else {
                System.out.println(" nno se pudo resolver el sudoku generando uno nuevo ");
            }
        }

        limitQuadrantNumbers(listTxt);
    }

    /**
     * Genera dos números válidos en un cuadrante específico del tablero.
     * Verifica que los números sean válidos en su fila, columna y bloque.
     *
     * @param firstRow    La fila inicial del cuadrante.
     * @param firstColumn La columna inicial del cuadrante.
     * @param random      Objeto de la clase Random para generar números aleatorios.
     * @param listTxt     Matriz de campos de texto que representan el tablero en la interfaz gráfica.
     */


    private void generateQuadrant(int firstRow, int firstColumn, Random random, TextField[][] listTxt ) {
        int count = 0;
        while (count < 2) {
            int fila = random.nextInt(2) + firstRow;
            int col = random.nextInt(3) + firstColumn;
            if (sudoku[fila][col] == 0) {
                int num = random.nextInt(6) + 1;
                if (isNumberValid(fila, col, num, listTxt)) {
                    sudoku[fila][col] = num;
                    count++;
                }
            }
        }
    }

    /**
     * Elimina números en exceso en cada cuadrante del Sudoku para que no haya más de dos números por cuadrante.
     *
     * @param listTxt Matriz de campos de texto que representan el tablero en la interfaz gráfica.
     */

    private void limitQuadrantNumbers(TextField[][] listTxt) {
        eliminateQuadrantNumbers(0, 0, listTxt); // Primer cuadrante (fila 0-1, columna 0-2)
        eliminateQuadrantNumbers(0, 3, listTxt); // Segundo cuadrante (fila 0-1, columna 3-5)
        eliminateQuadrantNumbers(2, 0, listTxt); // Tercer cuadrante (fila 2-3, columna 0-2)
        eliminateQuadrantNumbers(2, 3, listTxt); // Cuarto cuadrante (fila 2-3, columna 3-5)
        eliminateQuadrantNumbers(4, 0, listTxt); // Quinto cuadrante (fila 4-5, columna 0-2)
        eliminateQuadrantNumbers(4, 3, listTxt); // Sexto cuadrante (fila 4-5, columna 3-5)
    }

    /**
     * Elimina números aleatorios de un cuadrante para dejar solo dos números en dicho cuadrante.
     *
     * @param firstRow    La fila inicial del cuadrante.
     * @param firstColumn La columna inicial del cuadrante.
     * @param listTxt     Matriz de campos de texto que representan el tablero en la interfaz gráfica.
     */

    private void eliminateQuadrantNumbers(int firstRow, int firstColumn, TextField[][] listTxt) {
        Random random = new Random();
        int[][] indices = new int[6][2];
        int index = 0;
        // Guardamos las posiciones de los numeros en el cuadrante
        for (int i = firstRow; i < firstRow + 2; i++) { // 2 filas por cuadrante
            for (int j = firstColumn; j < firstColumn + 3; j++) { // 3 columnas por cuadrante
                if (sudoku[i][j] != 0) {
                    indices[index][0] = i;
                    indices[index][1] = j;
                    index++;
                }
            }
        }
        // Si hay más de 2 numeros en el cuadrante  eliminar el exceso
        while (index > 2) {
            int randomIndex = random.nextInt(index);
            int row = indices[randomIndex][0];
            int col = indices[randomIndex][1];
            sudoku[row][col] = 0; // Eliminar el numero
            indices[randomIndex] = indices[--index]; // Actualizar la lista de indices
        }
    }

    /**
     * Comprueba si el Sudoku actual cumple con las reglas del juego.
     *
     * @return true si el Sudoku es válido, false de lo contrario.
     */

    public boolean comprobarSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                int aux = sudoku[i][j];
                if (!validateRow(i, aux, j) || !validateColumn(j, aux, i) || !validateBlock(i, j, aux)) {
                    return false;
                }
            }
        }
        return true;
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
                    for (int value = 1; value <= 6; value++) { // Probar valores del 1 al 6
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
    /**

     Valida si un número es válido para una row específica, ignorando una column, en el contexto de una matriz de TextField.
     @param row La row a validar.
     @param num El número a validar.
     @param column La column que se debe ignorar.
     @param listTxt La matriz de TextField que representa el sudoku.
     @return true si el número es válido, false en caso contrario. */

    private boolean validateRow1(int row, int num, int column, TextField[][] listTxt) {
        for (int j = 0; j < 6; j++) {
            if (j != column && getTextFieldValue1(row, j, listTxt) == num) {
                return false;
            }
        }
        return true;
    }
    /**

     Valida si un número es válido para una columna específica, ignorando una row, en el contexto de una matriz de TextField.
     @param column La columna a validar.
     @param num El número a validar.
     @param row La row que se debe ignorar.
     @param listTxt La matriz de TextField que representa el sudoku.
     @return true si el número es válido, false en caso contrario. */

    private boolean validateColumn1(int column, int num, int row, TextField[][] listTxt) {
        for (int i = 0; i < 6; i++) {
            if (i != row && getTextFieldValue1(i, column, listTxt) == num) {
                return false;
            }
        }
        return true;
    }

    /**

     Valida si un número es válido para un bloque específico, en el contexto de una matriz de TextField.
     @param row La row del bloque.
     @param column La column del bloque.
     @param num El número a validar.
     @param listTxt La matriz de TextField que representa el sudoku.
     @return true si el número es válido, false en caso contrario. */

    private boolean validateBlock1(int row, int column, int num, TextField[][] listTxt) {
        int filaInicio = (row / 2) * 2; // Cada bloque tiene 2 filas
        int colInicio = (column / 3) * 3; // Cada bloque tiene 3 columnas
        for (int i = filaInicio; i < filaInicio + 2; i++) { // 2 filas por bloque
            for (int j = colInicio; j < colInicio + 3; j++) { // 3 columnas por bloque
                if ((i != row || j != column) && getTextFieldValue1(i, j, listTxt) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**

     Obtiene el valor numérico de un TextField en una matriz de TextField.
     @param fila La fila del TextField.
     @param columna La columna del TextField.
     @param listaTxt La matriz de TextField.
     @return El valor numérico del TextField, o 0 si está vacío. */

    private int getTextFieldValue1(int fila, int columna, TextField[][] listaTxt) {
        String text = listaTxt[fila][columna].getText();
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
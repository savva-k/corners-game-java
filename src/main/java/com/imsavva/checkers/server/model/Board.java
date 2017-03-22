package com.imsavva.checkers.server.model;

/**
 * The chessboard class.
 *
 * @author Savva Kodeikin
 */
public class Board {
    private Cell[][] field = new Cell[8][8];
    private static final char[] characters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };

    public Board() {
        createCells();
    }

    private void createCells() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                String name = characters[j] + String.valueOf(i + 1);
                field[i][j] = new Cell(name, j, i);
            }
        }
    }
}

package com.imsavva.checkers.server.view;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Cell;
import com.imsavva.checkers.server.beans.Figure;

/**
 * @author Savva Kodeikin
 */
public class ConsoleInterfaceDrawer implements InterfaceDrawer {

    public void draw(Board board) {
        System.out.println("drawing a board");

        for (int y = 0; y < board.getHeight(); y++) {
            System.out.print(board.getHeight() - y);

            for (int x = board.getWidth() - 1; x >= 0; x--) {
                Cell cell = board.getCellAt(x, y);
                drawCell(cell);
            }

            System.out.println();
        }

        System.out.print(" ");

        for (int i = 0; i < board.getWidth(); i++) {
            System.out.print(" " + board.CHARACTERS[i] + " ");
        }

        System.out.println();
    }

    private void drawCell(Cell cell) {
        if (cell.isEmpty()) {
            System.out.print(" _ ");
        } else {
            if (Figure.Color.WHITE == cell.getFigure().getColor()) {
                System.out.print(" x ");
            } else {
                System.out.print(" o ");
            }
        }
    }
}

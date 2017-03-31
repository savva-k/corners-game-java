package com.imsavva.checkers.server.view;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Cell;
import com.imsavva.checkers.server.beans.Figure;
import com.imsavva.checkers.server.beans.Player;
import com.imsavva.checkers.server.model.GameModel;

/**
 * @author Savva Kodeikin
 */
public class ConsoleInterfaceDrawer implements InterfaceDrawer {

    public static final String WHITE_FIGURE_LETTER = " x ";
    public static final String BLACK_FIGURE_LETTER = " o ";

    public void draw(GameModel gameModel) {
        Board board = gameModel.getBoard();
        Player player = gameModel.getActivePlayer();
        System.out.println(String.format("%s [%s \"%s\"], it's your turn!",
                player.getName(),
                player.getColor(),
                getLetter(player.getColor())));

        for (int y = 0; y < board.getHeight(); y++) {
            System.out.print(board.getHeight() - y);

            for (int x = 0; x < board.getWidth(); x++) {
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

    public void proclaimWinner(Player winner) {
        gameOver();
        System.out.println(winner.getName() + " wins!");
    }

    public void proclaimDeadHeat() {
        gameOver();
        System.out.println("Dead heat!");
    }

    private void gameOver() {
        System.out.println("----------------");
        System.out.println("Game over!");
    }

    private void drawCell(Cell cell) {
        if (cell.isEmpty()) {
            System.out.print(" _ ");
        } else {
            System.out.print(getLetter(cell.getFigure().getColor()));
        }
    }

    private String getLetter(Figure.Color color) {
        return color == Figure.Color.WHITE ? WHITE_FIGURE_LETTER : BLACK_FIGURE_LETTER;
    }
}

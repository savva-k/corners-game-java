package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Figure;
import com.imsavva.checkers.server.beans.Point;
import com.imsavva.checkers.server.view.InterfaceDrawer;

import java.util.ArrayList;
import java.util.List;

/**
 * Ugolki is a two-players game, usually played on a 8x8 board.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Ugolki">https://en.wikipedia.org/wiki/Ugolki</a>
 * @author Savva Kodeikin
 */
public class UgolkiGame {

    private Board board;
    private InterfaceDrawer interfaceDrawer;

    public UgolkiGame(Board board, InterfaceDrawer drawer) {
        this.board = board;
        this.interfaceDrawer = drawer;

        board.cleanCells();
        initWhiteFigures();
        initBlackFigures();
    }

    /**
     * Start a new game.
     */
    public void play() {
        interfaceDrawer.draw();
    }

    private void initBlackFigures() {
        for (Point point : CheckersParams.getBlacksStartPoints()) {
            board.addFigure(new Figure(point, Figure.Color.BLACK), point);
        }
    }

    private void initWhiteFigures() {
        for (Point point : CheckersParams.getWhitesStartPoints()) {
            board.addFigure(new Figure(point, Figure.Color.WHITE), point);
        }
    }

    /**
     * This class contains parameters for ugolki game.
     */
    private static class CheckersParams {
        private static List<Point> whitesStartPoints = new ArrayList<Point>();
        private static List<Point> blacksStartPoints = new ArrayList<Point>();

        static {
            whitesStartPoints.add(new Point(0, 0));
            whitesStartPoints.add(new Point(1, 0));
            whitesStartPoints.add(new Point(2, 0));
            whitesStartPoints.add(new Point(3, 0));
            whitesStartPoints.add(new Point(0, 1));
            whitesStartPoints.add(new Point(1, 1));
            whitesStartPoints.add(new Point(2, 1));
            whitesStartPoints.add(new Point(3, 1));
            whitesStartPoints.add(new Point(0, 2));
            whitesStartPoints.add(new Point(1, 2));
            whitesStartPoints.add(new Point(2, 2));
            whitesStartPoints.add(new Point(3, 2));

            blacksStartPoints.add(new Point(4, 7));
            blacksStartPoints.add(new Point(5, 7));
            blacksStartPoints.add(new Point(6, 7));
            blacksStartPoints.add(new Point(7, 7));
            blacksStartPoints.add(new Point(4, 6));
            blacksStartPoints.add(new Point(5, 6));
            blacksStartPoints.add(new Point(6, 6));
            blacksStartPoints.add(new Point(7, 6));
            blacksStartPoints.add(new Point(4, 5));
            blacksStartPoints.add(new Point(5, 5));
            blacksStartPoints.add(new Point(6, 5));
            blacksStartPoints.add(new Point(7, 5));
        }

        public static List<Point> getWhitesStartPoints() {
            return whitesStartPoints;
        }

        public static List<Point> getBlacksStartPoints() {
            return blacksStartPoints;
        }
    }
}

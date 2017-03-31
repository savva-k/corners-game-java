package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.*;
import com.imsavva.checkers.server.model.exceptions.GameException;
import com.imsavva.checkers.server.model.exceptions.PathCheckingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ugolki is a two-players game, usually played on a 8x8 board.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Ugolki">https://en.wikipedia.org/wiki/Ugolki</a>
 * @author Savva Kodeikin
 */
public class UgolkiGame implements GameModel {

    private Board board;
    private Player player1;
    private Player player2;
    private Player activePlayer;
    private WinCheckResponse lastGameStatus;
    private PathChecker pathChecker;
    private WinCriteria winCriteria;

    public UgolkiGame(Board board, Player player1, Player player2,
                      PathChecker pathChecker, WinCriteria winCriteria) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.pathChecker = pathChecker;
        this.winCriteria = winCriteria;
    }

    public void startGame() {
        board.cleanCells();
        initWhiteFigures();
        initBlackFigures();
        assignFigureColors();
        activePlayer = getPlayerWithWhites();
        lastGameStatus = new WinCheckResponse(WinCheckResponse.Status.JUST_STARTED);
    }

    public void move(String from, String to) throws GameException {
        Cell cellFrom = board.getCellAt(from);
        Cell cellTo = board.getCellAt(to);

        try {
            pathChecker.checkMovePossibility(activePlayer, cellFrom, cellTo);
            cellTo.setFigure(cellFrom.getFigure());
            cellFrom.removeFigure();
            lastGameStatus = winCriteria.checkForWinner(board, activePlayer);
            changeActivePlayer();
        } catch (PathCheckingException e) {
            throw new GameException(e.getMessage());
        }
    }

    public WinCheckResponse getStatus() {
        return lastGameStatus;
    }

    public Board getBoard() {
        return board;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    private void initBlackFigures() {
        for (Point point : CheckersParams.getWhitesStartPoints()) {
            board.addFigure(new Figure(point, Figure.Color.BLACK), point);
        }
        board.getCellAt(2, 4).setFigure(board.getCellAt(2, 5).getFigure());
        board.getCellAt(2, 5).removeFigure();
    }

    private void initWhiteFigures() {
        for (Point point : CheckersParams.getBlacksStartPoints()) {
            board.addFigure(new Figure(point, Figure.Color.WHITE), point);
        }
        board.getCellAt(4, 3).setFigure(board.getCellAt(4, 2).getFigure());
        board.getCellAt(4, 2).removeFigure();
    }

    private void assignFigureColors() {
        Random random = new Random();

        if (random.nextBoolean()) {
            player1.setColor(Figure.Color.WHITE);
            player2.setColor(Figure.Color.BLACK);
        } else {
            player1.setColor(Figure.Color.BLACK);
            player2.setColor(Figure.Color.WHITE);
        }
    }

    private Player getPlayerWithWhites() {
        return player1.getColor() == Figure.Color.WHITE ? player1 : player2;
    }

    private void changeActivePlayer() {
        activePlayer = activePlayer == player1 ? player2 : player1;
    }

    /**
     * This class contains parameters for the ugolki game.
     */
    public static class CheckersParams {
        private static List<Point> whitesStartPoints = new ArrayList<Point>();
        private static List<Point> blacksStartPoints = new ArrayList<Point>();

        static {
            whitesStartPoints.add(new Point(0, 7));
            whitesStartPoints.add(new Point(1, 7));
            whitesStartPoints.add(new Point(2, 7));
            whitesStartPoints.add(new Point(3, 7));
            whitesStartPoints.add(new Point(0, 6));
            whitesStartPoints.add(new Point(1, 6));
            whitesStartPoints.add(new Point(2, 6));
            whitesStartPoints.add(new Point(3, 6));
            whitesStartPoints.add(new Point(0, 5));
            whitesStartPoints.add(new Point(1, 5));
            whitesStartPoints.add(new Point(2, 5));
            whitesStartPoints.add(new Point(3, 5));

            blacksStartPoints.add(new Point(4, 0));
            blacksStartPoints.add(new Point(5, 0));
            blacksStartPoints.add(new Point(6, 0));
            blacksStartPoints.add(new Point(7, 0));
            blacksStartPoints.add(new Point(4, 1));
            blacksStartPoints.add(new Point(5, 1));
            blacksStartPoints.add(new Point(6, 1));
            blacksStartPoints.add(new Point(7, 1));
            blacksStartPoints.add(new Point(4, 2));
            blacksStartPoints.add(new Point(5, 2));
            blacksStartPoints.add(new Point(6, 2));
            blacksStartPoints.add(new Point(7, 2));
        }

        public static List<Point> getWhitesStartPoints() {
            return whitesStartPoints;
        }

        public static List<Point> getBlacksStartPoints() {
            return blacksStartPoints;
        }
    }
}

package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Cell;
import com.imsavva.checkers.server.model.exceptions.PathCheckingException;

/**
 * @author Savva Kodeikin
 */
public class UgolkiPathChecker implements PathChecker {

    private Board board;

    public UgolkiPathChecker(Board board) {
        this.board = board;
    }

    public void checkMovePossibility(Cell from, Cell to) throws PathCheckingException {
        if (isOneCellMove(from, to)) {
            checkShortMove(from, to);
        } else {
            checkLongMove(from, to);
        }
    }

    /**
     * Check if a player doesn't intend to jump over other figures.
     *
     * @param from Cell from
     * @param to Cell to
     * @return true, if Cell "to" is the nearest cell to "from"
     */
    private boolean isOneCellMove(Cell from, Cell to) {
        return Math.abs(from.getX() - to.getX()) <=1
                && Math.abs(from.getY() - to.getY()) <= 1;
    }

    private void checkShortMove(Cell from, Cell to) throws PathCheckingException {
        if (from.isEmpty()) {
            throw new PathCheckingException("Source cell is empty!");
        }

        if (!to.isEmpty()) {
            throw new PathCheckingException("Destination cell is already taken!");
        }
    }

    private void checkLongMove(Cell from, Cell to) {

    }

}

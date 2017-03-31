package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Cell;
import com.imsavva.checkers.server.beans.Player;
import com.imsavva.checkers.server.model.exceptions.PathCheckingException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Savva Kodeikin
 */
public class UgolkiPathChecker implements PathChecker {

    private Board board;

    public UgolkiPathChecker(Board board) {
        this.board = board;
    }

    public void checkMovePossibility(Player player, Cell from, Cell to) throws PathCheckingException {
        quickCheck(player, from, to);

        List<Cell> neighbourCells = board.getNeighbourCells(from);
        List<Cell> possibleMoves = new ArrayList<Cell>();

        for (Cell cell : neighbourCells) {
            if (!cell.isEmpty()) {
                tryJumpOver(possibleMoves, new ArrayList<Cell>(), from, cell);
            } else {
                possibleMoves.add(cell);
            }
        }

        if (!possibleMoves.contains(to)) {
            throw new PathCheckingException("You cannot move your figure there!");
        }
    }

    private void quickCheck(Player player, Cell from, Cell to) throws PathCheckingException {
        if (from.isEmpty()) {
            throw new PathCheckingException("Source cell is empty!");
        }

        if (!to.isEmpty()) {
            throw new PathCheckingException("Destination cell is already taken!");
        }

        if (player.getColor() != from.getFigure().getColor()) {
            throw new PathCheckingException("You cannot move your opponent's figures!");
        }
    }

    private void tryJumpOver(List<Cell> possibleMoves, List<Cell> jumpedOver, Cell from, Cell takenCell) {
        Cell cellToJump = getCellToJump(from, takenCell);

        if (cellToJump != null && cellToJump.isEmpty()) {
            List<Cell> neighbourCells = board.getNeighbourCells(cellToJump);
            possibleMoves.add(cellToJump);
            jumpedOver.add(takenCell);

            for (Cell cell : neighbourCells) {
                if (!cell.isEmpty() && !jumpedOver.contains(cell)) {
                    tryJumpOver(possibleMoves, jumpedOver, cellToJump, cell);
                }
            }
        }
    }

    private Cell getCellToJump(Cell from, Cell over) {
        int newX = over.getX() + (over.getX() - from.getX());
        int newY = over.getY() + (over.getY() - from.getY());
        return board.getCellAt(newX, newY);
    }

}

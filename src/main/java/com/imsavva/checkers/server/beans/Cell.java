package com.imsavva.checkers.server.beans;

/**
 * Cell class represents a cell on a game board.
 *
 * @author Savva Kodeikin
 */
public class Cell {

    private Point point;
    private String name;
    private Figure figure;

    public Cell() {

    }

    public Cell (String name, Point point) {
        this.name = name;
        this.point = point;
    }

    public Cell(String name, int x, int y) {
        this(name, new Point(x, y));
    }

    /**
     * Remove a figure from the cell.
     */
    public void removeFigure() {
        setFigure(null);
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public int getX() {
        return point.getX();
    }

    public void setX(int x) { this.point.setX(x); }

    public int getY() {
        return point.getY();
    }

    public void setY(int y) {
        this.point.setY(y);
    }

    /**
     * Check if the cell contain a figure or not.
     *
     * @return boolean result
     */
    public boolean isEmpty() {
        return this.figure == null;
    }

    @Override
    public String toString() {
        return String.format("Cell [%s, %s]", name, point);
    }
}

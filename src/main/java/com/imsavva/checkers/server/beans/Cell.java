package com.imsavva.checkers.server.beans;

/**
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

    @Override
    public String toString() {
        return String.format("Cell [%s, %s]", name, point);
    }

    public boolean isEmpty() {
        return this.figure == null;
    }
}

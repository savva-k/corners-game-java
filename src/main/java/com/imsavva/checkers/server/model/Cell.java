package com.imsavva.checkers.server.model;

/**
 * @author Savva Kodeikin
 */
public class Cell {

    private int x;
    private int y;
    private String name;
    private Figure figure;

    public Cell() {

    }

    public Cell(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("Cell [%s, %s, %s]", name, x, y);
    }
}

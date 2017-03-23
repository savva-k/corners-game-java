package com.imsavva.checkers.server.beans;

/**
 * Figure class represents player's units on a game board.
 *
 * @author Savva Kodeikin
 */
public class Figure {

    private Color color;
    private Point point;

    public Figure() {

    }

    public Figure(Point point, Color color) {
        this.point = point;
        this.color = color;
    }

    public Figure(int x, int y, Color color) {
        this(new Point(x, y), color);
    }

    public Color getColor() {
        return color;
    }

    /**
     * Figure can be of different colors.
     */
    public enum Color {
        BLACK,
        WHITE
    }
}

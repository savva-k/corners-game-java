package com.imsavva.checkers.server.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * The chessboard class.
 *
 * @author Savva Kodeikin
 */
public class Board {

    public static final char[] CHARACTERS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };

    private static final int DEFAULT_WIDTH = 8;
    private static final int DEFAULT_HEIGHT = 8;
    private Map<Point, Cell> cells;
    private int width;
    private int height;

    public Board() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new HashMap<Point, Cell>();
        createCells();
    }

    /**
     * Add a figure to the board at the specified point.
     *
     * @param figure Figure to add
     * @param point Point to add the figure to
     */
    public void addFigure(Figure figure, Point point) {
        this.cells.get(point).setFigure(figure);
    }

    public void cleanCells() {
        for (Cell cell : this.cells.values()) {
            cell.removeFigure();
        }
    }

    /**
     * Get a cell of the board placed at specified coordinates.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return Cell at specified coordinates
     */
    public Cell getCellAt(int x, int y) {
        return this.cells.get(new Point(x, y));
    }

    /**
     * Get cell by a text address, like A1, H8.
     *
     * @param address String address
     * @throws IllegalArgumentException
     * @return Cell cell
     */
    public Cell getCellAt(String address) {
        if (address.length() != 2) {
            throw new IllegalArgumentException("Incorrect argument \"address\" length: " + address);
        }

        char xAxisChar = address.toUpperCase().charAt(0);

        for (int x = 0; x < CHARACTERS.length; x++) {
            if (xAxisChar == CHARACTERS[x]) {
                try {
                    int y = Integer.valueOf(address.substring(1));
                    return getCellAt(x, y);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Incorrect argument \"address\": " + address);
                }
            }
        }

        throw new IllegalArgumentException("Incorrect argument \"address\" value: " + address);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private void createCells() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                String name = CHARACTERS[j] + String.valueOf(i + 1);
                Cell cell = new Cell(name, j, i);
                Point point = new Point (i, j);
                this.cells.put(point, cell);
            }
        }
    }
}

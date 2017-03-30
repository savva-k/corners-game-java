package com.imsavva.checkers.server.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Map<String, Cell> cellsByName;
    private int width;
    private int height;

    public Board() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new HashMap<Point, Cell>();
        cellsByName = new HashMap<String, Cell>();
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

    public List<Cell> getNeighbourCells(Cell cell) {
        List<Cell> cells = new ArrayList<Cell>();

        addNeighbourCell(cells, cell, 1, 0);
        addNeighbourCell(cells, cell, -1, 0);
        addNeighbourCell(cells, cell, 0, 1);
        addNeighbourCell(cells, cell, 0, -1);

        return cells;
    }

    /**
     * Get cell by a text address, like A1, H8.
     *
     * @param address String address
     * @throws IllegalArgumentException
     * @return Cell cell
     */
    public Cell getCellAt(String address) {
        return cellsByName.get(address);
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
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                String name = CHARACTERS[x] + String.valueOf(y + 1);
                int inversedY = getWidth() - (y + 1);
                Cell cell = new Cell(name, x, inversedY);
                Point point = new Point (x, inversedY);
                this.cells.put(point, cell);
                this.cellsByName.put(name, cell);
            }
        }
    }

    private void addNeighbourCell(List<Cell> cells, Cell cell, int offsetX, int offsetY) {
        Cell neighbourCell = getCellAt(cell.getX() + offsetX, cell.getY() + offsetY);

        if (neighbourCell != null) {
            cells.add(neighbourCell);
        }
    }
}

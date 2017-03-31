package com.imsavva.checkers.server.beans;

/**
 * @author Savva Kodeikin
 */
public class Player {

    private Figure.Color color;
    private boolean isActive;
    private String name;

    public Player() {

    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Figure.Color getColor() {
        return color;
    }

    public void setColor(Figure.Color color) {
        this.color = color;
    }
}

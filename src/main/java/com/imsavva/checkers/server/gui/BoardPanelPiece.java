package com.imsavva.checkers.server.gui;

import lombok.*;

import java.awt.*;

@Getter
@Builder
class BoardPanelPiece {
    private final boolean isEmpty;
    private final boolean isWhite;
    private final Point square;

    @Setter
    private Image image;

    boolean isAt(int x, int y) {
        return square.x == x && square.y == y;
    }

    @Override
    public String toString() {
        String color = isWhite ? "white" : "black";
        return String.format("(%s, %s: %s)", square.x, square.y,
                isEmpty ? "empty" : color);
    }
}

package com.imsavva.checkers.server.gui;

public interface BoardPanelListener {
    void movingPiece(String from, String to);
    BoardPanelPiece getPieceAt(int x, int y);
}

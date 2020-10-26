package com.imsavva.checkers.server.gui;

import com.imsavva.checkers.server.beans.Player;

public interface BoardPanelListener {
    void movingPiece(String from, String to);
    BoardPanelPiece getPieceAt(int x, int y);
    Player getCurrentPlayer();
}

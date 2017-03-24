package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Player;

/**
 * @author Savva Kodeikin
 */
public interface WinCriteria {

    /**
     * Check a game board if there's a winner or not.
     *
     * @param board a game board
     * @param player1
     * @param player2
     * @return null if no one win
     */
    WinCheckResponse checkForWinner(Board board, Player player1, Player player2);
}

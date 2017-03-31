package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Player;

/**
 * @author Savva Kodeikin
 */
public interface WinCriteria {

    /**
     * Check a game board if there's a winner or not.
     * If there can be dead heat, this case also should be treated.
     *
     * @param board a game board
     * @param activePlayer the current player
     * @return WinCheckResponse game status object
     */
    WinCheckResponse checkForWinner(Board board, Player activePlayer);
}

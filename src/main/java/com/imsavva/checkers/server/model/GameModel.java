package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Player;
import com.imsavva.checkers.server.model.exceptions.GameException;

/**
 * A game model interface.
 *
 * @author Savva Kodeikin
 */
public interface GameModel {

    /**
     * Start a new game.
     */
    void startGame();

    /**
     * Move a figure.
     *
     * @throws GameException
     */
    void move(String from, String to) throws GameException;

    /**
     * Return current game status.
     *
     * @return WinCheckResponse response containing the game status and a winner if possible
     */
    WinCheckResponse getStatus();

    /**
     * Get a game board.
     *
      * @return board game board
     */
    Board getBoard();

    /**
     * Get a player that is currently active.
     *
     * @return Player current active player
     */
    Player getActivePlayer();
}

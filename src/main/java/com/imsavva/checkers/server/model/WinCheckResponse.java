package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.Player;

/**
 * A response class that holds a game status and a winner, if there is one.
 *
 * @author Savva Kodeikin
 */
public class WinCheckResponse {

    private Status status;
    private Player winner;

    public WinCheckResponse() {}

    public WinCheckResponse(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public enum Status {

        /**
         * This is a new game.
         */
        JUST_STARTED,

        /**
         * The game is in progress.
         */
        IN_PROGRESS,

        /**
         * The game is finished and there is a winner.
         */
        FINISHED,

        /**
         * The game is finished.
         * Each player has the same score amount.
         */
        DEAD_HEAT
    }
}

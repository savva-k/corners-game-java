package com.imsavva.checkers.server.model.commands;

import com.imsavva.checkers.server.model.exceptions.GameException;

/**
 * @author Savva Kodeikin
 */
public interface Command {

    /**
     * Command execution actions.
     *
     * @throws GameException
     */
    void execute() throws GameException;

    enum Type {

        /**
         * A command to move a figure.
         */
        MOVE_FIGURE
    }
}

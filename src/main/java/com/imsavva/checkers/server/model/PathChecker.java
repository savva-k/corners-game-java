package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.Cell;
import com.imsavva.checkers.server.model.exceptions.PathCheckingException;

/**
 * @author Savva Kodeikin
 */
public interface PathChecker {

    /**
     * Check if it's possible to move a figure from {@link com.imsavva.checkers.server.beans.Cell} from to Cell to.
     * @param from
     * @param to
     * @return
     */
    void checkMovePossibility(Cell from, Cell to) throws PathCheckingException;
}

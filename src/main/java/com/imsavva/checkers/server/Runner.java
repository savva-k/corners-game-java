package com.imsavva.checkers.server;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.model.UgolkiGame;
import com.imsavva.checkers.server.view.ConsoleInterfaceDrawer;
import com.imsavva.checkers.server.view.InterfaceDrawer;

/**
 * @author Savva Kodeikin
 */
public class Runner {

    public static void main(String[] args) {
        Board board = new Board();
        InterfaceDrawer drawer = new ConsoleInterfaceDrawer(board);
        UgolkiGame game = new UgolkiGame(board, drawer);
        game.play();
    }
}

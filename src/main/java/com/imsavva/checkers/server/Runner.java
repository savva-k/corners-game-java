package com.imsavva.checkers.server;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Player;
import com.imsavva.checkers.server.controller.ConsoleController;
import com.imsavva.checkers.server.controller.Controller;
import com.imsavva.checkers.server.model.GameModel;
import com.imsavva.checkers.server.model.PathChecker;
import com.imsavva.checkers.server.model.UgolkiGame;
import com.imsavva.checkers.server.model.UgolkiPathChecker;
import com.imsavva.checkers.server.view.ConsoleInterfaceDrawer;
import com.imsavva.checkers.server.view.InterfaceDrawer;

/**
 * @author Savva Kodeikin
 */
public class Runner {

    public static void main(String[] args) {
        Board board = new Board();
        InterfaceDrawer drawer = new ConsoleInterfaceDrawer();
        PathChecker pathChecker = new UgolkiPathChecker(board);
        Player player1 = new Player("Dummy Player");
        Player player2 = new Player("Test Player");

        GameModel game = new UgolkiGame(board, player1, player2, pathChecker);
        Controller controller = new ConsoleController(drawer, game);
        controller.startGame();
    }
}

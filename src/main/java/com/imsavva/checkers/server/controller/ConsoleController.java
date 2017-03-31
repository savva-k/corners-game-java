package com.imsavva.checkers.server.controller;

import com.imsavva.checkers.server.model.GameModel;
import com.imsavva.checkers.server.model.WinCheckResponse;
import com.imsavva.checkers.server.model.exceptions.GameException;
import com.imsavva.checkers.server.view.InterfaceDrawer;

import java.util.Scanner;

/**
 * Console controller reads commands from command line and delegates them to a game.
 *
 * @author Savva Kodeikin
 */
public class ConsoleController implements Controller {

    private InterfaceDrawer interfaceDrawer;
    private GameModel game;

    public ConsoleController(InterfaceDrawer drawer, GameModel game) {
        this.interfaceDrawer = drawer;
        this.game = game;
    }

    public void startGame() {
        game.startGame();
        WinCheckResponse gameStatus = game.getStatus();
        Scanner scanner = new Scanner(System.in);

        while (gameStatus.getStatus() == WinCheckResponse.Status.JUST_STARTED ||
                gameStatus.getStatus() == WinCheckResponse.Status.IN_PROGRESS) {
            interfaceDrawer.draw(game);

            System.out.println("Write a move, e.g. B3 B4");
            String[] command = scanner.nextLine().toUpperCase().split(" ");

            try {
                game.move(command[0], command[1]);
            } catch (GameException e) {
                System.out.println("Exception: " + e.getMessage());
            }

            gameStatus = game.getStatus();
        }
    }
}

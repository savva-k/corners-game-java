package com.imsavva.checkers.server.gui;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Cell;
import com.imsavva.checkers.server.beans.Figure;
import com.imsavva.checkers.server.beans.Player;
import com.imsavva.checkers.server.model.*;
import com.imsavva.checkers.server.model.exceptions.GameException;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@Slf4j
public class GraphicInterface extends JFrame implements ActionListener, BoardPanelListener {
    private transient GameModel game;
    private transient Board board;
    private JLabel currentPlayerLabel;
    private JLabel statusLabel;

    public GraphicInterface() {
        initGame();
        initMenuBar();
        initUI();
        game.startGame();
    }

    private void initGame() {
        board = new Board();
        PathChecker pathChecker = new UgolkiPathChecker(board);
        Player player1 = new Player("Dummy Player");
        Player player2 = new Player("Test Player");
        WinCriteria winCriteria = new UgolkiWinCriteria();
        game = new UgolkiGame(board, player1, player2, pathChecker, winCriteria);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem newGameMenuItem = new JMenuItem("New Game", KeyEvent.VK_N);
        newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newGameMenuItem.addActionListener(this);
        fileMenu.add(newGameMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);

        this.setJMenuBar(menuBar);
    }

    private void initUI() {
        BoardPanel boardPanel = new BoardPanel();
        boardPanel.addMoveListener(this);
        boardPanel.initComponents();

        currentPlayerLabel = new JLabel();
        statusLabel = new JLabel();
        resetStatusLabel();
        refreshCurrentPlayer();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        add(currentPlayerLabel);
        add(boardPanel);
        add(statusLabel);

        pack();
        setResizable(false);

        setTitle("Corners Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public BoardPanelPiece getPieceAt(int x, int y) {
        Cell cell = board.getCellAt(x, y);
        return BoardPanelPiece.builder()
                .square(new Point(x, y))
                .isEmpty(cell.isEmpty())
                .isWhite(cell.isEmpty() || cell.getFigure().getColor() == Figure.Color.WHITE)
                .build();
    }

    public void movingPiece(String from, String to) {
        try {
            resetStatusLabel();
            game.move(from, to);
            checkGameStatus();

        } catch (GameException e) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(e.getMessage());
            log.error(e.getMessage());
        }
    }

    private void checkGameStatus() {
        WinCheckResponse gameStatus = game.getStatus();
        if (gameStatus.getStatus() == WinCheckResponse.Status.FINISHED) {
            Player winner = gameStatus.getWinner();
            JOptionPane.showMessageDialog(this, String.format("%s wins!", winner.getName()),
                    "Game Over", JOptionPane.PLAIN_MESSAGE);

        } else if (gameStatus.getStatus() == WinCheckResponse.Status.DEAD_HEAT) {
            JOptionPane.showMessageDialog(this, "Dead heat!",
                    "Game Over", JOptionPane.PLAIN_MESSAGE);
        } else {
            refreshCurrentPlayer();
        }
    }

    private void refreshCurrentPlayer() {
        String currentPlayer = "Current player: " + game.getActivePlayer().getName();
        currentPlayerLabel.setText(currentPlayer);
    }

    private void resetStatusLabel() {
        statusLabel.setText(" ");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("Quit")) {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        } else if (actionEvent.getActionCommand().equals("New Game")) {
            log.info("Starting new game...");
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GraphicInterface app = new GraphicInterface();
            app.setVisible(true);
        });
    }
}

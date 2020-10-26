package com.imsavva.checkers.server.gui;

import com.imsavva.checkers.server.beans.Figure;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

@Slf4j
public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private static final int BOARD_WIDTH = 450;
    private static final int BOARD_HEIGHT = 450;
    private static final int BOARD_MARGIN = 22;
    private static final int BOARD_SQUARE_LENGTH = 50;
    private static final int BOARD_SQUARE_PADDING = 1;

    private transient Image boardImage;
    private transient Image whitePieceImage;
    private transient Image blackPieceImage;

    private transient Point lastMousePos;
    private transient BoardPanelPiece movingBoardPanelPiece;
    private transient Point movingPiecePos;

    private transient BoardPanelListener boardPanelListener;

    public void initComponents() {
        // load images
        boardImage = getImage("board.jpg");
        whitePieceImage = getImage("white.png");
        blackPieceImage = getImage("black.png");

        // set panel size
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        // add listeners
        addMouseListener(this);
        addMouseMotionListener(this);

        // image refresh timer
        new Timer(10, this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw board
        g.drawImage(boardImage, 0, 0, this);
        markCurrentPlayer(g);

        // draw pieces
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (movingBoardPanelPiece == null || !movingBoardPanelPiece.isAt(x, y)) {
                    BoardPanelPiece piece = pieceAtSquare(new Point(x, y));
                    if (piece != null) {
                        Point piecePos = squareToPosition(piece.getSquare());
                        g.drawImage(piece.getImage(), piecePos.x, piecePos.y, this);
                    }
                }
            }
        }

        // draw moving piece
        if (movingBoardPanelPiece != null) {
            g.drawImage(movingBoardPanelPiece.getImage(), movingPiecePos.x, movingPiecePos.y, this);
        }

        // make sure we update the graphics
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Take a square on the board and map to image coordinates.
     * @param square Point representing square on the board. Y = row, X = col
     * @return Point holding component coordinates.
     */
    Point squareToPosition(Point square) {
        return new Point(
            BOARD_MARGIN + BOARD_SQUARE_PADDING +
                (square.x * (BOARD_SQUARE_LENGTH + BOARD_SQUARE_PADDING)),

            BOARD_MARGIN + BOARD_SQUARE_PADDING +
                (square.y * (BOARD_SQUARE_LENGTH + BOARD_SQUARE_PADDING)));
    }

    /**
     * Get square at the specified component coordinates.
     * @param pos The component coordinates.
     * @return The square as Y = row, and X = col. Or null out of bounds.
     */
    Point positionToSquare(Point pos) {
        int col = pos.x - BOARD_MARGIN - BOARD_SQUARE_PADDING;
        int row = pos.y - BOARD_MARGIN - BOARD_SQUARE_PADDING;

        if (col < 0 || row < 0) {
            return null;
        }

        col /= (BOARD_SQUARE_LENGTH + BOARD_SQUARE_PADDING);
        row /= (BOARD_SQUARE_LENGTH + BOARD_SQUARE_PADDING);

        if (col >= 8 || row >= 8) {
            return null;
        }

        return new Point(col, row);
    }

    /**
     * Get the piece at the specified square.
     * @param square Square as Y = row, X = col.
     * @return The piece or null if empty.
     */
    private BoardPanelPiece pieceAtSquare(Point square) {
        BoardPanelPiece piece = boardPanelListener.getPieceAt(square.x, square.y);
        if (piece.isEmpty()) {
            return null;
        }
        piece.setImage(piece.isWhite() ? whitePieceImage : blackPieceImage);
        return piece;
    }

    private void markCurrentPlayer(Graphics g) {
        g.setColor(Color.GREEN);
        if (boardPanelListener.getCurrentPlayer().getColor() == Figure.Color.WHITE) {
            g.fillRect(225, 20, 205, 3);
        } else {
            g.fillRect(20, 428, 205, 3);
        }
    }

    String getSquareName(Point square) {
        return String.format("%c%c", 'A' + square.x, '8' - square.y);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        // Check to see if we clicked within a square
        Point square = positionToSquare(mouseEvent.getPoint());
        if (square != null) {

            // check to see if there's a piece at that square
            movingBoardPanelPiece = pieceAtSquare(square);
            if (movingBoardPanelPiece != null) {
                log.debug("Picking up piece: {}", movingBoardPanelPiece);

                // save the mouse position so we can move the piece relative to the drag
                // and convert the square coordinates to actual component coordinates to
                // float the image while we drag the mouse.
                lastMousePos = mouseEvent.getPoint();
                movingPiecePos = squareToPosition(square);
            }

        } else {
            log.debug("Clicked outside the board.");
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // Nothing moving just exit
        if (movingBoardPanelPiece == null) {
            return;
        }

        // Check to see if we dropped the piece at an actual square,
        // and if we did, check to see that there's not another piece
        // already there.
        Point targetSquare = positionToSquare(mouseEvent.getPoint());
        if (boardPanelListener != null) {
            log.debug("Dropping piece: {} to ({}, {})", movingBoardPanelPiece,
                    targetSquare.x, targetSquare.y);
            boardPanelListener.movingPiece(
                    getSquareName(movingBoardPanelPiece.getSquare()),
                    getSquareName(targetSquare));
        }

        // Set moving piece to null so we don't paint it anymore.
        movingBoardPanelPiece = null;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        // check to see if we're moving a piece
        if (movingPiecePos != null) {
            // Update the moving piece coordinates
            // by the amount the mouse has moved.
            movingPiecePos.translate(
                    mouseEvent.getX() - lastMousePos.x,
                    mouseEvent.getY() - lastMousePos.y);

            // update the mouse's last position for next cycle.
            lastMousePos = mouseEvent.getPoint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // This method is called by the timer, and we just repaint the component.
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        /* No need to handle clicks */
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        /* No need to handle moves */
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        /* No need to test for enter */
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        /* No need to test for exit */
    }

    public static Image getImage(String filename) {
        URL url = BoardPanel.class.getResource("/" + filename);
        if (url == null) {
            throw new IllegalArgumentException("Image \"" + filename + "\" not found.");
        }
        return new ImageIcon(url).getImage();
    }

    public void addMoveListener(BoardPanelListener moveListener) {
        this.boardPanelListener = moveListener;
    }
}

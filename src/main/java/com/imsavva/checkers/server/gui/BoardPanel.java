package com.imsavva.checkers.server.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
class Piece {
    @Setter
    private Point square;
    private final Image image;
}

@Slf4j
public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private static final int BOARD_WIDTH = 450;
    private static final int BOARD_HEIGHT = 450;
    private static final int BOARD_MARGIN = 22;
    private static final int BOARD_SQUARE_LENGTH = 50;
    private static final int BOARD_SQUARE_PADDING = 1;

    private Image boardImage;
    private Point lastMousePos;
    private Piece movingPiece;
    private Point movingPiecePos;

    private java.util.List<Piece> pieceList;

    public BoardPanel initComponents() {
        // load images
        boardImage = getImage("board.jpg");
        Image whitePiece = getImage("white.png");
        Image blackPiece = getImage("black.png");

        // Initial piece positions
        pieceList = new ArrayList<>();
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 3; ++y) {
                pieceList.add(new Piece(new Point(4 + x, y), blackPiece));
                pieceList.add(new Piece(new Point(x, y + 5), whitePiece));
            }
        }

        // set panel size
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        // add listeners
        addMouseListener(this);
        addMouseMotionListener(this);

        // image refresh timer
        new Timer(10, this).start();

        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw board
        g.drawImage(boardImage, 0, 0, this);

        // draw pieces
        Point piecePos;
        for (Piece piece : pieceList) {
            if (movingPiece == null || movingPiece.getSquare() != piece.getSquare()) {
                piecePos = squareToPosition(piece.getSquare());
                g.drawImage(piece.getImage(), piecePos.x, piecePos.y, this);
            }
        }

        // draw moving piece
        if (movingPiece != null) {
            g.drawImage(movingPiece.getImage(), movingPiecePos.x, movingPiecePos.y, this);
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
    private Piece pieceAtSquare(Point square) {
        for (Piece p : pieceList) {
            if (p.getSquare().equals(square)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        // Check to see if we clicked within a square
        Point square = positionToSquare(mouseEvent.getPoint());
        if (square != null) {

            // check to see if there's a piece at that square
            movingPiece = pieceAtSquare(square);
            if (movingPiece != null) {

                // save the mouse position so we can move the piece relative to the drag
                // and convert the square coordinates to actual component coordinates to
                // float the image while we drag the mouse.
                lastMousePos = mouseEvent.getPoint();
                movingPiecePos = squareToPosition(square);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // Check to see if we dropped the piece at an actual square,
        // and if we did, check to see that there's not another piece
        // already there.
        Point square = positionToSquare(mouseEvent.getPoint());
        if (square != null && pieceAtSquare(square) == null) {
            movingPiece.setSquare(square);
        }

        // Set moving piece to null so we don't paint it anymore.
        movingPiece = null;
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
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    public static Image getImage(String filename) {
        URL url = BoardPanel.class.getResource("/" + filename);
        if (url == null) {
            throw new IllegalArgumentException("Image \"" + filename + "\" not found.");
        }
        return new ImageIcon(url).getImage();
    }
}

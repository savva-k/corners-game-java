package com.imsavva.checkers.server.gui;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class BoardPanelTest {
    private BoardPanel panel;

    @Before
    public void setup() {
        panel = new BoardPanel();
    }

    @Test
    public void getSquareNameTest() {
        assertThat(panel.getSquareName(new Point(0, 0)), is("A8"));
        assertThat(panel.getSquareName(new Point(7, 0)), is("H8"));
        assertThat(panel.getSquareName(new Point(7, 7)), is("H1"));
        assertThat(panel.getSquareName(new Point(0, 7)), is("A1"));
    }

    @Test
    public void squareToPositionText() {
        Point pos = panel.squareToPosition(new Point(0, 0));
        assertThat(pos.x, is(23));
        assertThat(pos.y, is(23));

        pos = panel.squareToPosition(new Point(7, 7));
        assertThat(pos.x, is(23 + (7 * 51)));
        assertThat(pos.y, is(23 + (7 * 51)));
    }

    @Test
    public void positionToSquareTest() {
        Point square = panel.positionToSquare(new Point(22, 22));
        assertThat(square, is(nullValue()));

        square = panel.positionToSquare(new Point(23, 23));
        assertThat(square.x, is(0));
        assertThat(square.y, is(0));

        square = panel.positionToSquare(new Point(23 + (7 * 51), 23 + (7 * 51)));
        assertThat(square.x, is(7));
        assertThat(square.y, is(7));

        square = panel.positionToSquare(new Point(23 + (8 * 51), 23 + (8 * 51)));
        assertThat(square, is(nullValue()));
    }
}

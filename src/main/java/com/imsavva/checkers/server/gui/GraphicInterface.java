package com.imsavva.checkers.server.gui;

import javax.swing.*;
import java.awt.*;

public class GraphicInterface extends JFrame {
    public GraphicInterface() {
        initUI();
    }

    public void initUI() {
        add(new BoardPanel().initComponents());

        pack();
        setResizable(false);

        setTitle("Corners Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GraphicInterface app = new GraphicInterface();
            app.setVisible(true);
        });
    }
}

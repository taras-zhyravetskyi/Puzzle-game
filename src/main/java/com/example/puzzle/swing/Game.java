package com.example.puzzle.swing;

import org.springframework.beans.factory.annotation.Value;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Game extends JFrame {
    private List<Point> solution;
    private List<PuzzleButton> buttons;
    private JPanel panel;
    private BufferedImage source;
    private BufferedImage resized;
    private int width;
    private int height;

    private final int DESIRED_WIDTH = 400;
    @Value("${app.columns}")
    private int columns;

    @Value("${app.rows}")
    private int rows;

    public Game() {
        initUI();
    }

    public void initUI() {
        solution = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                solution.add(new Point(i,j));
            }
        }

        buttons = new ArrayList<>();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new GridLayout(rows, columns));
    }
}

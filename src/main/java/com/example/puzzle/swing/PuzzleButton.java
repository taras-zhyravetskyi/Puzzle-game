package com.example.puzzle.swing;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
@Setter
public class PuzzleButton extends JButton {
    private boolean isLastButton;

    public PuzzleButton() {
        initUI();
    }

    public PuzzleButton(Image image) {
        super(new ImageIcon(image));
        initUI();
    }

    public void initUI() {
        isLastButton = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        });
    }
}

package com.example.puzzle.model;

import java.awt.image.BufferedImage;
import lombok.Data;

@Data
public class PuzzlePiece {
    private int originalX;
    private int originalY;
    private int currentX;
    private int currentY;
    private int rotation;
    private String imagePath;

    public PuzzlePiece(int originalX, int originalY, String imagePath) {
        this.originalX = originalX;
        this.originalY = originalY;
        this.currentX = originalX;
        this.currentY = originalY;
        this.rotation = 0;
        this.imagePath = imagePath;
    }
}

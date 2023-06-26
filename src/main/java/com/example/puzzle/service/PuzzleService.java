package com.example.puzzle.service;

import com.example.puzzle.model.Puzzle;
import com.example.puzzle.model.PuzzlePiece;

import java.awt.image.BufferedImage;
import java.util.List;

public interface PuzzleService {
    Puzzle createPuzzle(BufferedImage image);

    PuzzlePiece getPiece(String id);

    Puzzle shufflePieces(Puzzle puzzle);

    boolean checkSolution(Puzzle puzzle);
}

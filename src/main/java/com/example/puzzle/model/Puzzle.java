package com.example.puzzle.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Puzzle {
    private List<PuzzlePiece> pieces;
    private int width;
    private int height;
}

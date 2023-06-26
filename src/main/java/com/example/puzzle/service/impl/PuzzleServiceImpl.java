package com.example.puzzle.service.impl;

import com.example.puzzle.model.Puzzle;
import com.example.puzzle.model.PuzzlePiece;
import com.example.puzzle.service.PuzzleService;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PuzzleServiceImpl implements PuzzleService {
    private static final String IMAGES_PATH = "/images/";
    private static final String ABSOLUTE_IMAGES_PATH = "src/main/resources/static/images/";
    private static final int IMAGE_HEIGHT = 600;

    @Value("${app.columns}")
    private int columns;

    @Value("${app.rows}")
    private int rows;

    public Puzzle createPuzzle(BufferedImage image) {
        int pieceHeight = IMAGE_HEIGHT / rows;
        int pieceWidth = pieceHeight * (image.getWidth() / image.getHeight());

        ImagePlus imagePlus = new ImagePlus("Image", image);
        ImageProcessor ip = imagePlus.getProcessor();

        List<PuzzlePiece> pieces = new ArrayList<>();

        File outputDir = new File(ABSOLUTE_IMAGES_PATH);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                ip.setRoi(x * pieceWidth, y * pieceHeight, pieceWidth, pieceHeight);
                ImageProcessor pieceIp = ip.crop();
                BufferedImage pieceImage = pieceIp.getBufferedImage();

                String fileName = new StringBuilder().append(x).append("_").append(y).append(".jpg").toString();
                String relativePath = IMAGES_PATH + fileName;

                PuzzlePiece piece = new PuzzlePiece(x * pieceWidth, y * pieceHeight, relativePath);
                pieces.add(piece);

                File outputfile = new File(outputDir, fileName);

                try {
                    ImageIO.write(pieceImage, "jpg", outputfile);
                } catch (IOException e) {
                    throw new RuntimeException("Can`t divide image into puzzle", e);
                }
            }
        }
        return new Puzzle(pieces, image.getWidth(), image.getHeight());
    }

    @Override
    public PuzzlePiece getPiece(String id) {
        return null;
    }

    @Override
    public Puzzle shufflePieces(Puzzle puzzle) {
        // Змішуємо масив шматків пазла
        Collections.shuffle(puzzle.getPieces());

        // Оновлюємо координати шматків пазла
        for (PuzzlePiece piece : puzzle.getPieces()) {
            piece.setCurrentX(getRandomNumber(0, 500)); // Задайте межі координати X для переміщення шматка пазла
            piece.setCurrentY(getRandomNumber(0, 500)); // Задайте межі координати Y для переміщення шматка пазла
        }
        return puzzle;
    }

    @Override
    public boolean checkSolution(Puzzle puzzle) {
        for (PuzzlePiece piece : puzzle.getPieces()) {
            if (piece.getCurrentX() != piece.getOriginalX()
                    && piece.getCurrentY() != piece.getOriginalY()) {
                return false;
            }
        }
        return true;
    }

    private int getRandomNumber(int min, int max) {
        // Метод для отримання випадкового числа в заданому діапазоні
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private String getAbsolutePath(String relativePath) {
        Path resourceDirectory = Paths.get("src", "main", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        return absolutePath + File.separator + relativePath;
    }
}

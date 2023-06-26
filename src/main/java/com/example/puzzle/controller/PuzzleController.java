package com.example.puzzle.controller;

import com.example.puzzle.model.Puzzle;
import com.example.puzzle.service.PuzzleService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/puzzle")
@RequiredArgsConstructor
public class PuzzleController {
    private final PuzzleService puzzleService;

    @GetMapping()
    public String showPuzzle(Model model) {
        Puzzle puzzle = new Puzzle();
        model.addAttribute("puzzle", puzzle);
        return "puzzle";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image, Model model) {
        try {
            byte[] imageBytes = image.getBytes();

            ByteArrayInputStream bytes = new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(bytes);

            Puzzle puzzle = puzzleService.createPuzzle(bufferedImage);

            model.addAttribute("puzzle", puzzle);
            return "puzzle";
        } catch (Exception e) {
            model.addAttribute("error", "Error processing image: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping()
    public String handlePuzzleActions(@ModelAttribute("puzzle") Puzzle puzzle, @RequestParam(name = "action", required = false) String action, Model model) {
        if ("shuffle".equals(action)) {
            if (puzzle != null) {
                Puzzle shuffledPuzzle = puzzleService.shufflePieces(puzzle);
                model.addAttribute("puzzle", shuffledPuzzle);
            }
        } else if ("check-solution".equals(action)) {
            boolean solutionCorrect = puzzleService.checkSolution(puzzle);
            if (solutionCorrect) {
                model.addAttribute("message", "Congratulations! Puzzle solved!");
            } else {
                model.addAttribute("message", "The puzzle is not solved yet.");
            }
        }
        return "puzzle";
    }

    @GetMapping("/shuffle")
    public String shufflePieces(@ModelAttribute("puzzle") Puzzle puzzle, Model model) {
        if (puzzle != null) {
            Puzzle shuffledPuzzle = puzzleService.shufflePieces(puzzle);
            model.addAttribute("puzzle", shuffledPuzzle);
        }
        return "puzzle";
    }

    @GetMapping("/check-solution")
    public String checkSolution(@ModelAttribute("puzzle") Puzzle puzzle, Model model) {
        boolean solutionCorrect = puzzleService.checkSolution(puzzle);
        if (solutionCorrect) {
            model.addAttribute("message", "Congratulations! Puzzle solved!");
        } else {
            model.addAttribute("message", "The puzzle is not solved yet.");
        }
        return "puzzle";
    }
}

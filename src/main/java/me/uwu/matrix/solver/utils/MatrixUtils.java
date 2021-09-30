package me.uwu.matrix.solver.utils;

import me.uwu.matrix.solver.MatrixChar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class MatrixUtils {
    public static List<List<MatrixChar>> getMatrixCharsFromFile(File file) throws IOException {
        List<List<MatrixChar>> matrix = new ArrayList<>();
        List<String> fileData = Files.readAllLines(file.toPath());

        int y = 0;

        for (String data : fileData) {
            y++;
            if (data.startsWith("=")) break;
            String[] matrixLineData = data.split(",");
            List<MatrixChar> matrixLine = new ArrayList<>();
            int x = 0;
            for (String matrixData : matrixLineData) {
                x++;
                matrixLine.add(new MatrixChar(matrixData.toUpperCase().charAt(0), x, y));
            }
            matrix.add(matrixLine);
        }

        return matrix;
    }

    public static List<String> getWordsFromFile(File file) throws IOException {
        List<String> fileData = Files.readAllLines(file.toPath());
        List<String> words = new ArrayList<>();
        final boolean[] go = {false};
        fileData.forEach(line -> {
            if (go[0]) words.add(line.toUpperCase());
            if (line.contains("=")) go[0] = true;
        });
        words.sort(Comparator.comparingInt(String::length));
        Collections.reverse(words);
        return words;
    }
}

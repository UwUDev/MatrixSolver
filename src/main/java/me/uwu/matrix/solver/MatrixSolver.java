package me.uwu.matrix.solver;

import lombok.Getter;
import me.uwu.matrix.solver.utils.MatrixColor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MatrixSolver {
    private final List<String> words;
    private final List<List<MatrixChar>> matrix;
    private final MatrixManager manager;
    private final int matrixSizeX, matrixSizeY;

    public MatrixSolver(List<String> words, List<List<MatrixChar>> matrix) {
        this.words = words;
        this.matrix = matrix;
        this.manager = new MatrixManager(matrix);
        this.matrixSizeX = matrix.get(0).size();
        this.matrixSizeY = matrix.size();
    }

    public void solve(boolean debug) {
        System.out.println(words.size());
        System.out.println("Matrix :" + matrixSizeX + "x" + matrixSizeY + " loaded.");

        long startTimestamp = System.nanoTime();
        if (debug)
            printMatrix();
        if (words.size() > 0)
            solveLines(debug);
        if (words.size() > 0)
            solveColumns(debug);
        if (words.size() > 0)
            solveSlash(debug);
        if (words.size() > 0)
            solveBackSlash(debug);
        long endTimestamp = System.nanoTime();

        printMatrix();
        System.out.println("Took: " + ((endTimestamp / 1000) - (startTimestamp / 1000)) + "µs.");

        if (words.size() > 0){
            System.out.println("\n\u001B[33m!!!\u001B[31m   Uh oh... Some words are note found :/   \u001B[33m!!!\u001B[31m   \nWords not found: ");
            words.forEach(w -> System.out.println("- " + w));
            System.out.print("\u001B[0m");
        }
        System.out.println();

        StringBuilder endLetters = new StringBuilder();
        StringBuilder endWord = new StringBuilder();
        for (MatrixChar matrixChar : manager.getUnvalidatedColoredString()) {
            endLetters.append(matrixChar.getColoredCharacter()).append(" ");
            endWord.append(matrixChar.getCharacter());
            System.out.print("\rRemaining letters: " + endLetters);

            try { Thread.sleep(250);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        if (words.size() == 0)
            System.out.println("\nWord should be: " + MatrixColor.VALIDATED.getColor() + endWord.substring(0, 1).toUpperCase() + endWord.substring(1).toLowerCase());
    }

    private void solveLines(boolean debug){
        List<String> toRemove = new ArrayList<>();
        for (String word : words) {
            //Ouaishe ca marche pas il a remis le mot ce chacal
            //System.out.println(word);
            //printMatrix();

            boolean exit = false;
            for (int lineIndex = 1; lineIndex <= matrixSizeY; lineIndex++) {
                if (exit) break;
                for (int from = 1; from <= matrixSizeX + 1 - word.length(); from++) {
                    int to = from + word.length() - 1;
                    String tempWord = manager.getStringFromLine(lineIndex, from, to);
                    //System.out.println(tempWord);
                    if (word.equals(tempWord) || word.equals(invertWord(tempWord))){
                        List<MatrixChar> tempMatrixChars = manager.getCharsFromLine(lineIndex, from, to);
                        if (manager.hasAlreadyValidatedChars(tempMatrixChars)) continue;

                        manager.validate(tempMatrixChars);
                        exit = true;
                        if (debug)
                            printMatrix();
                        toRemove.add(word);
                        break;
                    }
                }
            }
        }
        removeWords(toRemove);
        toRemove.clear();
    }

    private void solveColumns(boolean debug){
        List<String> toRemove = new ArrayList<>();
        for (String word : words) {
            //System.out.println(word);
            boolean exit = false;
            for (int rowIndex = 1; rowIndex <= matrixSizeX; rowIndex++) {
                if (exit) break;
                for (int from = 1; from <= matrixSizeY + 1 - word.length(); from++) {
                    int to = from + word.length();
                    //System.out.println(rowIndex + ";" + from + ";" + to);
                    String tempWord = manager.getStringFromColumn(rowIndex, from, to);
                    //System.out.println(tempWord);
                    if (word.equals(tempWord) || word.equals(invertWord(tempWord))){
                        List<MatrixChar> tempMatrixChars = manager.getCharsFromColumn(rowIndex, from, to);
                        if (manager.hasAlreadyValidatedChars(tempMatrixChars)) continue;

                        manager.validate(tempMatrixChars);
                        exit = true;
                        if (debug)
                            printMatrix();
                        toRemove.add(word);
                        break;
                    }
                }
            }
        }
        removeWords(toRemove);
        toRemove.clear();
    }

    public void solveSlash(boolean debug) {
        List<String> toRemove = new ArrayList<>();
        for (String word : words) {
            boolean exit = false;
            for (List<MatrixChar> slashedRow : manager.getSlashedRows()) {
                //noinspection DuplicatedCode
                if (exit) break;
                StringBuilder sb = new StringBuilder();
                for (MatrixChar matrixChar : slashedRow) {
                    sb.append(matrixChar.getCharacter());
                }
                if (sb.toString().contains(word)){
                    for (int index = 0; index < word.length(); index++) {
                        List<MatrixChar> tempMatrix= new ArrayList<>();
                        StringBuilder sb2 = new StringBuilder();
                        for (int i = index; i < word.length() + index; i++) {
                            tempMatrix.add(slashedRow.get(i));
                            sb2.append(slashedRow.get(i).getCharacter());
                        }
                        if (sb2.toString().equals(word) || sb2.toString().equals(invertWord(word))){
                            if (manager.hasAlreadyValidatedChars(tempMatrix)) continue;

                            manager.validate(tempMatrix);
                            exit = true;
                            if (debug)
                                printMatrix();
                            toRemove.add(word);
                            break;
                        }
                    }

                }
            }
        }
        removeWords(toRemove);
    }

    public void solveBackSlash(boolean debug) {
        List<String> toRemove = new ArrayList<>();
        for (String word : words) {
            boolean exit = false;
            for (List<MatrixChar> slashedRow : manager.getBackSlashedRows()) {
                //noinspection DuplicatedCode
                if (exit) break;
                StringBuilder sb = new StringBuilder();
                for (MatrixChar matrixChar : slashedRow) {
                    sb.append(matrixChar.getCharacter());
                }
                if (sb.toString().contains(word)){
                    for (int index = 0; index < word.length(); index++) {
                        List<MatrixChar> tempMatrix= new ArrayList<>();
                        StringBuilder sb2 = new StringBuilder();
                        for (int i = index; i < word.length() + index; i++) {
                            tempMatrix.add(slashedRow.get(i));
                            sb2.append(slashedRow.get(i).getCharacter());
                        }
                        if (sb2.toString().equals(word) || sb2.toString().equals(invertWord(word))){
                            if (manager.hasAlreadyValidatedChars(tempMatrix)) continue;

                            manager.validate(tempMatrix);
                            exit = true;
                            if (debug)
                                printMatrix();
                            toRemove.add(word);
                            break;
                        }
                    }

                }
            }
        }
        removeWords(toRemove);
    }

    public void removeWords(List<String> wordList){
        wordList.forEach(words::remove);
    }

    public void printMatrix(){
        StringBuilder sbDivider = new StringBuilder();
        for (int i = 0; i < matrixSizeX * 2 - 1; i++)
            sbDivider.append('=');
        System.out.println(sbDivider);
        for (List<MatrixChar> matrixChars : matrix) {
            StringBuilder sb = new StringBuilder();
            for (MatrixChar character : matrixChars)
                sb.append(character.getColoredCharacter()).append(" ");
            System.out.println(sb);
        }
        System.out.println(sbDivider);
        System.out.flush();
    }

    public String invertWord(String word){
        return new StringBuilder(word).reverse().toString();
    }
}

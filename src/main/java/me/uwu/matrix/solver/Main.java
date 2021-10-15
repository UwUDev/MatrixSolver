package me.uwu.matrix.solver;

import me.uwu.matrix.solver.utils.MatrixUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean validFile = false;
        Scanner sc = new Scanner(System.in);
        File dataFile = null;
        while (!validFile) {
            System.out.print("\u001B[34mMatrix file name: ");
            dataFile = new File(sc.nextLine());
            if (dataFile.exists())
                validFile = true;
            else System.out.print("\u001B[31mCannot find matrix file.\n");
        }
        System.out.print("Debug mode ? (y/n)");

        final String tempData = sc.nextLine();
        boolean debug = tempData.equalsIgnoreCase("y") ||
                tempData.equalsIgnoreCase("yes");

        System.out.print("\u001B[0m");
        MatrixSolver matrixSolver = new MatrixSolver(MatrixUtils.getWordsFromFile(dataFile), MatrixUtils.getMatrixCharsFromFile(dataFile));
        matrixSolver.solve(debug);
    }
}

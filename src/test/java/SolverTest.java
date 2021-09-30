import me.uwu.matrix.solver.MatrixSolver;
import me.uwu.matrix.solver.utils.MatrixUtils;

import java.io.File;
import java.io.IOException;

public class SolverTest {
    public static void main(String[] args) throws IOException {
        boolean debug = false;
        File dataFile = new File("matrix.txt");
        MatrixSolver matrixSolver = new MatrixSolver(MatrixUtils.getWordsFromFile(dataFile), MatrixUtils.getMatrixCharsFromFile(dataFile));
        matrixSolver.solve(debug);
    }
}

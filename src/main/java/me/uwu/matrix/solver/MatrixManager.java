package me.uwu.matrix.solver;

import lombok.Getter;
import lombok.Setter;
import me.uwu.matrix.solver.obj.Line;
import me.uwu.matrix.solver.obj.Point;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MatrixManager {
    private final List<List<MatrixChar>> matrix;
    private final List<MatrixChar> rawMatrix = new ArrayList<>();
    private final int matrixSizeX, matrixSizeY;

    public MatrixManager(List<List<MatrixChar>> matrix) {
        this.matrix = matrix;
        matrixToRaw();
        this.matrixSizeX = matrix.get(0).size();
        this.matrixSizeY = matrix.size();
    }

    public List<MatrixChar> getCharsFromColumn(int columnIndex, int from, int to){
        List<MatrixChar> chars = new ArrayList<>();
        for (int i = 0; i < (to - from); i++)
            chars.add(getCharacter(columnIndex, from + i));
        return chars;
    }

    public List<MatrixChar> getCharsFromLine(int lineIndex, int from, int to){
        List<MatrixChar> chars = new ArrayList<>();
        for (int i = 0; i < (to + 1 - from); i++)
            chars.add(getCharacter(from + i, lineIndex));
        return chars;
    }

    public String getStringFromColumn(int columnIndex, int from, int to){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (to - from); i++) {
            sb.append(getCharacter(columnIndex, from + i).getCharacter());
        }
        return sb.toString();
    }

    public String getStringFromLine(int lineIndex, int from, int to){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (to + 1 - from); i++)
            sb.append(getCharacter(from + i, lineIndex).getCharacter());
        return sb.toString();
    }

    public String getColoredStringFromColumn(int columnIndex, int from, int to){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (to - from); i++)
            sb.append(getCharacter(columnIndex, from + i).getColoredCharacter());
        return sb.toString();
    }

    public String getColoredStringFromLine(int lineIndex, int from, int to){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (to + 1 - from); i++)
            sb.append(getCharacter(from + i, lineIndex).getColoredCharacter());
        return sb.toString();
    }

    private void matrixToRaw() {
        rawMatrix.clear();
        matrix.forEach(rawMatrix::addAll);
    }

    public List<List<MatrixChar>> getSlashedRows(){
        List<List<MatrixChar>> chars = new ArrayList<>();
        Point pointA = new Point(1,1);
        Point pointB = new Point(1,1);
        for (int i = 0; i < matrixSizeX + matrixSizeY - 2; i++) {
            if (pointA.getX() == matrixSizeX){
                if (pointA.getY() != matrixSizeY)
                    pointA.setY(pointA.getY() + 1);
            } else pointA.setX(pointA.getX() + 1);

            if (pointB.getY() == matrixSizeY){
                if (pointB.getX() != matrixSizeX)
                    pointB.setX(pointB.getX() + 1);
            } else pointB.setY(pointB.getY() + 1);
            chars.add(pointsToChars(new Line(pointA, pointB, false).getPoints()));
            /*StringBuilder sb = new StringBuilder();
            for (MatrixChar matrixChar : tempMatrix) {
                sb.append(matrixChar.getCharacter());
            }
            System.out.println(sb);*/
            //System.out.println(pointA);
            //System.out.println(pointB);
            //System.out.println(new Line(pointA, pointB));
        }
        return chars;
    }

    public List<List<MatrixChar>> getBackSlashedRows(){
        List<List<MatrixChar>> chars = new ArrayList<>();
        Point pointA = new Point(1, matrixSizeY);
        Point pointB = new Point(1, matrixSizeY);
        for (int i = 0; i < matrixSizeX + matrixSizeY - 2; i++) {
            if (pointA.getY() == 1){
                if (pointA.getX() != matrixSizeX)
                    pointA.setX(pointA.getX() + 1);
            } else pointA.setY(pointA.getY() - 1);

            if (pointB.getX() == matrixSizeX){
                if (pointB.getY() != 1)
                    pointB.setY(pointB.getY() - 1);
            } else pointB.setX(pointB.getX() + 1);
            chars.add(pointsToChars(new Line(pointA, pointB, true).getPoints()));
            /*StringBuilder sb = new StringBuilder();
            for (MatrixChar matrixChar : tempMatrix) {
                sb.append(matrixChar.getCharacter());
            }
            System.out.println(sb);*/
            //System.out.println(pointA);
            //System.out.println(pointB);
            //System.out.println(new Line(pointA, pointB, true));
        }
        return chars;
    }

    public List<MatrixChar> pointsToChars(List<Point> points){
        List<MatrixChar> chars = new ArrayList<>();
        points.forEach(p -> chars.add(getCharacter(p.getX(), p.getY())));
        return chars;
    }

    public MatrixChar getCharacter(int x, int y){
        for (MatrixChar matrixChar : rawMatrix) {
            if (matrixChar.getX() == x && matrixChar.getY() == y){
                return matrixChar;
            }
        }
        return null;
    }

    public void validate(List<MatrixChar> matrixChars){
        matrixChars.forEach(MatrixChar::validate);
    }

    public List<MatrixChar> getUnvalidatedColoredString() {
        List<MatrixChar> chars = new ArrayList<>();
        for (MatrixChar matrixChar : rawMatrix) {
            if (!matrixChar.isSolved()) chars.add(matrixChar);
        }
        return chars;
    }

    public boolean hasAlreadyValidatedChars(List<MatrixChar> chars) {
        for (MatrixChar mChar : chars) {
            if (mChar.isSolved()) return true;
        }
        return false;
    }
}

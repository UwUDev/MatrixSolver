package me.uwu.matrix.solver;

import lombok.Getter;
import me.uwu.matrix.solver.utils.MatrixColor;

@Getter
public class MatrixChar {
    private final char character;
    private final int x, y;
    private boolean solved = false;

    public MatrixChar(char character, int x, int y) {
        this.character = character;
        this.x = x;
        this.y = y;
    }

    public String getColor(){
        if (solved)
            return MatrixColor.VALIDATED.getColor();
        return MatrixColor.NOT_VALIDATED.getColor();
    }

    public String getColoredCharacter(){
        return getColor() + character + MatrixColor.RESET.getColor();
    }

    public void validate(){
        solved = true;
    }
}

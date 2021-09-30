package me.uwu.matrix.solver.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum MatrixColor {
    VALIDATED("\u001B[32m"),
    NOT_VALIDATED("\u001B[31m"),
    RESET("\u001B[0m");

    private final String color;
}

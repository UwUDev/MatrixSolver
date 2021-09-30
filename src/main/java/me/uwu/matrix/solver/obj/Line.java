package me.uwu.matrix.solver.obj;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Line {
    private final List<Point> points;

    public Line(Point pointA, Point pointB, boolean reversed) {
        this.points = new ArrayList<>();
        int distance = Math.abs(pointA.getX() - pointB.getX());

        if (!reversed)
            for (int i = 0; i < distance; i++)
                points.add(new Point(pointA.getX() - i, pointA.getY() + i));
        else
            for (int i = 0; i < distance; i++)
                points.add(new Point(pointA.getX() + i, pointA.getY() + i));

        points.add(pointB);
    }
}

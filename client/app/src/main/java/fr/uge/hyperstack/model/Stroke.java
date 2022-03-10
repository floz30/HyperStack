package fr.uge.hyperstack.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.io.Serializable;
import java.util.LinkedList;

public class Stroke implements Serializable, Element {

    private int color;
    private int strokeSize;
    private transient Path pathOfStroke = new Path();

    private LinkedList<float[]> pathPoints = new LinkedList<>();

    public Stroke(int color, int strokeSize) {
        this.color = color;
        this.strokeSize = strokeSize;
    }

    public Path getPathOfStroke() {
        return pathOfStroke;
    }

    public void moveTo(float x, float y) {
        getPathOfStroke().moveTo(x, y);
    }

    public void lineTo(float x, float y) {
        getPathOfStroke().lineTo(x, y);
    }

    @Override
    public int getPositionX() {
        return 0;
    }

    @Override
    public int getPositionY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void drawElement(Canvas canvas, Paint paint) {
        paint.setColor(this.color);
        paint.setStrokeWidth(this.strokeSize);
        canvas.drawPath(this.pathOfStroke, paint);
    }
}

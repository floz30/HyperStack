package fr.uge.hyperstack.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

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

    public void setPathOfStroke() {
        Path path = new Path();
        if (!pathPoints.isEmpty()) {
            float[] firstPoint = pathPoints.getFirst();
            path.moveTo(firstPoint[0], firstPoint[1]);
            for (int i = 1; i < pathPoints.size(); i++) {
                path.lineTo(pathPoints.get(i)[0], pathPoints.get(i)[1]);
            }
            this.pathOfStroke = path;
        }
    }

    @Override
    public void onFingerMoveAction(float x, float y) {
        lineTo(x, y);
    }

    public void moveTo(float x, float y) {
        pathOfStroke.moveTo(x, y);
        pathPoints.add(new float[]{x, y});
    }

    public void lineTo(float x, float y) {
        pathOfStroke.lineTo(x, y);
        pathPoints.add(new float[]{x, y});

    }

    @Override
    public float getPositionX() {
        return 0;
    }

    @Override
    public float getPositionY() {
        return 0;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public void drawElement(Canvas canvas, Paint paint) {
        paint.setColor(this.color);
        paint.setStrokeWidth(this.strokeSize);
        canvas.drawPath(this.pathOfStroke, paint);
    }
}

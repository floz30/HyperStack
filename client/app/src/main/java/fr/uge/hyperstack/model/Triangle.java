package fr.uge.hyperstack.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Triangle extends AbstractFigure {
    private Point pointA;
    private Point pointB;
    public Triangle(int color, int strokeSize, Point origin, Point pointA, Point pointB) {
        super(color, strokeSize, origin);
        this.pointA = pointA;
        this.pointB = pointB;
    }

    @Override
    public float getWidth() {
        return Math.abs(Math.abs(pointB.getX()) - Math.abs(getPositionX()));
    }

    @Override
    public float getHeight() {
        return Math.abs(Math.abs(pointA.getY()) - Math.abs(getPositionY()));
    }

    @Override
    public void drawElement(Canvas canvas, Paint paint) {
        paint.setColor(getColor());
        paint.setStrokeWidth(getStrokeSize());
        canvas.drawLine(getPositionX(),getPositionY(),pointA.getX(),pointA.getY(),paint);
        canvas.drawLine(getPositionX(),getPositionY(),pointB.getX(),pointB.getY(),paint);
        canvas.drawLine(pointA.getX(),pointA.getY(),pointB.getX(),pointB.getY(),paint);
    }

    @Override
    public void setPathOfStroke() {

    }

    @Override
    public void onFingerMoveAction(float x, float y) {
        setPointA(new Point(getPositionX(),y));
        setPointB(new Point(x,getPositionY()));
    }

    @Override
    public boolean containsPoint(float x, float y) {
        return false;
    }

    @Override
    public float getArea() {
        return (getHeight()*getWidth())/2f;
    }

    public void setPointA(Point pointA) {
        this.pointA = pointA;
    }

    public void setPointB(Point pointB) {
        this.pointB = pointB;
    }
}

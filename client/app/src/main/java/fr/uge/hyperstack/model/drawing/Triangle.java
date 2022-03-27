package fr.uge.hyperstack.model.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import fr.uge.hyperstack.model.ElementVisitor;

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
    public float convertBiasHorizontal(float width) {
        return 0;
    }

    @Override
    public float convertBiasVertical(float height) {
        return 0;
    }

    @Override
    public void accept(ElementVisitor elementVisitor) {
        elementVisitor.draw(this);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getColor());
        paint.setStrokeWidth(getStrokeSize());
        canvas.drawLine(getPositionX(), getPositionY(), pointA.getX(), pointA.getY(), paint);
        canvas.drawLine(getPositionX(), getPositionY(), pointB.getX(), pointB.getY(), paint);
        canvas.drawLine(pointA.getX(), pointA.getY(), pointB.getX(), pointB.getY(), paint);
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
        float d1, d2, d3;
        boolean has_neg, has_pos;
        Point pt = new Point(x,y);

        d1 = sign(pt, getOrigin(), pointA);
        d2 = sign(pt, pointA, pointB);
        d3 = sign(pt, pointB, getOrigin());

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

    @Override
    public void moveTo(float width, float height) {
        setPointA(new Point(pointA.getX() + width,pointA.getY() + height));
        setPointB(new Point(pointB.getX() + width,pointB.getY() + height));
        setOrigin(new Point(getPositionX() + width,getPositionY() + height));
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

    float sign (Point p1, Point p2, Point p3)
    {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

}

package fr.uge.hyperstack.model.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle extends AbstractFigure{
    private Point endPoint;

    public Circle(int color, Point endPoint, int strokeSize, Point origin) {
        super(color, strokeSize, origin);
        this.endPoint = endPoint;
    }

    @Override
    public float getWidth() {
        return Math.abs(Math.abs(endPoint.getX()) - Math.abs(getPositionX()));
    }

    @Override
    public float getHeight() {
        return Math.abs(Math.abs(endPoint.getY()) - Math.abs(getPositionY()));
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(getColor());
        paint.setStrokeWidth(getStrokeSize());
        canvas.drawCircle((endPoint.getX() + getPositionX())/2f, (getPositionY() + endPoint.getX())/2f,getWidth()/2f, paint);
    }

    @Override
    public void onFingerMoveAction(float x, float y) {
        setEndPoint(new Point(x,y));
    }

    @Override
    public void setPathOfStroke() {

    }

    @Override
    public boolean containsPoint(float x, float y) {
        return false;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public float getArea() {
        float rayon = getWidth()/2f;
        return (float) (Math.PI * rayon * rayon);
    }
}

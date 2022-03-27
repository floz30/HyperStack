package fr.uge.hyperstack.model.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import fr.uge.hyperstack.model.ElementVisitor;

public class Rectangle extends AbstractFigure {
    private Point endPoint;


    public Rectangle(Point origin, Point endPoint, int color, int strokeSize) {
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
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getColor());
        paint.setStrokeWidth(getStrokeSize());
        canvas.drawRect(getPositionX(), getPositionY(), endPoint.getX(), endPoint.getY(), paint);
    }

    @Override
    public void setPathOfStroke() {

    }

    @Override
    public void onFingerMoveAction(float x, float y) {
        setEndPoint(new Point(x,y));
    }

    @Override
    public boolean containsPoint(float x, float y) {
        if(x > Math.min(endPoint.getX(),getPositionX()) && x < Math.max(endPoint.getX(),getPositionX())){
            return y > Math.min(endPoint.getY(), getPositionY()) && y < Math.max(endPoint.getY(), getPositionY());
        }
        return false;
    }

    @Override
    public void moveTo(float width, float height) {
        setOrigin(new Point(getPositionX()+width,getPositionY()+height));
        endPoint = new Point(endPoint.getX() + width, endPoint.getY() + height);
    }

    @Override
    public float getArea() {
        return getHeight()*getWidth();
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}

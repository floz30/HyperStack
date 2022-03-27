package fr.uge.hyperstack.model.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import fr.uge.hyperstack.model.ElementVisitor;

public class Circle extends AbstractFigure{
    private float radius;

    public Circle(int color, Point endPoint, int strokeSize, Point origin) {
        super(color, strokeSize, origin);
        this.radius = (float) Math.sqrt(Math.pow(getPositionX()-endPoint.getX(),2) + Math.pow(getPositionY()- endPoint.getY(),2));
    }

    @Override
    public float getWidth() {
        return radius;
    }

    @Override
    public float getHeight() {
        return radius;
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
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getStrokeSize());
        canvas.drawCircle(getPositionX(), getPositionY(),radius, paint);
    }

    @Override
    public void onFingerMoveAction(float x, float y) {
        this.radius = (float) Math.sqrt(Math.pow(getPositionX() - x,2) + Math.pow(getPositionY()- y,2));
    }

    @Override
    public void setPathOfStroke() {

    }

    @Override
    public boolean containsPoint(float x, float y) {
        return Math.sqrt(Math.pow(x-getPositionX(),2) + Math.pow(y-getPositionY(),2)) <= radius;
    }

    @Override
    public void moveTo(float width, float height) {
        setOrigin(new Point(getPositionX()+width,getPositionY()+height));
    }

    @Override
    public float getArea() {
        float rayon = getWidth()/2f;
        return (float) (Math.PI * rayon * rayon);
    }
}

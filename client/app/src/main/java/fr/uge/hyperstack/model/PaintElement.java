package fr.uge.hyperstack.model;


import android.graphics.Canvas;
import android.graphics.Paint;

public interface PaintElement extends Element {

    /**
     * Dessine l'élément.
     */
    void draw(Canvas canvas, Paint paint);

    int getColor();

    int getStrokeSize();

    void onFingerMoveAction(float x, float y);

    void setPathOfStroke();

}

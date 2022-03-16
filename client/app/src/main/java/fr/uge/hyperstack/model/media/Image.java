package fr.uge.hyperstack.model.media;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import fr.uge.hyperstack.model.MediaElement;

/**
 * Représente une image importée dans l'application.
 */
public class Image implements MediaElement {
    private final Bitmap content;


    public Image(@NonNull Bitmap content) {
        this.content = content;
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

//    @Override
//    public void draw(Canvas canvas, Paint paint) {
//        canvas.drawBitmap(content, 0, 0, paint); // TODO
//    }
}

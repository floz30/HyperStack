package fr.uge.hyperstack.model.media;

import android.graphics.Bitmap;

import java.io.Serializable;

import androidx.annotation.NonNull;
import fr.uge.hyperstack.model.ElementVisitor;
import fr.uge.hyperstack.model.MediaElement;

/**
 * Représente une image importée dans l'application.
 */
public class Image implements MediaElement, Serializable {
    private transient Bitmap content = null;
    private float x;
    private float y;
    private float width;
    private float height;

    public Image(@NonNull Bitmap content) {
        this.content = content;
    }

    @Override
    public float convertBiasHorizontal(float maxWidth) {
        return (x + width / 2) / maxWidth;
    }

    @Override
    public float convertBiasVertical(float maxHeight) {
        return (y + height / 2) / maxHeight;
    }


    @Override
    public float getPositionX() {
        return x;
    }

    @Override
    public float getPositionY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public Bitmap getContent() {
        return content;
    }

    @Override
    public void accept(ElementVisitor elementVisitor) {
        elementVisitor.draw(this);
    }
}

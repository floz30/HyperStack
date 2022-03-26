package fr.uge.hyperstack.model.media;

import android.net.Uri;

import androidx.annotation.NonNull;

import fr.uge.hyperstack.model.ElementVisitor;
import fr.uge.hyperstack.model.MediaElement;

public class Video implements MediaElement {
    private final Uri location;
    private float x;
    private float y;
    private float width;
    private float height;

    public Video(@NonNull Uri location) {
        this.location = location;
    }

    public Uri getLocation() {
        return location;
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

    @Override
    public float convertBiasHorizontal(float maxWidth) {
        return (x + width / 2) / maxWidth;
    }

    @Override
    public float convertBiasVertical(float maxHeight) {
        return (y + height / 2) / maxHeight;
    }

    @Override
    public void accept(ElementVisitor elementVisitor) {
        elementVisitor.draw(this);
    }

}

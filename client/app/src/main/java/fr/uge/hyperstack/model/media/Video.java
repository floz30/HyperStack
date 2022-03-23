package fr.uge.hyperstack.model.media;

import android.net.Uri;

import androidx.annotation.NonNull;
import fr.uge.hyperstack.model.MediaElement;

public class Video implements MediaElement {
    private final Uri location;

    public Video(@NonNull Uri location) {
        this.location = location;
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

}
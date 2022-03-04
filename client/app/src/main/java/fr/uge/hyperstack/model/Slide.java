package fr.uge.hyperstack.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Slide {
    private final String title;
    private transient Bitmap cachedImage = null;

    public Slide(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImage(Context context) {
        if (cachedImage != null) return cachedImage;
        try (InputStream is = context.getAssets().open("data/cheval.png")) {
            Bitmap b = BitmapFactory.decodeStream(is);
            cachedImage = b;
            return b;
        } catch (IOException e) {
            System.err.println("ERROR");
            return null;
        }
    }
}

package fr.uge.hyperstack.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * Classe représentant une pile d'une ou plusieurs planches.<br/>
 * Une sorte de présentation sur PowerPoint.
 *
 * @see Slide
 */
public class Stack implements Serializable {
    /**
     * Titre de la présentation.
     */
    private String title;

    /**
     * Nom de l'utilisateur qui a créé cette présentation.
     */
    private String creator;

    /**
     * Timestamp du moment de la création de cette présentation.
     */
    private long creationTime;

    /**
     * Ensemble des diapositives contenu dans cette présentation.
     */
    private List<Slide> slides;

    /**
     * Image représentant la première diapositive de cette présentation.
     */
    private transient Bitmap cachedImage = null;


    public Stack(@NonNull String title) {
        this.slides = new ArrayList<>();
        this.title = Objects.requireNonNull(title);
        this.creationTime = System.currentTimeMillis();
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

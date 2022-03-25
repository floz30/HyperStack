package fr.uge.hyperstack.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import fr.uge.hyperstack.Logs;
import fr.uge.hyperstack.view.EditorView;

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


    private ArrayList<Logs> logs = new ArrayList<>();

    public Stack(@NonNull String title) {
        this.slides = new ArrayList<>();
        this.slides.add(new Slide(1));
        this.title = Objects.requireNonNull(title);
        this.creationTime = System.currentTimeMillis();
    }


    public void initSlideLayer(Context context, ConstraintLayout layout){
        for(Slide slide : slides){
            slide.createLayer(context, layout);
        }
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

    public List<Slide> getSlides() {
        return slides;
    }

    public void addLayerElementToSlide(PaintElement element, int index) {
        slides.get(index).addElementOnLayer(element);
    }

    public ArrayList<Logs> getLogs() {
        return logs;
    }

    public void addLayerElementToSlide(Layer layer, int index) {
        slides.get(index).addLayer(layer);
        logs.add(new Logs(this));
        Log.d("TAG", "Draw: YES");
    }

    public void drawSlide(int index) {
        slides.get(index).drawLayers();
    }

    public void addNewSlide() {
        slides.add(new Slide(slides.size() + 1));
    }

    public int sizeOfStack() {
        return slides.size();
    }

    public void setDrawableElements(){
        for (Slide slide : slides) {
            slide.setDrawableElements();
        }
    }

    public void resetSlide(int index) {
        slides.set(index, new Slide(index));
        logs.add(new Logs(this));
        Log.d("TAG", "reset: YES");

    }
}

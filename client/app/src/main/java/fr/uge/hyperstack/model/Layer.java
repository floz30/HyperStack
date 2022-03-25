package fr.uge.hyperstack.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import fr.uge.hyperstack.model.media.Video;
import fr.uge.hyperstack.view.EditorView;
import fr.uge.hyperstack.view.SlideView;

/**
 * Classe représentant un calque qui contient un ou plusieurs éléments.
 * <p>
 * Un calque peut être partagés par plusieurs diapositives.
 *
 * @see Slide
 * @see PaintElement
 */
public class Layer implements Serializable  {
    /**
     * Ensemble des éléments présents sur ce calque.
     */
    private List<PaintElement> elements;

    private List<Video> videos;

    /**
     * La view sur laquelle le calque dessinera ses éléments.
     */
    private SlideView currentView;
    private ConstraintLayout layout;


    public Layer(Context context, ConstraintLayout layout) {
        this.elements = new ArrayList<>();
        this.videos = new ArrayList<>();
        this.currentView = new SlideView(context, layout);
        this.layout = layout;
    }


    public void addPaintElement(@NonNull PaintElement paintElement) {
        elements.add(paintElement);
    }

    public void draw(Canvas canvas) {
        draw();
//        for (PaintElement element : elements) {
//            element.draw(canvas);
//        }
    }

    public void draw() {
        currentView.drawImage();

        layout.addView(currentView);
//        for (PaintElement element : elements) {
//
//        }
    }

    public void setDrawableElements() {
        for (Element element : elements) {
            if (element instanceof PaintElement) {
                ((PaintElement) element).setPathOfStroke();
            }
        }
    }


    public Memento save() {
        return new Memento(elements);
    }

    public void restore(Memento m) {
        Objects.requireNonNull(m);
        this.elements = m.state;
    }


    class Memento {
        private final List<PaintElement> state;

        public Memento(List<PaintElement> elements) {
            Objects.requireNonNull(elements);
            this.state = elements;
        }

        public List<PaintElement> getState() {
            return state;
        }
    }
}

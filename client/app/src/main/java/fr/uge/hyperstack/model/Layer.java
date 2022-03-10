package fr.uge.hyperstack.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Classe représentant un calque qui contient un ou plusieurs éléments.
 * <p>
 * Un calque peut être partagés par plusieurs diapositives.
 *
 * @see Slide
 * @see Element
 */
public class Layer {
    /**
     * Ensemble des éléments présents sur ce calque.
     */
    private List<Element> elements;


    public Layer() {
        this.elements = new ArrayList<>();
    }


    public void addElement(@NonNull Element element) {
        this.elements.add(element);
    }

    public void drawElements(Canvas canvas, Paint paint) {
        for (Element element : elements) {
            element.drawElement(canvas, paint);
        }
    }
}

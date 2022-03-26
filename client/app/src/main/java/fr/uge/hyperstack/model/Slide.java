package fr.uge.hyperstack.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.view.EditorView;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Classe représentant une diapositive (ou aussi planche) d'une présentation.
 * <p>Elle peut contenir différents éléments :</p>
 * <ul>
 *     <li>des croquis</li>
 *     <li>du texte</li>
 *     <li>des images et vidéos</li>
 *     <li>des sons</li>
 *     <li>des géocalisations</li>
 *     <li>des entrées utilisateur (texte, checkbox, radio)</li>
 * </ul>
 * <p>
 * Une diapositive est la superposition de différents calques.
 * Les éléments sont placés sur ces derniers et non la diapositive directement.
 *
 * @see Layer
 * @see Stack
 */
public class Slide implements Serializable {
    /**
     * Ensemble des calques de cette diapositive.
     */
    private final List<Layer> layers;

    /**
     * Position de cette slide dans la présentation.
     * <p>
     * Commence à 1.
     */
    private int slideNumber;

    /**
     * Calque courant de cette slide.
     */
    private int currentLayer;


    public Slide(int slideNumber) {
        this.layers = new ArrayList<>();
        this.slideNumber = slideNumber;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    /**
     * Ajoute un élément au calque courant.
     *
     * @param element l'élément à ajouter au calque.
     */
    public void addElementOnLayer(Element element) {
        layers.get(currentLayer).addElement(element);
    }

    /**
     * Créer un calque.
     *
     * @param context
     * @param layout
     */
    public void createLayer(Context context, ConstraintLayout layout){
        Layer tmp = new Layer(context, layout);
        layers.add(tmp);
        currentLayer = layers.size() - 1;
    }

    /**
     * Dessine tous les calques contenu dans cette slide à l'écran.
     */
    public void drawLayers() {
        for (Layer layer : layers) {
            layer.draw();
        }
    }

    public void setDrawableElements() {
        for (Layer layer : layers) {
            layer.setDrawableElements();
        }
    }

    /**
     * Efface tous les éléments de cette slide présent à l'écran.
     */
    public void clear() {
        for (Layer layer : layers) {
            layer.clear();
        }
    }

    /**
     * Renvoie la position de cette slide dans la présentation.
     *
     * @return
     */
    public int getSlideNumber() {
        return slideNumber;
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }
}

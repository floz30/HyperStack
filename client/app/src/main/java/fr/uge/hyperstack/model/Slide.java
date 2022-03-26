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
    private List<Layer> layers;

    private View editView;

    private int slideNumber;

    private int currentLayer;


    public Slide(int slideNumber) {
        this.layers = new ArrayList<>();
        this.slideNumber = slideNumber;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void addElementOnLayer(PaintElement layer) {
        layers.get(currentLayer).addPaintElement(layer);
    }

    public void createLayer(Context context, ConstraintLayout layout){
        Layer tmp = new Layer(context, layout);
        //setEditView(view);
        layers.add(tmp);
        currentLayer = layers.size()-1;
    }

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


    public int getSlideNumber() {
        return slideNumber;
    }

    public void setEditView(View editView) {
        this.editView = editView;
    }

    public View getEditView(){
        return editView;
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
        currentLayer+=1;
    }

    public Layer getCurrentLayer(){
        return layers.get(currentLayer);
    }
}

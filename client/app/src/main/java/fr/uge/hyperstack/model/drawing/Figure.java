package fr.uge.hyperstack.model.drawing;

import java.io.Serializable;

import fr.uge.hyperstack.model.PaintElement;

public interface Figure extends PaintElement {

    boolean containsPoint(float x, float y);

    float getArea();
}

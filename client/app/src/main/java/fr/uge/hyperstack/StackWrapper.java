package fr.uge.hyperstack;

import java.io.Serializable;

import fr.uge.hyperstack.model.Element;

public class StackWrapper implements Serializable {
    private long timestamp;
    private int slideIndex;
    private Element element;

    public StackWrapper(int slideIndex, Element element) {
        this.timestamp = System.currentTimeMillis();
        this.element = element;
        this.slideIndex = slideIndex;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getSlideIndex() {
        return slideIndex;
    }

    public Element getElement() {
        return element;
    }
}

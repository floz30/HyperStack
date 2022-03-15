package fr.uge.hyperstack.model.drawing;

import fr.uge.hyperstack.model.Element;

abstract class AbstractFigure implements Figure, Element {
    private int color;
    private int strokeSize;
    private boolean fill;
    private Point origin;

    public AbstractFigure(int color, int strokeSize, Point origin) {
        this.origin = origin;
        this.color = color;
        this.strokeSize = strokeSize;
        this.fill = false;
    }

    public int getColor() {
        return color;
    }

    @Override
    public float getPositionX() {
        return origin.getX();
    }

    @Override
    public float getPositionY() {
        return origin.getY();
    }

    public int getStrokeSize() {
        return strokeSize;
    }
}

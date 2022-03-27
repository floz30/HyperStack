package fr.uge.hyperstack.model.drawing;


abstract class AbstractFigure implements Figure {
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

    @Override
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

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public Point getOrigin() {
        return origin;
    }

    @Override
    public int getStrokeSize() {
        return strokeSize;
    }
}

package fr.uge.hyperstack.model;

import fr.uge.hyperstack.model.media.Image;

public interface ElementVisitor {
    void visit(Image image);
    void visit(PaintElement paintElement);
}

package fr.uge.hyperstack.model;

import fr.uge.hyperstack.model.media.Image;
import fr.uge.hyperstack.model.media.Sound;
import fr.uge.hyperstack.model.media.Video;

public interface ElementVisitor {
    void draw(Image image);
    void draw(Video video);
    void draw(Sound sound);
    void draw(PaintElement paintElement);
}

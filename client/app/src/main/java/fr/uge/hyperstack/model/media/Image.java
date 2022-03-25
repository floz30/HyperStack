package fr.uge.hyperstack.model.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;

import java.io.Serializable;

import fr.uge.hyperstack.model.ElementVisitor;
import fr.uge.hyperstack.model.MediaElement;

/**
 * Représente une image importée dans l'application.
 */
public class Image implements MediaElement, Serializable {
    private transient Bitmap content = null;
    private float x;
    private float y;
    private float width;
    private float height;

    public Image(@NonNull Bitmap content) {
        this.content = content;
    }

    @Override
    public float convertBiasHorizontal(float maxWidth) {
        return (x + width / 2) / maxWidth;
    }

    @Override
    public float convertBiasVertical(float maxHeight) {
        return (y + height / 2) / maxHeight;
    }


    @Override
    public float getPositionX() {
        return x;
    }

    @Override
    public float getPositionY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public Bitmap getContent() {
        return content;
    }

//    @Override
//    public void draw(Canvas canvas, Paint paint) {
//        canvas.drawBitmap(content, 0, 0, paint); // TODO
//    }

    public void draw(Context context, ConstraintSet constraintSet, View father) {
        ImageView img = new ImageView(context);

        img.setId(ViewCompat.generateViewId());
        img.setImageBitmap(content);
        int id = img.getId();

        constraintSet.connect(id, ConstraintSet.START, father.getId(), ConstraintSet.START);
        constraintSet.connect(id, ConstraintSet.END, father.getId(), ConstraintSet.END);
        constraintSet.connect(id, ConstraintSet.TOP, father.getId(), ConstraintSet.TOP);
        constraintSet.connect(id, ConstraintSet.BOTTOM, father.getId(), ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(id, 0.5f);
        constraintSet.setVerticalBias(id, 0.5f);
        constraintSet.constrainWidth(id, ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(id, ConstraintSet.WRAP_CONTENT);

        ViewGroup viewGroup = (ViewGroup) father;
        viewGroup.addView(img);
    }

    @Override
    public void accept(ElementVisitor elementVisitor) {
        elementVisitor.visit(this);
    }
}

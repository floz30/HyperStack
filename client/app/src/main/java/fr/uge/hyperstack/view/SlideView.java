package fr.uge.hyperstack.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import fr.uge.hyperstack.model.PaintElement;
import fr.uge.hyperstack.model.drawing.Figure;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.Element;
import fr.uge.hyperstack.model.ElementVisitor;
import fr.uge.hyperstack.model.PaintElement;
import fr.uge.hyperstack.model.media.Image;

public class SlideView extends ConstraintLayout implements ElementVisitor {
    private final Context context;
    private ConstraintSet constraintSet = new ConstraintSet();


    public SlideView(@NonNull Context context, ConstraintLayout layout) {
        super(context);
        this.context = context;
        init(context, layout);
    }

    private void init(Context context, ConstraintLayout layout) {
        this.setId(ViewCompat.generateViewId());
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void drawFigure(PaintElement element) {
        EditorView editorView = new EditorView(context);
        editorView.addElement(element);

        int id = editorView.getId();
        constraintSet.constrainWidth(id, ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(id, ConstraintSet.WRAP_CONTENT);

        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(editorView);
    }

    public void drawButton() {
        Button btn = new Button(context);
        btn.setId(ViewCompat.generateViewId());
        btn.setText("HELLO");

        constraintSet.connect(btn.getId(), ConstraintSet.START, this.getId(), ConstraintSet.START);
        constraintSet.connect(btn.getId(), ConstraintSet.END, this.getId(), ConstraintSet.END);
        constraintSet.connect(btn.getId(), ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        constraintSet.connect(btn.getId(), ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(btn.getId(), 0.75f);
        constraintSet.setVerticalBias(btn.getId(), 0.25f);
        constraintSet.constrainWidth(btn.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(btn.getId(), ConstraintSet.WRAP_CONTENT);

        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(btn);
    }

    public void build() {
        this.setConstraintSet(constraintSet);
    }

    @Override
    public void visit(Image image) {
        ImageView img = new ImageView(context);

        img.setId(ViewCompat.generateViewId());
        img.setImageBitmap(image.getContent());
        int id = img.getId();

        constraintSet.connect(id, ConstraintSet.START, this.getId(), ConstraintSet.START);
        constraintSet.connect(id, ConstraintSet.END, this.getId(), ConstraintSet.END);
        constraintSet.connect(id, ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        constraintSet.connect(id, ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(id, image.convertBiasHorizontal(this.getWidth()));
        constraintSet.setVerticalBias(id, image.convertBiasVertical(this.getHeight()));
        constraintSet.constrainWidth(id, ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(id, ConstraintSet.WRAP_CONTENT);

        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(img);
    }

    @Override
    public void visit(PaintElement paintElement) {

    }
}

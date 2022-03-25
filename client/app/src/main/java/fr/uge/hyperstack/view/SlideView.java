package fr.uge.hyperstack.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import fr.uge.hyperstack.model.PaintElement;
import fr.uge.hyperstack.model.drawing.Figure;

public class SlideView extends ConstraintLayout {
    private final Context context;
    private ConstraintSet constraintSet = new ConstraintSet();
    private final Canvas canvas = new Canvas();

    public SlideView(@NonNull Context context, ConstraintLayout layout) {
        super(context);
        this.context = context;
        init(context, layout);
    }

    private void init(Context context, ConstraintLayout layout) {
        this.setId(ViewCompat.generateViewId());
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

//        ConstraintSet set = new ConstraintSet();
//        set.clone(layout);
//        set.constrainHeight(this.getId(), -1);
//        set.connect(this.getId(), ConstraintSet.TOP, R.id.editLayout, ConstraintSet.TOP);
//        set.connect(this.getId(), ConstraintSet.BOTTOM, R.id.bottomAppBar, ConstraintSet.TOP);
//        set.connect(this.getId(), ConstraintSet.START, R.id.editLayout, ConstraintSet.START);
//        set.connect(this.getId(), ConstraintSet.END, R.id.editLayout, ConstraintSet.END);
//        layout.setConstraintSet(set);

    }

    public void drawImage() {
        Button btn = new Button(context);
        btn.setId(ViewCompat.generateViewId());
        btn.setText("ALLO");

        constraintSet.connect(btn.getId(), ConstraintSet.START, this.getId(), ConstraintSet.START);
        constraintSet.connect(btn.getId(), ConstraintSet.END, this.getId(), ConstraintSet.END);
        constraintSet.connect(btn.getId(), ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        constraintSet.connect(btn.getId(), ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(btn.getId(), 0.25f);
        constraintSet.setVerticalBias(btn.getId(), 0.75f);
        constraintSet.constrainWidth(btn.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(btn.getId(), ConstraintSet.WRAP_CONTENT);

        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(btn);
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

}
